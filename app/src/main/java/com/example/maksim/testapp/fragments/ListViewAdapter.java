package com.example.maksim.testapp.fragments;

import android.app.Activity;
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

    private final Activity activity;
    private int resource;
    private final List<DummyItem> items;
    private final OnListFragmentInteractionListener listener;

    static class ViewHolder {
        public TextView text1;
        public TextView text2;
    }

    public ListViewAdapter(Activity activity, int resource, List<DummyItem> items, OnListFragmentInteractionListener listener) {
        super(activity, resource, items);
        this.activity = activity;
        this.resource = resource;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(resource, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text1 = (TextView) rowView.findViewById(R.id.listItemText1);
            viewHolder.text2 = (TextView) rowView.findViewById(R.id.listItemText2);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.text1.setText(items.get(position).id);
        holder.text2.setText(items.get(position).details);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onListFragmentInteraction(items.get(position));
            }
        });

        return rowView;
    }
}
