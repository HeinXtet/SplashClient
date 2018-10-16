package com.heinhtet.deevd.splash.ui.home_fragment.adapter

import android.arch.paging.PagedListAdapter
import android.os.Handler
import android.support.v7.util.DiffUtil
import android.support.v7.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.heinhtet.deevd.splash.R
import com.heinhtet.deevd.splash.extensions.h
import com.heinhtet.deevd.splash.extensions.leftToRight
import com.heinhtet.deevd.splash.extensions.load
import com.heinhtet.deevd.splash.extensions.s
import com.heinhtet.deevd.splash.model.response.PhotoModel
import com.heinhtet.deevd.splash.network.NetworkState
import com.heinhtet.deevd.splash.ui.main.viewholder.NetworkStateViewHolder
import com.heinhtet.deevd.splash.utils.log.L

/**
 * Created by Hein Htet on 8/22/18.
 */

class PhotoAdapter(private val retryCallback: () -> Unit) : PagedListAdapter<PhotoModel, RecyclerView.ViewHolder>(PHOTO_COMPARATOR) {
    private var networkState: NetworkState? = null
    private val TAG = "PhotoAdapter"
    var lastPosition = -1

    private val _nextAnimationStartTime: Long = 0
    private var _currentTime: Long = 0
    private var _timeAtFirstCall = System.currentTimeMillis()
    private var _delay: Long = 150
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.photo_items -> {
                L.i(TAG, " OnCreateViewHolder")
                RVH.create(parent, retryCallback)
            }
            R.layout.item_network_state -> NetworkStateViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.photo_items -> (holder as RVH).onBind(getItem(position)!!)
            R.layout.item_network_state -> {
                val layoutParams = (holder).itemView.layoutParams as? (StaggeredGridLayoutManager.LayoutParams)
                layoutParams?.isFullSpan = true
                (holder as NetworkStateViewHolder).bindTo(networkState)
            }

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
            R.layout.photo_items
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

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is RVH) {

        }
    }


    class RVH(val view: View, retryCallback: () -> Unit) : RecyclerView.ViewHolder(view) {

        private val TAG = "RVH"
        @BindView(R.id.iv)
        lateinit var iv: AppCompatImageView
        @BindView(R.id.photo_frame)
        lateinit var photo_frame: ViewGroup
        @BindView(R.id.user_name_tv)
        lateinit var userNameTv: AppCompatTextView
        @BindView(R.id.user_twitter_name_tv)
        lateinit var userTwitterName: AppCompatTextView
        @BindView(R.id.user_iv)
        lateinit var userIv: AppCompatImageView
        @BindView(R.id.fav_cb)
        lateinit var favCb: AppCompatCheckBox
        @BindView(R.id.collect_frame)
        lateinit var collectFrame: ViewGroup

        var isFirstTime = true
        var likeCbClicked = false

        init {
            ButterKnife.bind(this, view)
            favCb.setOnCheckedChangeListener { compoundButton, b ->

            }
            collectFrame.setOnClickListener {

            }
        }

        fun onBind(item: PhotoModel) {
            iv.layoutParams.height = item.height!! / 4
            iv.requestLayout()
            iv.load(item.urls?.raw!!, false)

            if (view.resources.getBoolean(R.bool.is_land)) {
                iv.scaleType = ImageView.ScaleType.CENTER_CROP
            }
            if (item.user != null) {
                userIv.load(item.user?.profileImage!!.medium!!, true)
                userNameTv.text = item.user?.username
                if (item.user?.twitterUsername != null) {
                    userTwitterName.text = "@${item.user?.twitterUsername}"
                    userTwitterName.s()
                } else {
                    userTwitterName.h()
                }
                favCb.isChecked = item.likedByUser!!
            }
        }


        private fun setAnimate(position: Int, holder: RVH) {
            if (position > RVH.lastPosition)
                holder.apply {
                    userIv.leftToRight(0f)
                    userNameTv.leftToRight(0f)
                    userTwitterName.leftToRight(0f)
                    RVH.lastPosition = position
                }
        }

        companion object {
            var lastPosition = -1
            fun create(parent: ViewGroup, retryCallback: () -> Unit): RVH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.photo_items, parent, false)

                return RVH(view, retryCallback)
            }
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<PhotoModel>() {
            override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean =
                    oldItem == newItem
        }
    }

}