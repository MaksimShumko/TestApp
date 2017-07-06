package com.example.maksim.testapp.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.maksim.testapp.R;
import com.example.maksim.testapp.fragments.FormFragment;
import com.example.maksim.testapp.fragments.ListFragment;
import com.example.maksim.testapp.fragments.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements com.example.maksim.testapp.fragments.ListFragment.OnListFragmentInteractionListener {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("MainActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        ListFragment listFragment = ListFragment.newInstance(1);
        startFragment(listFragment, R.id.fragmentContainerLeft, true, false);

        boolean isLandTablet = getResources().getBoolean(R.bool.isLandTablet);
        Log.e("MainActivity", "onCreate isLandTablet " + String.valueOf(isLandTablet));
        if (isLandTablet) {
            /*FormFragment formFragment = new FormFragment();
            formFragment.setParam(listFragment.getItem(0));
            startFragment(formFragment, R.id.fragmentContainerRight, true, false);*/
            onItemSelected(listFragment.getItem(0));
        } else {
            FormFragment formFragment = (FormFragment) fragmentManager.findFragmentById(R.id.fragmentContainerRight);
            if (formFragment != null)
                stopFragment(formFragment);
        }
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
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Log.e("MainActivity", "onListFragmentInteraction");
        onItemSelected(item);
    }

    private void onItemSelected(DummyContent.DummyItem item) {
        FormFragment formFragment = new FormFragment();
        formFragment.setParam(item);

        boolean isLandTablet = getResources().getBoolean(R.bool.isLandTablet);
        if (!isLandTablet) {
            startFragment(formFragment, R.id.fragmentContainerLeft, true, true);
        } else {
            startFragment(formFragment, R.id.fragmentContainerRight, true, false);
        }
    }
}
