package com.gxy.common.ext

/**
 * @author zhangyuxiang
 * @date 2024/3/19
 */

fun String?.safeToFloat(): Float {
    return this?.toFloatOrNull() ?: 0.0f
}

fun String?.getFromIdForServerEntity(): Int {
    return this?.substringBefore("-")?.toIntOrNull() ?: 0
}