package com.adasoraninda.mymoviedb.presentation.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ListVerticalDecorator(
    private val top: Int = 0,
    private val bottom: Int = 0,
    private val leftAndRight: Int = 0,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = leftAndRight
        outRect.right = leftAndRight
        when (parent.getChildAdapterPosition(view)) {
            0 -> {
                outRect.top = top
                outRect.bottom = bottom / 2
            }
            parent.adapter?.itemCount?.minus(1) -> {
                outRect.top = top / 2
                outRect.bottom = bottom
            }
            else -> {
                outRect.top = top / 2
                outRect.bottom = bottom / 2
            }
        }
    }
}

class ListHorizontalDecorator(
    private val start: Int = 0,
    private val end: Int = 0,
    private val middle: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        when (parent.getChildAdapterPosition(view)) {
            0 -> {
                outRect.left = start
                outRect.right = middle / 2
            }
            parent.adapter?.itemCount?.minus(1) -> {
                outRect.right = end
                outRect.left = middle / 2
            }
            else -> {
                outRect.left = middle / 2
                outRect.right = middle / 2
            }
        }
    }
}