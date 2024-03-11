package ru.xen.productslist.utils

import android.content.res.Resources
import android.util.TypedValue
import androidx.fragment.app.FragmentActivity

object DimensionUtils {
    fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels

    fun getScreenHeight() = Resources.getSystem().displayMetrics.heightPixels

    fun getActionBarHeight(activity: FragmentActivity): Int {
        val tv = TypedValue()
        return if (activity.theme.resolveAttribute(
                androidx.appcompat.R.attr.actionBarSize,
                tv,
                true
            )
        ) {
            TypedValue.complexToDimensionPixelSize(tv.data, activity.resources.displayMetrics)
        } else {
            0
        }
    }
}