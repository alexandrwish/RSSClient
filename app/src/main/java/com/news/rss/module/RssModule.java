package com.news.rss.module;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.news.rss.RssApplication;
import com.news.rss.client.RssClient;
import com.news.rss.db.RssDBAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public class RssModule {

    @Provides
    @Singleton
    public RssClient provideRssClient() {
        return new Retrofit.Builder()
                .baseUrl("http://4pda.ru")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build().create(RssClient.class);
    }

    @Provides
    @Singleton
    public RssDBAdapter provideDBAdapter() {
        return new RssDBAdapter();
    }

    @Provides
    @Singleton
    public SharedPreferences providePreferences() {
        return PreferenceManager.getDefaultSharedPreferences(RssApplication.getInstance());
    }
}