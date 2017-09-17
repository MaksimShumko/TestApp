package com.example.maksim.testapp.details.fragment;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.details.fragment.adapter.DetailsAdapter;
import com.example.maksim.testapp.details.presenter.DetailsViewPresenterInterface;
import com.example.maksim.testapp.details.model.data.GitHubUserDetails;
import com.example.maksim.testapp.details.presenter.DetailsPresenter;
import com.example.maksim.testapp.githubapi.room.RoomSqlDatabase;

public class DetailsFragment extends Fragment implements DetailsViewInterface {
    public static final String SELECTED_MODEL = "SELECTED_MODEL";
    private String userLogin;
    private DetailsViewPresenterInterface presenter;
    private DetailsAdapter adapter;
    private RecyclerView recyclerView;

    public DetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null) {
            userLogin = bundle.getString(SELECTED_MODEL);
        }

        RoomSqlDatabase roomSqlDatabase = Room.databaseBuilder(getActivity(),
                RoomSqlDatabase.class, RoomSqlDatabase.DATABASE_NAME_GIT_HUB).build();
        presenter = new DetailsPresenter(this, roomSqlDatabase);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new DetailsAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void updateView(GitHubUserDetails gitHubUserDetails) {
        if (gitHubUserDetails != null) {
            adapter.updateElements(gitHubUserDetails);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public String getSelectedUserLogin() {
        return userLogin;
    }

    public String getGitHubUser() {
        return userLogin;
    }

    public void updateContent(String userLogin) {
        this.userLogin = userLogin;
        if (presenter != null)
            presenter.onUserChanged();
        if (adapter != null) {
            adapter.cleanFields();
            adapter.notifyDataSetChanged();
        }
    }
}
