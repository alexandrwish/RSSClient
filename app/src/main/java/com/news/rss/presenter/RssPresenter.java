package com.news.rss.presenter;

import android.util.Log;

import com.news.rss.listener.ViewListener;
import com.news.rss.loader.ItemLoader;
import com.news.rss.record.ItemRecord;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RssPresenter {

    private final ItemLoader mLoader;
    private ViewListener mListener;

    @Inject
    public RssPresenter(ItemLoader loader) {
        mLoader = loader;
        load();
    }

    public void restore(final ViewListener listener) {
        Log.d(RssPresenter.class.getName(), "Восстановление данных из БД");
        mListener = listener;
        mLoader.restore()
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<List<ItemRecord>>() {
                    public void call(List<ItemRecord> records) {
                        Log.d(RssPresenter.class.getName(), "Данные загружены из БД");
                        render(records);
                    }
                }, new Action1<Throwable>() {
                    public void call(Throwable throwable) {
                        Log.e(RssPresenter.class.getName(), throwable.getMessage(), throwable);
                    }
                });
    }

    public void pause() {
        mListener = null;
    }

    public void click(ItemRecord record) {
        if (mListener != null) {
            mListener.onShowNews(record.getLink());
        }
    }

    public void commentClick(ItemRecord record) {
        if (mListener != null) {
            mListener.onShowNews(record.getComments());
        }
    }

    public void load() {
        mLoader.load()
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<ItemRecord>>() {
                    public void onCompleted() {
                        complete();
                    }

                    public void onError(Throwable throwable) {
                        Log.e(RssPresenter.class.getName(), throwable.getMessage(), throwable);
                        mLoader.restore()
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Subscriber<List<ItemRecord>>() {
                                    public void onCompleted() {
                                        complete();
                                    }

                                    public void onError(Throwable throwable) {
                                        Log.e(RssPresenter.class.getName(), throwable.getMessage(), throwable);
                                        complete();
                                    }

                                    public void onNext(List<ItemRecord> records) {
                                        Log.d(RssPresenter.class.getName(), "Данные восстановлены из БД");
                                        render(records);
                                    }
                                });
                    }

                    public void onNext(List<ItemRecord> records) {
                        Log.d(RssPresenter.class.getName(), "Данные загружены по сети");
                        render(records);
                    }
                });
    }

    private void render(List<ItemRecord> records) {
        if (mListener != null) {
            mListener.onDataLoad(records);
        } else {
            Log.e(RssPresenter.class.getName(), "Нету слушателя");
        }
    }

    private void complete() {
        if (mListener != null) {
            mListener.onStopRefresh();
        }
    }
}