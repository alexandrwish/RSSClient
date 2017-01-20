package com.news.rss.component;

import com.news.rss.RssApplication;
import com.news.rss.activity.RssActivity;
import com.news.rss.module.RssModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RssModule.class})
public interface RssComponent {

    void inject(RssApplication application);

    void inject(RssActivity activity);
}