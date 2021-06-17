package com.mml.flickerbrowser

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(
    context: Context ?,
    recyclerView: RecyclerView,
    private val listener: OnRecyclerClickListener
) : RecyclerView.SimpleOnItemTouchListener() {

    companion object {
        private const val TAG = "RecyclerItemClickListen"
    }

    interface OnRecyclerClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    private val gestureDetector = GestureDetectorCompat(context,
        object : GestureDetector.SimpleOnGestureListener(){
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                Log.d(TAG, "onSingleTapUp: starts")
                val childView = recyclerView.findChildViewUnder(e.x,e.y)
                if (childView != null) {
                    listener.onItemClick(childView,recyclerView.getChildAdapterPosition(childView))
                }
                return true
                
            }

            override fun onLongPress(e: MotionEvent) {
                Log.d(TAG, "onLongPress: starts")
                val childView = recyclerView.findChildViewUnder(e.x,e.y)
                if (childView != null ) {
                    listener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView))
                }
            }
        })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.d(TAG, "onInterceptTouchEvent: starts $e")
        val result = gestureDetector.onTouchEvent(e)
        Log.d(TAG, "onInterceptTouchEvent: return $result")
//        return super.onInterceptTouchEvent(rv, e)
        return result
    }
}