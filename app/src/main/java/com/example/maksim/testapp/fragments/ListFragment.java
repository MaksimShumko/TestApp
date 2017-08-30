package com.example.maksim.testapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.adapters.RecyclerViewAdapter;
import com.example.maksim.testapp.contracts.ModelListViewContract;
import com.example.maksim.testapp.presenters.ModelListPresenter;


public class ListFragment extends Fragment implements ModelListViewContract.View{

    public static final String RECYCLER_LAYOUT_STATE = "RECYCLER_LAYOUT_STATE";
    private final String LOG = "ListFragment";
    private ModelListViewContract.Presenter presenter;
    private RecyclerViewAdapter adapter;
    private OnListFragmentInteractionListener onListFragmentInteractionListener;
    private RecyclerView recyclerView;
    private Parcelable savedRecyclerLayoutState;

    public ListFragment() {
        Log.e(LOG, "ListFragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(LOG, "onCreate");
        super.onCreate(savedInstanceState);
        presenter = new ModelListPresenter(this);
        presenter.executeRequest("Maksim");
        Bundle bundle = getArguments();
        if(bundle != null)
            setSavedRecyclerLayoutState(bundle.getParcelable(RECYCLER_LAYOUT_STATE));
        else if(savedInstanceState != null)
            setSavedRecyclerLayoutState(savedInstanceState.getParcelable(RECYCLER_LAYOUT_STATE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(LOG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new RecyclerViewAdapter(getActivity(), presenter.getUsers());
        adapter.setOnItemClickListener(presenter);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedRecyclerLayoutState != null && recyclerView != null)
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Parcelable savedRecyclerLayoutState = getSavedRecyclerLayoutState();
        outState.putParcelable(RECYCLER_LAYOUT_STATE, savedRecyclerLayoutState);
    }

    @Override
    public void onAttach(Context context) {
        Log.e(LOG, "onAttach");
        super.onAttach(context);
        try {
            onListFragmentInteractionListener = (OnListFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    public Parcelable getSavedRecyclerLayoutState() {
        if(recyclerView != null) {
            return recyclerView.getLayoutManager().onSaveInstanceState();
        }
        return null;
    }

    public void setSavedRecyclerLayoutState(Parcelable savedRecyclerLayoutState) {
        this.savedRecyclerLayoutState = savedRecyclerLayoutState;
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.updateElements(presenter.getUsers());
        adapter.notifyDataSetChanged();
        onListFragmentInteractionListener.setFirstElementOfList(presenter.getUsers().get(0).login);
    }

    @Override
    public void onItemClick(String userLogin) {
        onListFragmentInteractionListener.onListFragmentInteraction(userLogin);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(String userLogin);
        void setFirstElementOfList(String userLogin);
    }
}
