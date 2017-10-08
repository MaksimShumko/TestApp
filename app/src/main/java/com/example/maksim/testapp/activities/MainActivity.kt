package com.example.maksim.testapp.activities

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.os.Parcelable
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText

import com.example.maksim.testapp.R
import com.example.maksim.testapp.details.fragment.DetailsFragment
import com.example.maksim.testapp.list.fragment.ListFragment

class MainActivity : AppCompatActivity(), ListFragment.OnListFragmentInteractionListener {

    private val LOG = "MainActivity"
    private val fragmentManagerVal: FragmentManager = fragmentManager

    private var actionBar: ActionBar? = null
    private var isLandTablet: Boolean = false
    private var savedGitHubUserData: String? = null
    private var savedRecyclerLayoutState: Parcelable? = null
    private var menuSearchUsers: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e(LOG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        isLandTablet = resources.getBoolean(R.bool.isLandTablet)
        actionBar = supportActionBar
        setFragmentManagerListener()

        if (savedInstanceState == null && !isLandTablet) {
            val listFragment = ListFragment()
            startFragment(listFragment, R.id.fragmentContainer, true, false)
        } else if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState
                    .getParcelable(ListFragment.RECYCLER_LAYOUT_STATE)
            savedGitHubUserData = savedInstanceState
                    .getString(DetailsFragment.SELECTED_MODEL)

            if (!isLandTablet) {
                updatePortraitTabletAndPhoneState()
            } else {
                updateLandTabletState()
            }
        }

