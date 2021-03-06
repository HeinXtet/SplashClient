package com.heinhtet.deevd.splash.utils.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.heinhtet.deevd.splash.R


/**
 * Created by Hein Htet on 10/14/18.
 */

class CustomViewPager : ViewPager {

    private var mSwipeOrientation: Int = 0
  //  private val mScroller: ScrollerCustomDuration? = null

    constructor(context: Context) : super(context) {
        mSwipeOrientation = HORIZONTAL
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setSwipeOrientation(context, attrs)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return super.onTouchEvent(if (mSwipeOrientation == VERTICAL) swapXY(event) else event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (mSwipeOrientation == VERTICAL) {
            val intercepted = super.onInterceptHoverEvent(swapXY(event))
            swapXY(event)
            return intercepted
        }
        return super.onInterceptTouchEvent(event)
    }

    fun setSwipeOrientation(swipeOrientation: Int) {
        if (swipeOrientation == HORIZONTAL || swipeOrientation == VERTICAL)
            mSwipeOrientation = swipeOrientation
        else
            throw IllegalStateException("Swipe Orientation can be either CustomViewPager.HORIZONTAL" + " or CustomViewPager.VERTICAL")
        initSwipeMethods()
    }

    private fun setSwipeOrientation(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomViewPager)
        mSwipeOrientation = typedArray.getInteger(R.styleable.CustomViewPager_swipe_orientation, 0)
        typedArray.recycle()
        initSwipeMethods()
    }

    private fun initSwipeMethods() {
        if (mSwipeOrientation == VERTICAL) {
            // The majority of the work is done over here
            setPageTransformer(true, VerticalPageTransformer())
            // The easiest way to get rid of the overscroll drawing that happens on the left and right
            overScrollMode = View.OVER_SCROLL_NEVER
        }
    }

    /**
     * Set the factor by which the duration will change
     */
//    fun setScrollDurationFactor(scrollFactor: Double) {
//        mScroller!!.setScrollDurationFactor(scrollFactor)
//    }

    private fun swapXY(event: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()

        val newX = event.y / height * width
        val newY = event.x / width * height

        event.setLocation(newX, newY)
        return event
    }

    private inner class VerticalPageTransformer : ViewPager.PageTransformer {

        override fun transformPage(page: View, position: Float) {
            if (position < -1) {
                // This page is way off-screen to the left
                page.alpha = 0f
            } else if (position <= 1) {
                page.alpha = 1f

                // Counteract the default slide transition
                page.translationX = page.width * -position

                // set Y position to swipe in from top
                val yPosition = position * page.height
                page.translationY = yPosition
            } else {
                // This page is way off screen to the right
                page.alpha = 0f
            }
        }
    }

    companion object {
        val HORIZONTAL = 0
        val VERTICAL = 1
    }
}