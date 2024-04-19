package com.gxy.common.ext

import java.lang.Exception
import java.text.SimpleDateFormat

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
/**
 * 日期转换
 */
fun String.toFormattedDate(inputFormat: String, outputFormat: String): String {
    try {
        val inputDateFormat = SimpleDateFormat(inputFormat)
        val outputDateFormat = SimpleDateFormat(outputFormat)

        val date = inputDateFormat.parse(this)
        return outputDateFormat.format(date)
    } catch (e: Exception) {
        println("日期格式转换出现异常: ${e.message}")
        return this // 返回原始日期字符串
    }
}