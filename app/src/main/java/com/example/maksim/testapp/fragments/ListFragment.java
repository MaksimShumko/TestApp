package com.example.maksim.testapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.activities.MainActivity;
import com.example.maksim.testapp.adapters.RecyclerViewAdapter;
import com.example.maksim.testapp.contracts.ModelListViewContract;
import com.example.maksim.testapp.models.Model;
import com.example.maksim.testapp.presenters.ModelListPresenter;

import java.util.List;


public class ListFragment extends Fragment implements ModelListViewContract.View {

    private ModelListViewContract.Presenter presenter;
    private RecyclerViewAdapter adapter;
    private OnListFragmentInteractionListener onListFragmentInteractionListener;

    public ListFragment() {
        Log.e("ListFragment", "ListFragment");
    }

    public static ListFragment newInstance() {
        Log.e("ListFragment", "newInstance");
        return new ListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("ListFragment", "onCreate");
        super.onCreate(savedInstanceState);
        presenter = new ModelListPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("ListFragment", "onCreateView");

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new RecyclerViewAdapter(getContext(), presenter.getAllModels());
        adapter.setOnItemClickListener(presenter);
        recyclerView.setAdapter(adapter);
        notifyDataSetChanged();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        Log.e("ListFragment", "onAttach");
        super.onAttach(context);
        try {
            onListFragmentInteractionListener = (OnListFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        Log.e("ListFragment", "onDetach");
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("List");
    }

    @Override
    public void showView(List<Model> elements) {

    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Model model) {
        onListFragmentInteractionListener.onListFragmentInteraction(model);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Model model);
    }
}
