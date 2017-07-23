package com.example.maksim.testapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.activities.MainActivity;
import com.example.maksim.testapp.contracts.ModelFormViewContract;
import com.example.maksim.testapp.models.Model;
import com.example.maksim.testapp.presenters.ModelFormPresenter;

public class FormFragment extends Fragment implements ModelFormViewContract.View {
    private TextView textView;
    private Model model;
    private ModelFormViewContract.Presenter presenter;

    public FormFragment() {
        // Required empty public constructor
        presenter = new ModelFormPresenter(this);
    }

    public static FormFragment newInstance() {
        return new FormFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null)
            model = bundle.getParcelable("model");
        else
            model = presenter.getModel(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        textView = (TextView) view.findViewById(R.id.textView);
        if(model != null)
            textView.setText(model.getTitle() + " " + model.getDescription());
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

    @Override
    public void onResume() {
        super.onResume();
        /*boolean isLandTablet = getResources().getBoolean(R.bool.isLandTablet);
        if(isLandTablet)
            ((MainActivity) getActivity()).setActionBarTitle("List & form");
        else
            ((MainActivity) getActivity()).setActionBarTitle("Form");*/
    }

    @Override
    public void onItemClick(int position) {

    }
}
