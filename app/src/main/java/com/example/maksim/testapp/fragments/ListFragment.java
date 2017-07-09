package com.example.maksim.testapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.fragments.dummy.DummyContent;
import com.example.maksim.testapp.fragments.dummy.DummyContent.DummyItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ListFragment extends Fragment {

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("ListFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        if (view instanceof ListView) {
            ListView listView = (ListView) view;

            ListViewAdapter adapter = new ListViewAdapter(getContext(), DummyContent.ITEMS,
                    onListFragmentInteractionListener);
            listView.setAdapter(adapter);

        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        Log.e("ListFragment", "onAttach");
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            onListFragmentInteractionListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        Log.e("ListFragment", "onDetach");
        super.onDetach();
        onListFragmentInteractionListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item);
    }

    public DummyItem getItem(int itemNum) {
        return DummyContent.ITEMS.get(itemNum);
    }
}
