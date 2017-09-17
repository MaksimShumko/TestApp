package com.example.maksim.testapp.details.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.details.model.data.GitHubUserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maksim on 2017-09-17.
 */

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    private final String LOG_TAG = "RecyclerViewAdapter";
    private List<ElementsHolder> elements;
    private Context context;

    public DetailsAdapter(Context context) {
        //Log.e(LOG_TAG, "RecyclerViewAdapter");
        this.context = context;
    }

    public void updateElements(GitHubUserDetails userDetails) {
        elements = new ArrayList<>();
        elements.add(new ElementsHolder(context.getString(R.string.user_name), userDetails.name));
        elements.add(new ElementsHolder(context.getString(R.string.user_login), userDetails.login));
        elements.add(new ElementsHolder(context.getString(R.string.user_bio), userDetails.bio));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.e(LOG_TAG, "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.detail_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //Log.e(LOG_TAG, "onBindViewHolder");
        if(holder != null && elements != null) {
            ElementsHolder elementsHolder = elements.get(position);
            holder.title.setText(elementsHolder.title);
            holder.value.setText(elementsHolder.value);
        }
    }

    @Override
    public int getItemCount() {
        //Log.e(LOG_TAG, "getItemCount");
        if(elements != null)
            return elements.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView value;
        View itemView;

        ViewHolder(View itemView) {
            super(itemView);
            //Log.e(LOG_TAG, "ViewHolder");
            this.itemView = itemView;
            title = (TextView) itemView.findViewById(R.id.detailTitle);
            value = (TextView) itemView.findViewById(R.id.detailValue);
        }
    }

    private class ElementsHolder {
        String title;
        String value;

        ElementsHolder(String title, String value) {
            this.title = title;
            this.value = value;
        }
    }
}

