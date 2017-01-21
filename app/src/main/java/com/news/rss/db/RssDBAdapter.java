package com.news.rss.db;

import com.news.rss.RssApplication;
import com.news.rss.entity.DaoMaster;
import com.news.rss.entity.DaoSession;

public class RssDBAdapter {

    private static final String DATABASE_NAME = "rss_name";
    private static final Integer DATABASE_VERSION = 1;

    private final DaoSession mSession;

    public RssDBAdapter() {
        mSession = new DaoMaster(new RssOpenHelper(RssApplication.getInstance(), DATABASE_NAME, DATABASE_VERSION).getWritableDatabase()).newSession();
    }

    public DaoSession getSession() {
        return mSession;
    }
}