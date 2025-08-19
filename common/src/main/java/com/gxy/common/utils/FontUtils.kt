package com.gxy.common.utils

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.size


/**
 * @author zhangyuxiang
 * @date 2025/8/19
 */
fun adjustFontSize(view: View?) {
    if (view is ViewGroup) {
        for (i in 0..<view.size) {
            adjustFontSize(view.getChildAt(i))
        }
    } else if (view is TextView) {
        val tv = view
        val originalSizeSp =
            tv.getTextSize() / tv.getResources().getDisplayMetrics().scaledDensity
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, originalSizeSp + 10) // +5sp
    }
}