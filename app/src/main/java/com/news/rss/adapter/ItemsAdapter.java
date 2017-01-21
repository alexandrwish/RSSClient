package com.news.rss.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.news.rss.R;
import com.news.rss.listener.CommentListener;
import com.news.rss.record.ItemRecord;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsAdapter extends ArrayAdapter<ItemRecord> {

    private final CommentListener mListener;

    public ItemsAdapter(Context context) {
        super(context, R.layout.list_item, new ArrayList<ItemRecord>());
        mListener = (CommentListener) context;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item, parent, false);
            view.setTag(new ViewHolder(view));
        } else {
            view = convertView;
        }
        final ItemRecord record = getItem(position);
        if (record != null) {
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.title.setText(record.getTitle());
            holder.description.setText(record.getDescription());
            holder.date.setText(record.getDate());
            holder.comments.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    mListener.onShowComments(record);
                }
            });
        }
        return view;
    }

    protected class ViewHolder {

        @BindView(R.id.item_title)
        protected TextView title;
        @BindView(R.id.item_description)
        protected TextView description;
        @BindView(R.id.item_date)
        protected TextView date;
        @BindView(R.id.item_comments)
        protected TextView comments;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}