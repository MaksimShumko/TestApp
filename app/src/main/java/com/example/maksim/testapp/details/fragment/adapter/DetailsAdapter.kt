package com.example.maksim.testapp.details.fragment.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.maksim.testapp.R
import com.example.maksim.testapp.details.model.data.GitHubUserDetails

import java.util.ArrayList

/**
 * Created by Maksim on 2017-09-17.
 */

class DetailsAdapter(private val context: Context)//Log.e(LOG_TAG, "RecyclerViewAdapter");
    : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {
    private val LOG_TAG = "RecyclerViewAdapter"
    private var elements: MutableList<ElementsHolder>? = null

    fun updateElements(userDetails: GitHubUserDetails) {
        elements = ArrayList()
        elements!!.add(ElementsHolder(context.getString(R.string.user_name), userDetails.name))
        elements!!.add(ElementsHolder(context.getString(R.string.user_login), userDetails.login))
        elements!!.add(ElementsHolder(context.getString(R.string.user_bio), userDetails.bio))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Log.e(LOG_TAG, "onCreateViewHolder");
        val inflater = LayoutInflater.from(context)
        val item = inflater.inflate(R.layout.detail_item, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        //Log.e(LOG_TAG, "onBindViewHolder");
        if (holder != null && elements != null) {
            val elementsHolder = elements!![position]
            holder.title.text = elementsHolder.title
            holder.value.text = elementsHolder.value
        }
    }

    override fun getItemCount(): Int {
        //Log.e(LOG_TAG, "getItemCount");
        return if (elements != null) elements!!.size else 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.detailTitle)
        var value: TextView = itemView.findViewById(R.id.detailValue)
    }

    private inner class ElementsHolder internal constructor(internal var title: String?, internal var value: String?)

    fun cleanFields() {
        elements = null
    }
}

