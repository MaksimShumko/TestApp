package com.example.maksim.testapp.list.fragment

import android.app.Fragment
import android.arch.persistence.room.Room
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.maksim.testapp.R
import com.example.maksim.testapp.list.presenter.ListViewPresenter
import com.example.maksim.testapp.list.presenter.ListViewPresenterInterface
import com.example.maksim.testapp.githubapi.room.RoomSqlDatabase
import com.example.maksim.testapp.list.fragment.adapter.RecyclerViewAdapter
import com.example.maksim.testapp.list.model.data.GitHubUser
import com.example.maksim.testapp.utils.InternetUtils

import android.content.Context.MODE_PRIVATE


class ListFragment : Fragment(), ListViewInterface, OnItemClickListener {
    private val LOG = "ListFragment"
    private var presenter: ListViewPresenterInterface? = null
    private var adapter: RecyclerViewAdapter? = null
    private var onListFragmentInteractionListener: OnListFragmentInteractionListener? = null
    private var recyclerView: RecyclerView? = null
    private var savedRecyclerLayoutState: Parcelable? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    override val prefSearchQuery: String
        get() {
            val prefs = activity
                    .getSharedPreferences(PREFERENCE_SEARCH_QUERY, MODE_PRIVATE)
            return prefs.getString(SEARCH_QUERY, null)
        }

    override val isNetworkAvailable: Boolean
        get() = InternetUtils.isNetworkAvailable(activity)

    init {
        Log.e(LOG, "ListFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e(LOG, "onCreate")
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null)
            setSavedRecyclerLayoutState(bundle.getParcelable(RECYCLER_LAYOUT_STATE))
        else if (savedInstanceState != null)
            setSavedRecyclerLayoutState(savedInstanceState.getParcelable(RECYCLER_LAYOUT_STATE))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.e(LOG, "onCreateView")
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        swipeRefreshLayout = view.findViewById<View>(R.id.swipeToRefresh) as SwipeRefreshLayout
        swipeRefreshLayout!!.setOnRefreshListener { presenter!!.onSwipeRefresh() }

        val roomSqlDatabase = Room.databaseBuilder(activity,
                RoomSqlDatabase::class.java, RoomSqlDatabase.DATABASE_NAME_GIT_HUB).build()
        presenter = ListViewPresenter(this, roomSqlDatabase)

        recyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.addItemDecoration(DividerItemDecoration(
                recyclerView!!.context, DividerItemDecoration.VERTICAL))
        adapter = RecyclerViewAdapter(activity, this, presenter!!)
        recyclerView!!.adapter = adapter
        return view
    }

    override fun onAttach(context: Context) {
        Log.e(LOG, "onAttach")
        super.onAttach(context)
        try {
            onListFragmentInteractionListener = activity as OnListFragmentInteractionListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnListFragmentInteractionListener")
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        val savedRecyclerLayoutState = getSavedRecyclerLayoutState()
        outState.putParcelable(RECYCLER_LAYOUT_STATE, savedRecyclerLayoutState)
        super.onSaveInstanceState(outState)
    }

    fun getSavedRecyclerLayoutState(): Parcelable? {
        return if (recyclerView != null) {
            recyclerView!!.layoutManager.onSaveInstanceState()
        } else null
    }

    fun setSavedRecyclerLayoutState(savedRecyclerLayoutState: Parcelable?) {
        this.savedRecyclerLayoutState = savedRecyclerLayoutState
    }

    fun onSearchQueryChanged() {
        if (presenter != null) {
            presenter!!.onSearchQueryChanged()
        }
    }

    override fun onDataChanged(gitHubUser: MutableList<GitHubUser>, addElements: Boolean) {
        if (addElements)
            adapter!!.addElements(gitHubUser)
        else
            adapter!!.updateElements(gitHubUser)
        adapter!!.notifyDataSetChanged()

        if (gitHubUser.isNotEmpty())
            onListFragmentInteractionListener!!.setFirstElementOfList(gitHubUser[0].login)

        if (addElements)
            savedRecyclerLayoutState = null
        if (savedRecyclerLayoutState != null && recyclerView != null)
            recyclerView!!.layoutManager.onRestoreInstanceState(savedRecyclerLayoutState)
    }

    override fun showNetworkErrorToast() {
        Toast.makeText(activity, activity.getString(R.string.network_not_available),
                Toast.LENGTH_LONG).show()
    }

    override fun showRequestErrorToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun startSwipeRefresh() {
        swipeRefreshLayout!!.isRefreshing = true
    }

    override fun stopSwipeRefresh() {
        swipeRefreshLayout!!.isRefreshing = false
    }

    override fun onItemClick(login: String) {
        onListFragmentInteractionListener!!.onListFragmentInteraction(login)
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(userLogin: String)
        fun setFirstElementOfList(userLogin: String?)
    }

    companion object {
        val RECYCLER_LAYOUT_STATE = "RECYCLER_LAYOUT_STATE"
        val PREFERENCE_SEARCH_QUERY = "PREFERENCE_SEARCH_QUERY"
        val SEARCH_QUERY = "SEARCH_QUERY"
    }
}
