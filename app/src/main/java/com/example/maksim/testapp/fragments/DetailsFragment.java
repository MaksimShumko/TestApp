package com.example.maksim.testapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.contracts.ModelFormViewContract;
import com.example.maksim.testapp.models.Model;
import com.example.maksim.testapp.presenters.ModelFormPresenter;

public class DetailsFragment extends Fragment implements ModelFormViewContract.View {
    public static final String SELECTED_MODEL = "SELECTED_MODEL";
    private TextView textView;
    private Model model;
    private ModelFormViewContract.Presenter presenter;

    public DetailsFragment() {
        presenter = new ModelFormPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null)
            model = bundle.getParcelable(SELECTED_MODEL);
        else
            model = presenter.getModel(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        textView = (TextView) view.findViewById(R.id.textView);
        if(model != null)
            textView.setText(model.getTitle() + " " + model.getDescription());
        return view;
    }

    @Override
    public void onItemClick(int position) {

    }

    public Model getModel() {
        return model;
    }

    public void updateContent(Model model) {
        if(model != null) {
            this.model = model;
            if(textView != null)
                textView.setText(this.model.getTitle() + " " + this.model.getDescription());
        }
    }
}
