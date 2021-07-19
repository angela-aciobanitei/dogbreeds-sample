package com.ang.acb.dogbreeds.details

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

/**
 * A custom ItemDecoration to provide equal spacing for a RecyclerView that lays out items in a grid.
 */
class GridMarginDecoration private constructor(
    private val itemOffset: Int
) : RecyclerView.ItemDecoration() {

    constructor(
        context: Context,
        @DimenRes itemOffsetId: Int,
    ) : this(context.resources.getDimensionPixelSize(itemOffsetId))

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect[itemOffset, itemOffset, itemOffset] = itemOffset
    }
}
