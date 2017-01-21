package com.news.rss.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.news.rss.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentFragment extends Fragment {

    @BindView(R.id.content)
    protected WebView mContent;

    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container);
        ButterKnife.bind(this, view);
        mContent.getSettings().setJavaScriptEnabled(true);
        mContent.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        return view;
    }

    public void show(String url) {
        mContent.loadUrl(url);
    }
}