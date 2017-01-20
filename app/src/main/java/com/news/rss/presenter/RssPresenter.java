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
    private boolean needRestore;

    @Inject
    public RssPresenter(ItemLoader loader) {
        mLoader = loader;
        load();
    }

    public void restore(final ViewListener listener) {
        mListener = listener;
        if (needRestore) {
            mLoader.restore()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<List<ItemRecord>>() {
                        public void call(List<ItemRecord> records) {
                            mListener.render(records);
                        }
                    });
        }
    }

    public void pause() {
        mListener = null;
    }

    public void click(ItemRecord record) {
        if (mListener != null) {
            mListener.show(record.getLink());
        }
    }

    public void commentClick(ItemRecord record) {
        if (mListener != null) {
            mListener.show(record.getComments());
        }
    }

    public void load() {
        mLoader.load()
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<List<ItemRecord>>() {
                    public void onCompleted() {
                        mListener.stopRefresh();
                    }

                    public void onError(Throwable e) {
                        Log.e(RssPresenter.class.getName(), e.getMessage(), e);
                        mLoader.restore()
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Action1<List<ItemRecord>>() {
                                    public void call(List<ItemRecord> records) {
                                        render(records);
                                    }
                                });
                    }

                    public void onNext(List<ItemRecord> records) {
                        render(records);
                    }
                });
    }

    private void render(List<ItemRecord> records) {
        if (mListener != null) {
            mListener.render(records);
            needRestore = false;
        } else {
            needRestore = true;
        }
    }
}