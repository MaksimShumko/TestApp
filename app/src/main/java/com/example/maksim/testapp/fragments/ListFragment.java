package com.example.maksim.testapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.activities.MainActivity;
import com.example.maksim.testapp.contracts.ModelListViewContract;
import com.example.maksim.testapp.presenters.ModelListPresenter;

import java.util.List;


public class ListFragment extends Fragment implements ModelListViewContract.View {

    private ModelListPresenter presenter;
    private ListViewAdapter adapter;

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

        if (view instanceof ListView) {
            ListView listView = (ListView) view;
            adapter = new ListViewAdapter(getContext(), presenter);
            adapter.setOnItemClickListener(presenter);
            listView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        Log.e("ListFragment", "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.e("ListFragment", "onDetach");
        super.onDetach();
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        MainActivity activity = (MainActivity) getActivity();
        activity.onListFragmentInteraction(position);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(int position);
    }
}
