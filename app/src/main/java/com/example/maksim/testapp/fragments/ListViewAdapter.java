package com.example.maksim.testapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.fragments.ListFragment.OnListFragmentInteractionListener;
import com.example.maksim.testapp.fragments.dummy.DummyContent.DummyItem;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<DummyItem> {

    private final OnListFragmentInteractionListener listener;

    private static class ViewHolder {
        TextView title;
        TextView description;
    }

    public ListViewAdapter(Context context, List<DummyItem> items, OnListFragmentInteractionListener listener) {
        super(context,  R.layout.list_item, items);
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final DummyItem item = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.listItemTitle);
            viewHolder.description = (TextView) convertView.findViewById(R.id.listItemDescription);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(item != null) {
            viewHolder.title.setText(item.id);
            viewHolder.description.setText(item.details);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onListFragmentInteraction(item);
                }
            });
        }

        return convertView;
    }

}
