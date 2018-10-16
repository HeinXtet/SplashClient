package com.heinhtet.deevd.splash.base.baseview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife

/**
 * Created by Hein Htet on 10/14/18.
 */
abstract class BaseFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        ButterKnife.bind(this,view)
        return view
    }

    abstract fun getLayoutId(): Int

}