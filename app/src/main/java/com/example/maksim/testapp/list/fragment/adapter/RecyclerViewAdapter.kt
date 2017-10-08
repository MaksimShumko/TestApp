package com.example.maksim.testapp.list.fragment.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.maksim.testapp.R
import com.example.maksim.testapp.list.model.data.GitHubUser
import com.example.maksim.testapp.list.fragment.OnItemClickListener
import com.example.maksim.testapp.list.presenter.ListViewPresenterInterface
import com.squareup.picasso.Picasso

import java.util.ArrayList

class RecyclerViewAdapter(private val context: Context, private val onItemClickListener: OnItemClickListener?,
                          private val presenter: ListViewPresenterInterface)//Log.e(LOG_TAG, "RecyclerViewAdapter");
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val LOG_TAG = "RecyclerViewAdapter"
    private var elements: MutableList<GitHubUser> = ArrayList()

    fun updateElements(elements: MutableList<GitHubUser>) {
        this.elements = elements
    }

    fun addElements(elements: MutableList<GitHubUser>) {
        this.elements.addAll(elements)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Log.e(LOG_TAG, "onCreateViewHolder");
        val inflater = LayoutInflater.from(context)
        val item = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Log.e(LOG_TAG, "onBindViewHolder");
        val element = elements[position]
        holder.login.text = element.login
        holder.name.text = element.eventsurl
        Picasso.with(context)
                .load(element.avatarurl)
                .into(holder.avatar)
        holder.score.text = element.id.toString()
        holder.itemView.setOnClickListener({onItemClickListener?.onItemClick(element.login!!)})

        if (position == itemCount - 1)
            presenter.isLastElement()
    }

    override fun getItemCount(): Int {
        //Log.e(LOG_TAG, "getItemCount");
        return elements.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var login: TextView = itemView.findViewById(R.id.listItemLogin)
        var name: TextView = itemView.findViewById(R.id.listItemName)
        var avatar: ImageView = itemView.findViewById(R.id.listItemAvatar)
        var score: TextView = itemView.findViewById(R.id.listItemScore)
    }
}
