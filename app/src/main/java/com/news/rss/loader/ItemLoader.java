package com.news.rss.loader;

import android.content.SharedPreferences;
import android.util.Log;

import com.news.rss.RssApplication;
import com.news.rss.client.RssClient;
import com.news.rss.entity.ItemEntity;
import com.news.rss.entity.ItemEntityDao;
import com.news.rss.record.ItemRecord;
import com.news.rss.record.RssRecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ItemLoader {

    private final RssClient mClient;
    private final SharedPreferences mPreferences;

    @Inject
    public ItemLoader(RssClient client, SharedPreferences preferences) {
        mClient = client;
        mPreferences = preferences;
    }

    public Observable<List<ItemRecord>> restore() {
        return RssApplication.getInstance().getSession().getItemEntityDao().rx().loadAll()
                .observeOn(Schedulers.io())
                .map(new Func1<List<ItemEntity>, List<ItemRecord>>() {
                    public List<ItemRecord> call(List<ItemEntity> entities) {
                        return convert(entities);
                    }
                });
    }

    public Observable<List<ItemRecord>> load() {
        return mClient.getNews()
                .observeOn(Schedulers.io())
                .filter(new Func1<RssRecord, Boolean>() {
                    public Boolean call(RssRecord rssRecord) {
                        return !mPreferences.getString("update_version", "").equalsIgnoreCase(rssRecord.getChannel().getDate());
                    }
                })
                .doOnNext(new Action1<RssRecord>() {
                    public void call(RssRecord rssRecord) {
                        mPreferences.edit().putString("update_version", rssRecord.getChannel().getDate()).apply();
                    }
                })
                .map(new Func1<RssRecord, List<ItemRecord>>() {
                    public List<ItemRecord> call(RssRecord rssRecord) {
                        return rssRecord.getChannel().getItems();
                    }
                })
                .map(new Func1<List<ItemRecord>, List<ItemRecord>>() {
                    public List<ItemRecord> call(List<ItemRecord> records) {
                        for (ItemRecord record : records) {
                            convertDateFormat(record);
                        }
                        return records;
                    }
                })
                .doOnNext(new Action1<List<ItemRecord>>() {
                    public void call(List<ItemRecord> records) {
                        ItemEntityDao dao = RssApplication.getInstance().getSession().getItemEntityDao();
                        dao.deleteAll();
                        for (ItemRecord record : records) {
                            dao.insert(convert(record));
                        }
                    }
                });
    }

    private List<ItemRecord> convert(List<ItemEntity> entities) {
        List<ItemRecord> records = new ArrayList<>(entities.size());
        for (ItemEntity entity : entities) {
            records.add(new ItemRecord(entity.getTitle(),
                    entity.getLink(),
                    entity.getDate(),
                    entity.getDescription(),
                    entity.getComments()));
        }
        return records;
    }

    private ItemEntity convert(ItemRecord record) {
        return new ItemEntity(null,
                record.getTitle(),
                record.getLink(),
                record.getDate(),
                record.getDescription(),
                record.getComments());
    }

    private void convertDateFormat(ItemRecord record) {
        try {
            DateFormat fromFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.UK);
            fromFormat.setLenient(false);
            DateFormat toFormat = new SimpleDateFormat("d MMM yyyy HH:mm", new Locale("ru"));
            toFormat.setLenient(false);
            record.setDate(toFormat.format(fromFormat.parse(record.getDate())));
        } catch (ParseException e) {
            Log.e(ItemLoader.class.getName(), e.getMessage(), e);
        }
    }
}