package com.example.maksim.testapp.details.fragment

import android.app.Fragment
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.maksim.testapp.R
import com.example.maksim.testapp.details.fragment.adapter.DetailsAdapter
import com.example.maksim.testapp.details.presenter.DetailsViewPresenterInterface
import com.example.maksim.testapp.details.model.data.GitHubUserDetails
import com.example.maksim.testapp.details.presenter.DetailsPresenter
import com.example.maksim.testapp.githubapi.room.RoomSqlDatabase

class DetailsFragment : Fragment(), DetailsViewInterface {
    override val selectedUserLogin: String
        get() = gitHubUser.toString()

    var gitHubUser: String? = null
        private set
    private var presenter: DetailsViewPresenterInterface? = null
    private var adapter: DetailsAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            gitHubUser = bundle.getString(SELECTED_MODEL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        swipeRefreshLayout = view.findViewById(R.id.swipeToRefresh)
        swipeRefreshLayout!!.setOnRefreshListener { updateContent(gitHubUser) }

        val roomSqlDatabase = Room.databaseBuilder(activity,
                RoomSqlDatabase::class.java, RoomSqlDatabase.DATABASE_NAME_GIT_HUB).build()
        presenter = DetailsPresenter(this, roomSqlDatabase)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        adapter = DetailsAdapter(activity)
        recyclerView!!.adapter = adapter
        return view
    }

    override fun updateView(gitHubUserDetails: GitHubUserDetails) {
        adapter!!.updateElements(gitHubUserDetails)
        adapter!!.notifyDataSetChanged()
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

    fun updateContent(userLogin: String?) {
        this.gitHubUser = userLogin
        if (presenter != null)
            presenter!!.onUserChanged()
        if (adapter != null) {
            adapter!!.cleanFields()
            adapter!!.notifyDataSetChanged()
        }
    }

    fun updateContentIfViewIsEmpty(userLogin: String) {
        if (adapter != null && this.gitHubUser == null)
            updateContent(userLogin)
    }

    companion object {
        val SELECTED_MODEL = "SELECTED_MODEL"
    }
}
