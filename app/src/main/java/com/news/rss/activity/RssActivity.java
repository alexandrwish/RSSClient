package com.news.rss.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.news.rss.R;
import com.news.rss.RssApplication;
import com.news.rss.adapter.ItemsAdapter;
import com.news.rss.fragment.ContentFragment;
import com.news.rss.listener.CommentListener;
import com.news.rss.listener.ViewListener;
import com.news.rss.presenter.RssPresenter;
import com.news.rss.record.ItemRecord;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class RssActivity extends Activity implements ViewListener, CommentListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.container_rss)
    protected SwipeRefreshLayout mSwipeContainer;
    @BindView(R.id.list_rss)
    protected ListView mListRss;

    @Inject
    protected RssPresenter mPresenter;

    protected ItemsAdapter mAdapter;
    protected ContentFragment mFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);
        RssApplication.getInstance().getHolder().inject(this);
        ButterKnife.bind(this);
        mSwipeContainer.setOnRefreshListener(this);
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_red_dark);
        mAdapter = new ItemsAdapter(this);
        mListRss.setAdapter(mAdapter);
        mFragment = (ContentFragment) getFragmentManager().findFragmentById(R.id.fragment_content);
    }

    protected void onResume() {
        super.onResume();
        mPresenter.restore(this);
    }

    protected void onPause() {
        mPresenter.pause();
        super.onPause();
    }

    public void onDataLoad(final List<ItemRecord> records) {
        runOnUiThread(new Runnable() {
            public void run() {
                mAdapter.clear();
                mAdapter.addAll(records);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onStopRefresh() {
        runOnUiThread(new Runnable() {
            public void run() {
                mSwipeContainer.setRefreshing(false);
            }
        });
    }

    public void onShowNews(String url) {
        mFragment.show(url);
    }

    public void onShowComments(ItemRecord record) {
        mPresenter.commentClick(record);
    }

    public void onRefresh() {
        mPresenter.load();
    }

    @OnItemClick(R.id.list_rss)
    protected void onItemClick(int position) {
        mPresenter.click(mAdapter.getItem(position));
    }
}