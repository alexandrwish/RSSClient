package com.news.rss.listener;

import com.news.rss.record.ItemRecord;

public interface CommentListener {

    void onShowComments(ItemRecord record);
}