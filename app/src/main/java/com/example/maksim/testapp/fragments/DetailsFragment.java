package com.example.maksim.testapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.contracts.ModelFormViewContract;
import com.example.maksim.testapp.models.GitHubUserDescription;
import com.example.maksim.testapp.presenters.ModelDescriptionPresenter;

public class DetailsFragment extends Fragment implements ModelFormViewContract.View {
    public static final String SELECTED_MODEL = "SELECTED_MODEL";
    private TextView textView;
    private String userLogin;
    private GitHubUserDescription gitHubUserDescription;
    private ModelFormViewContract.Presenter presenter;

    public DetailsFragment() {
        presenter = new ModelDescriptionPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            userLogin = bundle.getString(SELECTED_MODEL);
            if(userLogin != null) {
                presenter.executeRequest(userLogin);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        textView = (TextView) view.findViewById(R.id.textView);
        if (gitHubUserDescription != null)
            textView.setText(gitHubUserDescription.login + " " + gitHubUserDescription.name);
        return view;
    }

    @Override
    public void updateView(GitHubUserDescription gitHubUserDescription) {
        this.gitHubUserDescription = gitHubUserDescription;
        if (gitHubUserDescription != null && textView != null)
            textView.setText(gitHubUserDescription.login + " " + gitHubUserDescription.name);
    }

    public String getGitHubUser() {
        return userLogin;
    }

    public void updateContent(String userLogin) {
        this.userLogin = userLogin;
        presenter.executeRequest(userLogin);
        if(textView != null)
            textView.setText("");
    }
}
