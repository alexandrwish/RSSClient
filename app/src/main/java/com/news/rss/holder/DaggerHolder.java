package com.news.rss.holder;

import com.news.rss.RssApplication;
import com.news.rss.activity.RssActivity;
import com.news.rss.component.RssComponent;

public class DaggerHolder {

    private final RssComponent mComponent;

    public DaggerHolder(RssComponent component) {
        mComponent = component;
        mComponent.inject(RssApplication.getInstance());
    }

    public void inject(RssActivity activity) {
        mComponent.inject(activity);
    }
}