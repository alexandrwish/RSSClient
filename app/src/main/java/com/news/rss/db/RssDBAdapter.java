package com.news.rss.db;

import com.news.rss.RssApplication;
import com.news.rss.entity.DaoMaster;
import com.news.rss.entity.DaoSession;

public class RssDBAdapter {

    public static final String DATABASE_NAME = "rss_name";
    public static final Integer DATABASE_VERSION = 1;

    private DaoSession mSession;

    public RssDBAdapter() {
        init(RssApplication.getInstance());
    }

    private void init(RssApplication application) {
        RssOpenHelper helper = new RssOpenHelper(application, DATABASE_NAME, DATABASE_VERSION);
        mSession = new DaoMaster(helper.getWritableDatabase()).newSession();
    }

    public DaoSession getSession() {
        return mSession;
    }
}