        setActionBarTitle()
    }

    private fun setFragmentManagerListener() {
        fragmentManagerVal.addOnBackStackChangedListener { setMenuBackButton() }
    }

    private fun updateLandTabletState() {
        clearBackStack()

        val listFragment = fragmentManagerVal
                .findFragmentById(R.id.listFragment) as ListFragment
        listFragment.setSavedRecyclerLayoutState(savedRecyclerLayoutState)

        val detailsFragment = fragmentManagerVal
                .findFragmentById(R.id.detailsFragment) as DetailsFragment
        detailsFragment.updateContent(savedGitHubUserData)
    }

    private fun updatePortraitTabletAndPhoneState() {
        val fragment = fragmentManagerVal.findFragmentById(R.id.fragmentContainer)
        if (fragment != null) {
            (fragment as? ListFragment)?.setSavedRecyclerLayoutState(savedRecyclerLayoutState) ?: (fragment as? DetailsFragment)?.updateContent(savedGitHubUserData)
        } else {
            val bundle = Bundle()
            bundle.putParcelable(ListFragment.RECYCLER_LAYOUT_STATE, savedRecyclerLayoutState)

            val listFragment = ListFragment()
            listFragment.arguments = bundle
            startFragment(listFragment, R.id.fragmentContainer, true, false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (isLandTablet) {
            saveStateFromLandTablet(outState)
        } else {
            saveStateFromPortraitTabletAndPhone(outState)
        }
    }

    private fun saveStateFromLandTablet(outState: Bundle?) {
        val listFragment = fragmentManagerVal.findFragmentById(R.id.listFragment) as ListFragment?
        if (listFragment != null) {
            savedRecyclerLayoutState = listFragment.getSavedRecyclerLayoutState()
        }
        outState!!.putParcelable(ListFragment.RECYCLER_LAYOUT_STATE, savedRecyclerLayoutState)

        val detailsFragment = fragmentManagerVal
                .findFragmentById(R.id.detailsFragment) as DetailsFragment?
        if (detailsFragment != null) {
            val savedGitHubUserState = detailsFragment.gitHubUser
            outState.putString(DetailsFragment.SELECTED_MODEL, savedGitHubUserState)
        }
    }

    private fun saveStateFromPortraitTabletAndPhone(outState: Bundle?) {
        val fragment = fragmentManagerVal.findFragmentById(R.id.fragmentContainer)
        if (fragment != null) {
            if (fragment is ListFragment) {
                savedRecyclerLayoutState = fragment.getSavedRecyclerLayoutState()
            } else if (fragment is DetailsFragment) {
                savedGitHubUserData = fragment.gitHubUser
            }

            outState!!.putParcelable(ListFragment.RECYCLER_LAYOUT_STATE, savedRecyclerLayoutState)
            outState.putString(DetailsFragment.SELECTED_MODEL, savedGitHubUserData)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        menuSearchUsers = menu.findItem(R.id.menu_search_users)

        if (!isLandTablet) {
            setMenuBackButton()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> if (fragmentManagerVal.backStackEntryCount > 0) {
                fragmentManagerVal.popBackStack()
            }
            R.id.menu_search_users -> showEditQueryDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showEditQueryDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.edit_dialog, null)
        dialogBuilder.setView(dialogView)

        val editText = dialogView.findViewById<View>(R.id.editText) as EditText
        val prefs = getSharedPreferences(ListFragment.PREFERENCE_SEARCH_QUERY, Context.MODE_PRIVATE)
        val searchQuery = prefs.getString(ListFragment.SEARCH_QUERY, "")
        editText.append(searchQuery)

        dialogBuilder.setTitle(getString(R.string.edit_dialog_title))
        dialogBuilder.setPositiveButton("Done") { _, _ ->
            saveQueryText(editText.text.toString())
        }
        dialogBuilder.setNegativeButton("Cancel") { _, _ -> }
        val b = dialogBuilder.create()
        b.show()
    }

    private fun saveQueryText(searchQuery: String) {
        val editor = getSharedPreferences(ListFragment.PREFERENCE_SEARCH_QUERY, Context.MODE_PRIVATE).edit()
        editor.putString(ListFragment.SEARCH_QUERY, searchQuery)
        editor.apply()

        if (fragmentManagerVal.backStackEntryCount > 0) {
            fragmentManagerVal.popBackStack()
        }

        var listFragment: ListFragment? = null
        if (!isLandTablet) {
            val fragment = fragmentManagerVal.findFragmentById(R.id.fragmentContainer)
            if (fragment != null && fragment is ListFragment)
                listFragment = fragment
        } else {
            listFragment = fragmentManagerVal
                    .findFragmentById(R.id.listFragment) as ListFragment
        }

        if (listFragment != null)
            listFragment.onSearchQueryChanged()
    }

    private fun setMenuBackButton() {
        if (actionBar != null) {
            val backStackCount = fragmentManagerVal.backStackEntryCount
            val enableBackButton = backStackCount > 0
            actionBar!!.setHomeButtonEnabled(enableBackButton)
            actionBar!!.setDisplayHomeAsUpEnabled(enableBackButton)
        }

        setActionBarTitle()
    }

    private fun setActionBarTitle() {
        if (isLandTablet) {
            setActionBarTitle(getString(R.string.menu_title_list_and_details))
        } else {
            val fragment = fragmentManagerVal.findFragmentById(R.id.fragmentContainer)
            if (fragment is DetailsFragment) {
                setActionBarTitle(getString(R.string.menu_title_details))
            } else {
                setActionBarTitle(getString(R.string.menu_title_list))
            }
        }
    }

    private fun setActionBarTitle(title: String) {
        Log.e(LOG, "setActionBarTitle " + title)
        if (actionBar != null)
            actionBar!!.title = title
    }

    private fun startFragment(fragment: Fragment, fragmentContainer: Int, replace: Boolean, addToBackStack: Boolean) {
        var transaction = fragmentManagerVal.beginTransaction()
        transaction = if (replace)
            transaction.replace(fragmentContainer, fragment)
        else
            transaction.add(fragmentContainer, fragment)
        if (addToBackStack)
            transaction.addToBackStack(null)
        transaction.commitAllowingStateLoss()
    }

    private fun clearBackStack() {
        if (fragmentManagerVal.backStackEntryCount > 0) {
            val backStackEntry = fragmentManagerVal.getBackStackEntryAt(0)
            fragmentManagerVal.popBackStack(backStackEntry.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onListFragmentInteraction(userLogin: String) {
        Log.e(LOG, "onListFragmentInteraction")
        savedGitHubUserData = userLogin
        onItemSelected(userLogin)
    }

    override fun setFirstElementOfList(userLogin: String?) {
        val detailsFragment = fragmentManagerVal
                .findFragmentById(R.id.detailsFragment) as DetailsFragment?
        if (detailsFragment != null && isLandTablet)
            detailsFragment.updateContentIfViewIsEmpty(userLogin!!)
    }

    private fun onItemSelected(userLogin: String) {
        var detailsFragment: DetailsFragment? = fragmentManagerVal
                .findFragmentById(R.id.detailsFragment) as DetailsFragment
        val bundle = Bundle()
        bundle.putString(DetailsFragment.SELECTED_MODEL, userLogin)
        if (detailsFragment == null || !isLandTablet) {
            val listFragment = fragmentManagerVal.findFragmentById(R.id.listFragment) as ListFragment?
            if (listFragment != null)
                savedRecyclerLayoutState = listFragment.getSavedRecyclerLayoutState()

            detailsFragment = DetailsFragment()
            detailsFragment.arguments = bundle
            startFragment(detailsFragment, R.id.fragmentContainer, false, true)

            setActionBarTitle(getString(R.string.menu_title_details))
        } else {
            detailsFragment.updateContent(userLogin)
        }
    }
}
