package com.news.rss;

import android.app.Application;
import android.content.SharedPreferences;

import com.news.rss.component.DaggerRssComponent;
import com.news.rss.db.RssDBAdapter;
import com.news.rss.entity.DaoSession;
import com.news.rss.holder.DaggerHolder;
import com.news.rss.module.RssModule;

import javax.inject.Inject;

public class RssApplication extends Application {

    private static RssApplication sInstance;
    @Inject
    protected RssDBAdapter mDBAdapter;
    @Inject
    protected SharedPreferences mPreferences;
    private DaggerHolder mHolder;

    public static RssApplication getInstance() {
        return sInstance;
    }

    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mHolder = new DaggerHolder(DaggerRssComponent.builder().rssModule(new RssModule()).build());
    }

    public DaggerHolder getHolder() {
        return mHolder;
    }

    public DaoSession getSession() {
        return mDBAdapter.getSession();
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }
}