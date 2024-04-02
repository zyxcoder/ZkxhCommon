package com.gxy.common.network.api


/**
 * @author zhangyuxiang
 * @date 2024/2/27
 */
interface ApiService {

    companion object {
        const val RESPONSE_CODE_SUCCESS = "0"

        //一般我们成功的状态码返回是200，如果遇到特殊的成功状态码，需要在这里补充
        val SPECIAL_API_SUCCESS_STATUS_CODE = mapOf<String, String>()
    }


}