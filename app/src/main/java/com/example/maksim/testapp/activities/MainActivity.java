package com.example.maksim.testapp.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.fragments.FormFragment;
import com.example.maksim.testapp.fragments.ListFragment;
import com.example.maksim.testapp.models.Model;

public class MainActivity extends AppCompatActivity implements com.example.maksim.testapp.fragments.ListFragment.OnListFragmentInteractionListener {

    private FragmentManager fragmentManager;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("MainActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null)
            init();
    }

    private void init() {
        actionBar = getSupportActionBar();
        setFragmentManagerListener();

        ListFragment listFragment = null;
        if(fragmentManager.findFragmentById(R.id.fragmentContainerLeft) instanceof ListFragment)
            listFragment = (ListFragment) fragmentManager.findFragmentById(R.id.fragmentContainerLeft);
        if(listFragment == null) {
            listFragment = ListFragment.newInstance();
            startFragment(listFragment, R.id.fragmentContainerLeft, true, false);
        }

        boolean isLandTablet = getResources().getBoolean(R.bool.isLandTablet);
        if (isLandTablet) {
            showSecondFragment();
        } else {
            stopFragmentIfExist();
        }
    }

    private void showSecondFragment() {
        FormFragment formFragment = (FormFragment) fragmentManager.findFragmentById(R.id.fragmentContainerRight);
        if(formFragment == null) {
            formFragment = FormFragment.newInstance();
            startFragment(formFragment, R.id.fragmentContainerRight, true, false);
        }
    }

    private void stopFragmentIfExist() {
        //setLayoutWeight();

        FormFragment formFragment = (FormFragment) fragmentManager.findFragmentById(R.id.fragmentContainerRight);
        if (formFragment != null) {
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragmentContainerRight);
            frameLayout.setVisibility(View.GONE);
        }
            //stopFragment(formFragment);
    }

    private void setLayoutWeight() {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragmentContainerLeft);
        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
        param.weight = 1.0f;
        param.width = LinearLayout.LayoutParams.MATCH_PARENT;
        frameLayout.setLayoutParams(param);
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

    private void stopFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commitAllowingStateLoss();
    }

    private void setFragmentManagerListener() {
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                if(actionBar != null) {
                    boolean enableBackButton = backStackCount > 0;
                    actionBar.setHomeButtonEnabled(enableBackButton);
                    actionBar.setDisplayHomeAsUpEnabled(enableBackButton);
                }
            }
        });
    }

    @Override
    public void onListFragmentInteraction(Model model) {
        Log.e("MainActivity", "onListFragmentInteraction");
        onItemSelected(model);
    }

    private void onItemSelected(Model model) {
        FormFragment formFragment = FormFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", model);
        formFragment.setArguments(bundle);

        boolean isLandTablet = getResources().getBoolean(R.bool.isLandTablet);
        if (!isLandTablet) {
            startFragment(formFragment, R.id.fragmentContainerLeft, true, true);
        } else {
            startFragment(formFragment, R.id.fragmentContainerRight, true, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                }
            case R.id.menu_go_back:
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBarTitle(String title) {
        Log.e("MainActivity", "setActionBarTitle " + title);
        if(actionBar != null)
            actionBar.setTitle(title);
    }
}
