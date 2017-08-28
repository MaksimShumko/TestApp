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
import com.example.maksim.testapp.github.InitRetrofit;
import com.example.maksim.testapp.models.GitHubUser;

public class MainActivity extends AppCompatActivity
        implements com.example.maksim.testapp.fragments.ListFragment.OnListFragmentInteractionListener {

    private final String LOG = "MainActivity";
    private FragmentManager fragmentManager;
    private ActionBar actionBar;
    private boolean isLandTablet;
    private GitHubUser savedGitHubUserState;
    private Parcelable savedRecyclerLayoutState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(LOG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        isLandTablet = getResources().getBoolean(R.bool.isLandTablet);
        fragmentManager = getFragmentManager();

        actionBar = getSupportActionBar();
        setFragmentManagerListener();

        if(savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState
                    .getParcelable(ListFragment.RECYCLER_LAYOUT_STATE);
            savedGitHubUserState = savedInstanceState
                    .getParcelable(DetailsFragment.SELECTED_MODEL);
        }

        if (savedInstanceState == null && !isLandTablet) {
            ListFragment listFragment = new ListFragment();
            startFragment(listFragment, R.id.fragmentContainer, true, false);
        } else if(savedInstanceState != null) {
            if (!isLandTablet) {
                updatePortraitTabletAndPhoneState();
            } else {
                updateLandTabletState();
            }
        }

        setActionBarTitle();
    }

    private void updateLandTabletState() {
        clearBackStack();

        ListFragment listFragment = (ListFragment) fragmentManager
                .findFragmentById(R.id.list_fragment);
        if (listFragment != null) {
            listFragment.setSavedRecyclerLayoutState(savedRecyclerLayoutState);
        }

        DetailsFragment detailsFragment = (DetailsFragment) fragmentManager
                .findFragmentById(R.id.details_fragment);
        if (detailsFragment != null) {
            detailsFragment.updateContent(savedGitHubUserState);
        }
    }

    private void updatePortraitTabletAndPhoneState() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if (fragment != null) {
            if(fragment instanceof ListFragment) {
                ((ListFragment) fragment).setSavedRecyclerLayoutState(savedRecyclerLayoutState);
            } else if(fragment instanceof DetailsFragment) {
                ((DetailsFragment) fragment).updateContent(savedGitHubUserState);
            }
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ListFragment.RECYCLER_LAYOUT_STATE, savedRecyclerLayoutState);

            ListFragment listFragment = new ListFragment();
            listFragment.setArguments(bundle);
            startFragment(listFragment, R.id.fragmentContainer, true, false);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if(isLandTablet) {
            saveStateFromLandTablet(outState);
        } else {
            saveStateFromPortraitTabletAndPhone(outState);
        }
    }

    private void saveStateFromLandTablet(Bundle outState) {
        ListFragment listFragment = (ListFragment) fragmentManager.findFragmentById(R.id.list_fragment);
        if (listFragment != null) {
            savedRecyclerLayoutState = listFragment.getSavedRecyclerLayoutState();
        }
        outState.putParcelable(ListFragment.RECYCLER_LAYOUT_STATE, savedRecyclerLayoutState);

        DetailsFragment detailsFragment = (DetailsFragment) fragmentManager
                .findFragmentById(R.id.details_fragment);
        if (detailsFragment != null) {
            GitHubUser savedGitHubUserState = detailsFragment.getGitHubUser();
            outState.putParcelable(DetailsFragment.SELECTED_MODEL, savedGitHubUserState);
        }
    }

    private void saveStateFromPortraitTabletAndPhone(Bundle outState) {
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if (fragment != null) {
            if(fragment instanceof ListFragment) {
                savedRecyclerLayoutState = ((ListFragment) fragment).getSavedRecyclerLayoutState();
            } else if(fragment instanceof DetailsFragment) {
                savedGitHubUserState = ((DetailsFragment) fragment).getGitHubUser();
            }

            outState.putParcelable(ListFragment.RECYCLER_LAYOUT_STATE, savedRecyclerLayoutState);
            outState.putParcelable(DetailsFragment.SELECTED_MODEL, savedGitHubUserState);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                }
                break;
            case R.id.menu_execute_git_hub:
                InitRetrofit init = new InitRetrofit();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragmentManagerListener() {
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                setMenuBackButton();
            }
        });
    }

    private void setMenuBackButton() {
        if(actionBar != null) {
            int backStackCount = fragmentManager.getBackStackEntryCount();
            boolean enableBackButton = backStackCount > 0;
            actionBar.setHomeButtonEnabled(enableBackButton);
            actionBar.setDisplayHomeAsUpEnabled(enableBackButton);
        }

        setActionBarTitle();
    }

    private void setActionBarTitle() {
        if(isLandTablet) {
            setActionBarTitle(getString(R.string.menu_title_list_and_details));
        } else {
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
            if (fragment instanceof DetailsFragment) {
                setActionBarTitle(getString(R.string.menu_title_details));
            } else {
                setActionBarTitle(getString(R.string.menu_title_list));
            }
        }
    }

    private void setActionBarTitle(String title) {
        Log.e(LOG, "setActionBarTitle " + title);
        if(actionBar != null)
            actionBar.setTitle(title);
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

    private void clearBackStack() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(backStackEntry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onListFragmentInteraction(GitHubUser gitHubUser) {
        Log.e(LOG, "onListFragmentInteraction");
        savedGitHubUserState = gitHubUser;
        onItemSelected(gitHubUser);
    }

    private void onItemSelected(GitHubUser gitHubUser) {
        DetailsFragment detailsFragment = (DetailsFragment) fragmentManager
                .findFragmentById(R.id.details_fragment);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsFragment.SELECTED_MODEL, gitHubUser);
        if (detailsFragment == null || !isLandTablet) {
            ListFragment listFragment = (ListFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
            if(listFragment != null)
                savedRecyclerLayoutState = listFragment.getSavedRecyclerLayoutState();

            detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(bundle);
            startFragment(detailsFragment, R.id.fragmentContainer, false, true);

            setActionBarTitle(getString(R.string.menu_title_details));
        } else {
            detailsFragment.updateContent(gitHubUser);
        }
    }
}
