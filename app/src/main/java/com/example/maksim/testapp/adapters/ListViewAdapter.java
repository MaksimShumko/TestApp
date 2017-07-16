package com.example.maksim.testapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.contracts.ModelListViewContract;

public class ListViewAdapter extends ArrayAdapter<ModelListViewContract.ListItem> {
    private final String LOG_TAG = "ListViewAdapter";
    private ModelListViewContract.ListItem provider;
    private ModelListViewContract.OnItemClickListener listener;

    private static class ViewHolder {
        TextView title;
        TextView description;
    }

    public ListViewAdapter(Context context, ModelListViewContract.ListItem provider) {
        super(context, R.layout.list_item);
        Log.e(LOG_TAG, "ListViewAdapter");
        this.provider = provider;
    }

    public void setOnItemClickListener(ModelListViewContract.OnItemClickListener listener) {
        Log.e(LOG_TAG, "setOnItemClickListener");
        this.listener = listener;
    }

    @Override
    public int getCount() {
        Log.e(LOG_TAG, "getCount " + provider.getCount());
        return provider.getCount();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        Log.e(LOG_TAG, "getView");
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
        if(provider != null) {
            viewHolder.title.setText(provider.getTitle(position));
            viewHolder.description.setText(provider.getDescription(position));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
        }

        return convertView;
    }

}
