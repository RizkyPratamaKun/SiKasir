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

    /**
     * If the user touches a child view, and the click listener is not null, and the gesture detector
     * returns true, then call the click listener's onClick function
     *
     * @param rv RecyclerView - The RecyclerView that the touch event has been dispatched to.
     * @param e MotionEvent - The MotionEvent object that describes the touch event.
     * @return Boolean
     */
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildAdapterPosition(child))
        }
        return false
    }

    /**
     * A function that is called when a touch event occurs.
     *
     * @param rv The RecyclerView that the user is interacting with.
     * @param e The motion event that occurred.
     */
    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    /**
     * This function is called when a parent does not want this child to receive touch events
     *
     * @param disallowIntercept If true, the child wants the parent to stop intercepting its touch
     * events.
     */
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

}

/* This class is a custom RecyclerView.Adapter that takes a list of items and a click listener and
displays the items in a RecyclerView */
internal interface ClickListener {
    fun onClick(view: View, position: Int)
}