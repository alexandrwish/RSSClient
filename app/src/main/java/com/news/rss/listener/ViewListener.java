package com.news.rss.listener;

import com.news.rss.record.ItemRecord;

import java.util.List;

public interface ViewListener {

    void onDataLoad(List<ItemRecord> records);

    void onStopRefresh();

    void onShowNews(String url);
}