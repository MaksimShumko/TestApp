package com.example.maksim.testapp.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.fragments.DetailsFragment;
import com.example.maksim.testapp.fragments.ListFragment;
import com.example.maksim.testapp.models.Model;

public class MainActivity extends AppCompatActivity
        implements com.example.maksim.testapp.fragments.ListFragment.OnListFragmentInteractionListener {

    private FragmentManager fragmentManager;
    private ActionBar actionBar;
    private boolean isLandTablet;
    private Model savedModelState;
    private Parcelable savedRecyclerLayoutState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("MainActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        isLandTablet = getResources().getBoolean(R.bool.isLandTablet);
        fragmentManager = getFragmentManager();

        actionBar = getSupportActionBar();
        setFragmentManagerListener();

        if(!isLandTablet) {

            if (savedInstanceState == null) {
                ListFragment listFragment = new ListFragment();
                startFragment(listFragment, R.id.fragmentContainer, true, false);

                setActionBarTitle(getString(R.string.menu_title_list));
            } else {
                savedRecyclerLayoutState = savedInstanceState
                        .getParcelable(ListFragment.RECYCLER_LAYOUT_STATE);
                savedModelState = savedInstanceState
                        .getParcelable(DetailsFragment.SELECTED_MODEL);

                Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
                if (fragment != null) {
                    if(fragment instanceof ListFragment) {
                        ((ListFragment) fragment).setSavedRecyclerLayoutState(savedRecyclerLayoutState);

                        setActionBarTitle(getString(R.string.menu_title_list));
                    } else if(fragment instanceof DetailsFragment) {
                        ((DetailsFragment) fragment).updateContent(savedModelState);

                        setActionBarTitle(getString(R.string.menu_title_details));
                    }
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ListFragment.RECYCLER_LAYOUT_STATE, savedRecyclerLayoutState);

                    ListFragment listFragment = new ListFragment();
                    listFragment.setArguments(bundle);
                    startFragment(listFragment, R.id.fragmentContainer, true, false);

                    setActionBarTitle(getString(R.string.menu_title_list));
                }
            }

        } else {

            if(savedInstanceState != null) {
                savedRecyclerLayoutState = savedInstanceState
                        .getParcelable(ListFragment.RECYCLER_LAYOUT_STATE);
                savedModelState = savedInstanceState
                        .getParcelable(DetailsFragment.SELECTED_MODEL);

                ListFragment listFragment = (ListFragment) fragmentManager
                        .findFragmentById(R.id.list_fragment);
                if (listFragment != null) {
                    listFragment.setSavedRecyclerLayoutState(savedRecyclerLayoutState);
                }

                DetailsFragment detailsFragment = (DetailsFragment) fragmentManager
                        .findFragmentById(R.id.details_fragment);
                if (detailsFragment != null) {
                    detailsFragment.updateContent(savedModelState);
                }

                setActionBarTitle(getString(R.string.menu_title_list_and_details));
            }

        }
    }

    private void startFragment(Fragment fragment, int fragmentContainer, boolean replace, boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (replace) {
            transaction = transaction.replace(fragmentContainer, fragment);
        } else {
            transaction = transaction.add(fragmentContainer, fragment);
        }
        if(addToBackStack)
            transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    private void setFragmentManagerListener() {
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                setMenuBackButton();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if(isLandTablet) {

            ListFragment listFragment = (ListFragment) fragmentManager.findFragmentById(R.id.list_fragment);
            if (listFragment != null) {
                savedRecyclerLayoutState = listFragment.getSavedRecyclerLayoutState();
            }
            outState.putParcelable(ListFragment.RECYCLER_LAYOUT_STATE, savedRecyclerLayoutState);

            DetailsFragment detailsFragment = (DetailsFragment) fragmentManager
                    .findFragmentById(R.id.details_fragment);
            if (detailsFragment != null) {
                Model savedModelState = detailsFragment.getModel();
                outState.putParcelable(DetailsFragment.SELECTED_MODEL, savedModelState);
            }

        } else {

            Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
            if (fragment != null) {
                if(fragment instanceof ListFragment) {
                    savedRecyclerLayoutState = ((ListFragment) fragment).getSavedRecyclerLayoutState();
                } else if(fragment instanceof DetailsFragment) {
                    savedModelState = ((DetailsFragment) fragment).getModel();
                }

                outState.putParcelable(ListFragment.RECYCLER_LAYOUT_STATE, savedRecyclerLayoutState);
                outState.putParcelable(DetailsFragment.SELECTED_MODEL, savedModelState);
            }

        }
    }

    @Override
    public void onListFragmentInteraction(Model model) {
        Log.e("MainActivity", "onListFragmentInteraction");
        savedModelState = model;
        onItemSelected(model);
    }

    private void onItemSelected(Model model) {
        DetailsFragment detailsFragment = (DetailsFragment) fragmentManager
                .findFragmentById(R.id.details_fragment);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsFragment.SELECTED_MODEL, model);
        if (detailsFragment == null || !isLandTablet) {
            ListFragment listFragment = (ListFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
            if(listFragment != null)
                savedRecyclerLayoutState = listFragment.getSavedRecyclerLayoutState();

            detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(bundle);
            startFragment(detailsFragment, R.id.fragmentContainer, false, true);

            setActionBarTitle(getString(R.string.menu_title_details));
        } else {
            detailsFragment.updateContent(model);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        if(!isLandTablet) {
            setMenuBackButton();
        }
        return true;
    }

    private void setMenuBackButton() {
        if(actionBar != null) {
            int backStackCount = fragmentManager.getBackStackEntryCount();
            boolean enableBackButton = backStackCount > 0;
            actionBar.setHomeButtonEnabled(enableBackButton);
            actionBar.setDisplayHomeAsUpEnabled(enableBackButton);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();

                    setActionBarTitle(getString(R.string.menu_title_list));
                }
                break;
            case R.id.menu_go_back:
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void setActionBarTitle(String title) {
        Log.e("MainActivity", "setActionBarTitle " + title);
        if(actionBar != null)
            actionBar.setTitle(title);
    }
}
