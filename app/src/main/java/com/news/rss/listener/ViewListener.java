package com.news.rss.listener;

import com.news.rss.record.ItemRecord;

import java.util.List;

public interface ViewListener {

    void render(List<ItemRecord> records);

    void stopRefresh();

    void show(String url);
}