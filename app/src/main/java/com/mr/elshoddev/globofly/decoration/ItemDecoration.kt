package com.mr.elshoddev.globofly.decoration

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Array.get

class ItemDecoration(private val spaceSize: Int, private val horizontal: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceSize.dp

                left = horizontal.dp

                right = horizontal.dp
                bottom = spaceSize.dp
            } else {

                left = horizontal.dp
                bottom = spaceSize.dp
                right = horizontal.dp
            }

        }

    }

    private val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

}