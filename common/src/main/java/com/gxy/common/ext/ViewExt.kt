package com.gxy.common.ext

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * @author zhangyuxiang
 * @date 2025/8/8
 */


fun Context.createPadView(rootView: View): View {
    val originalAspectRatio = 9f / 16f  // 1080x1920 = 9:16
    val blackBarsColor = 0xFF000000.toInt()  // 纯黑色
    val screenWidth = resources.displayMetrics.widthPixels
    val screenHeight = resources.displayMetrics.heightPixels
    val screenAspectRatio = screenWidth.toFloat() / screenHeight


    // 只在横屏且屏幕比例大于原始比例时添加黑边
    if (screenAspectRatio > originalAspectRatio) {
        // 计算内容区域的宽度（保持原始比例）
        val contentWidth = (screenHeight * originalAspectRatio).toInt()

        // 计算左右黑边大小
        val sideMargin = (screenWidth - contentWidth) / 2

        // 创建新的根布局
        val newRoot = FrameLayout(this)
        newRoot.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        newRoot.setBackgroundColor(blackBarsColor)

        // 创建内容容器
        val contentContainer = FrameLayout(this)
        val params = FrameLayout.LayoutParams(
            contentWidth, ViewGroup.LayoutParams.MATCH_PARENT
        )
        params.setMargins(sideMargin, 0, sideMargin, 0)
        contentContainer.layoutParams = params

        // 移动原始内容到新容器
        contentContainer.addView(rootView)

        // 组装新布局
        newRoot.addView(contentContainer)

        return newRoot

    } else {
        return rootView
    }

}