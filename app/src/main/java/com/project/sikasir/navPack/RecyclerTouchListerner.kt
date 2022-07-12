package com.project.sikasir.navPack

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Dibuat oleh RizkyPratama pada 04-May-22.
 */

class RecyclerTouchListener internal constructor(
    context: Context,
    private val clickListener: ClickListener?
) : RecyclerView.OnItemTouchListener {
    /* Creating a GestureDetector object that will be used to detect single taps on the RecyclerView. */
    private val gestureDetector: GestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildAdapterPosition(child))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

}

internal interface ClickListener {
    fun onClick(view: View, position: Int)
}