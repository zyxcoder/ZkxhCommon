package com.gxy.common.utils

/**
 * @author zhangyuxiang
 * @date 2025/8/20
 */
object GlobalFontScale {
    var scale: Float = 1.0f
        private set

    fun setFontScale(value: Float) {
        scale = value
    }
}