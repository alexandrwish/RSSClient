package com.news.rss.client;

import com.news.rss.record.RssRecord;

import retrofit2.http.GET;
import rx.Observable;

public interface RssClient {

    @GET("/feed/rss")
    Observable<RssRecord> getNews();
}