package com.example.maksim.testapp.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.fragments.FormFragment;
import com.example.maksim.testapp.fragments.ListFragment;

public class MainActivity extends AppCompatActivity implements com.example.maksim.testapp.fragments.ListFragment.OnListFragmentInteractionListener {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("MainActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();

        ListFragment listFragment = ListFragment.newInstance();
        startFragment(listFragment, R.id.fragmentContainerLeft, true, false);

        boolean isLandTablet = getResources().getBoolean(R.bool.isLandTablet);
        if (isLandTablet) {
            showSecondFragment();
        } else {
            showOnlyFirstFragment();
        }
    }

    private void showSecondFragment() {
        FormFragment formFragment = FormFragment.newInstance();
        startFragment(formFragment, R.id.fragmentContainerRight, true, false);
    }

    private void showOnlyFirstFragment() {
        setLayoutWeight();

        FormFragment formFragment = (FormFragment) fragmentManager.findFragmentById(R.id.fragmentContainerRight);
        if (formFragment != null)
            stopFragment(formFragment);
    }

    private void setLayoutWeight() {
        FrameLayout frameLayoutLeft = (FrameLayout) findViewById(R.id.fragmentContainerLeft);
        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) frameLayoutLeft.getLayoutParams();
        param.weight = 1.0f;
        param.width = LinearLayout.LayoutParams.MATCH_PARENT;
        frameLayoutLeft.setLayoutParams(param);
    }

    private void startFragment(Fragment fragment, int fragmentContainer, boolean replace, boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (replace) {
            if (addToBackStack)
                transaction.replace(fragmentContainer, fragment).addToBackStack(null);
            else
                transaction.replace(fragmentContainer, fragment);
        } else {
            if (addToBackStack)
                transaction.add(fragmentContainer, fragment).addToBackStack(null);
            else
                transaction.add(fragmentContainer, fragment);
        }
        transaction.commitAllowingStateLoss();
    }

    private void stopFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onListFragmentInteraction(int position) {
        Log.e("MainActivity", "onListFragmentInteraction");
        onItemSelected(position);
    }

    private void onItemSelected(int position) {
        FormFragment formFragment = FormFragment.newInstance();
        formFragment.setPosition(position);

        boolean isLandTablet = getResources().getBoolean(R.bool.isLandTablet);
        if (!isLandTablet) {
            startFragment(formFragment, R.id.fragmentContainerLeft, true, true);
        } else {
            startFragment(formFragment, R.id.fragmentContainerRight, true, false);
        }
    }
}
