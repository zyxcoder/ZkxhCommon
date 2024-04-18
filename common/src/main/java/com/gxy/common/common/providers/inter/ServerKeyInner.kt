package com.gxy.common.common.providers.inter

/**
 * @author zhangyuxiang
 * @date 2024/4/12
 * 用于获取上传到服务器的key，每个模版Entity需实现
 */
interface ServerKeyInner {
    fun getServerKey(): String?
    fun getServerValue(): Any?
}