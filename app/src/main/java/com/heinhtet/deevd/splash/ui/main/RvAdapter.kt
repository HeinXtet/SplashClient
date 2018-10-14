package com.heinhtet.deevd.splash.ui.main

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.heinhtet.deevd.splash.R
import com.heinhtet.deevd.splash.extensions.load
import com.heinhtet.deevd.splash.model.response.Movie
import com.heinhtet.deevd.splash.network.NetworkState
import com.heinhtet.deevd.splash.ui.main.viewholder.NetworkStateViewHolder

/**
 * Created by Hein Htet on 8/22/18.
 */

class RvAdapter(private val retryCallback: () -> Unit) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.movie_items -> RVH.create(parent, retryCallback)
            R.layout.item_network_state -> NetworkStateViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.movie_items -> (holder as RVH).onBind(getItem(position)!!)
            R.layout.item_network_state -> (holder as NetworkStateViewHolder).bindTo(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }


    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }


    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.movie_items
        }
    }

    /**
     * Set the current network state to the adapter
     * but this work only after the initial load
     * and the adapter already have list to add new loading raw to it
     * so the initial loading state the activity responsible for handle it
     *
     * @param newNetworkState the new network state
     */
    fun setNetworkState(newNetworkState: NetworkState?) {
        if (currentList != null) {
            if (currentList!!.size != 0) {
                val hadExtraRow = hasExtraRow()
                this.networkState = newNetworkState
                if (hadExtraRow) {
                    notifyItemRemoved(super.getItemCount())
                } else {
                    notifyItemInserted(super.getItemCount())
                }
            }
        }
    }

    class RVH(itemView: View, retryCallback: () -> Unit) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.title_tv)
        lateinit var title: TextView
        @BindView(R.id.iv)
        lateinit var iv: AppCompatImageView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun onBind(item: Movie) {
            title.text = item.title
            iv.load(item.posterPath)

        }

        companion object {
            fun create(parent: ViewGroup, retryCallback: () -> Unit): RVH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.movie_items, parent, false)
                return RVH(view, retryCallback)
            }
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                    oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                    oldItem == newItem
        }
    }

}