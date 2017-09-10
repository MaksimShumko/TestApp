package com.example.maksim.testapp.list.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.list.data.GitHubUser;
import com.example.maksim.testapp.list.presenters.ListPresenterInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final String LOG_TAG = "RecyclerViewAdapter";
    private List<GitHubUser> elements;
    private ListPresenterInterface presenter;
    private Context context;

    public RecyclerViewAdapter(Context context, List<GitHubUser> elements) {
        //Log.e(LOG_TAG, "RecyclerViewAdapter");

        this.context = context;
        this.elements = elements;
    }

    public void setOnItemClickListener(ListPresenterInterface presenter) {
        //Log.e(LOG_TAG, "setOnItemClickListener");
        this.presenter = presenter;
    }

    public void updateElements(List<GitHubUser> elements) {
        this.elements = elements;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.e(LOG_TAG, "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Log.e(LOG_TAG, "onBindViewHolder");
        if(holder != null) {
            final GitHubUser element = elements.get(position);
            holder.login.setText(element.login);
            holder.name.setText(element.eventsUrl);
            Picasso.with(context)
                    .load(element.avatarUrl)
                    .into(holder.avatar);
            holder.score.setText(String.valueOf(element.id));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(presenter != null)
                        presenter.onItemClick(element.login);
                }
            });
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
        TextView login;
        TextView name;
        ImageView avatar;
        TextView score;
        View itemView;

        ViewHolder(View itemView) {
            super(itemView);
            //Log.e(LOG_TAG, "ViewHolder");
            this.itemView = itemView;
            login = (TextView) itemView.findViewById(R.id.listItemLogin);
            name = (TextView) itemView.findViewById(R.id.listItemName);
            avatar = (ImageView) itemView.findViewById(R.id.listItemAvatar);
            score = (TextView) itemView.findViewById(R.id.listItemScore);
        }
    }
}
