package com.example.maksim.testapp.details.fragment;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.details.presenter.DetailsViewPresenterInterface;
import com.example.maksim.testapp.details.model.data.GitHubUserDetails;
import com.example.maksim.testapp.details.presenter.DetailsPresenter;
import com.example.maksim.testapp.github_api.room.RoomSqlDatabase;

public class DetailsFragment extends Fragment implements DetailsViewInterface {
    public static final String SELECTED_MODEL = "SELECTED_MODEL";
    private TextView textView;
    private String userLogin;
    private GitHubUserDetails gitHubUserDetails;
    private DetailsViewPresenterInterface presenter;

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
        textView = (TextView) view.findViewById(R.id.textView);
        if (gitHubUserDetails != null)
            textView.setText(gitHubUserDetails.login + " " + gitHubUserDetails.name);
        return view;
    }

    @Override
    public void updateView(GitHubUserDetails gitHubUserDetails) {
        this.gitHubUserDetails = gitHubUserDetails;
        if (gitHubUserDetails != null && textView != null)
            textView.setText(gitHubUserDetails.login + " " + gitHubUserDetails.name);
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
        presenter.onUserChanged();
        if (textView != null)
            textView.setText("");
    }
}
