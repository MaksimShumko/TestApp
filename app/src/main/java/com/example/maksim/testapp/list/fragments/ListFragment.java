package com.example.maksim.testapp.list.fragments;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.maksim.testapp.list.presenters.ListPresenterInterface;
import com.example.maksim.testapp.room.RoomSqlDatabase;
import com.example.maksim.testapp.list.adapters.RecyclerViewAdapter;
import com.example.maksim.testapp.list.data.GitHubUser;
import com.example.maksim.testapp.list.presenters.ListPresenter;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class ListFragment extends Fragment implements ViewInterface {

    public static final String RECYCLER_LAYOUT_STATE = "RECYCLER_LAYOUT_STATE";
    public static final String PREFERENCE_SEARCH_QUERY = "PREFERENCE_SEARCH_QUERY";
    public static final String SEARCH_QUERY = "SEARCH_QUERY";
    private final String LOG = "ListFragment";
    private ListPresenterInterface presenter;
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

        RoomSqlDatabase roomSqlDatabase = Room.databaseBuilder(getActivity(),
                RoomSqlDatabase.class, RoomSqlDatabase.DATABASE_NAME_GIT_HUB).build();
        presenter = new ListPresenter(this, roomSqlDatabase);

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

    public void executeRequest() {
        if (presenter != null) {
            presenter.executeSearchRequest(getPrefSearchQuery());
        }
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.updateElements(presenter.getUsers());
        adapter.notifyDataSetChanged();
        List<GitHubUser> users = presenter.getUsers();
        if (users != null && users.size() > 0)
            onListFragmentInteractionListener.setFirstElementOfList(users.get(0).login);
    }

    @Override
    public void onItemClick(String userLogin) {
        onListFragmentInteractionListener.onListFragmentInteraction(userLogin);
    }

    @Override
    public String getPrefSearchQuery() {
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(PREFERENCE_SEARCH_QUERY, MODE_PRIVATE);
        return prefs.getString(SEARCH_QUERY, null);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(String userLogin);
        void setFirstElementOfList(String userLogin);
    }
}