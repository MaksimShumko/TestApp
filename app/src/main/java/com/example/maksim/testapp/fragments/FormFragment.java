package com.example.maksim.testapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.contracts.ModelFormViewContract;
import com.example.maksim.testapp.presenters.ModelFormPresenter;

public class FormFragment extends Fragment implements ModelFormViewContract.View {
    private TextView textView;
    private ModelFormViewContract.Actions presenter;
    private int position;

    public FormFragment() {
        // Required empty public constructor
    }

    public static FormFragment newInstance() {
        return new FormFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ModelFormPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        textView = (TextView) view.findViewById(R.id.textView);
        if(presenter != null)
            textView.setText(presenter.getTitle(position) + " " + presenter.getDescription(position));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onItemClick(int position) {

    }


}
