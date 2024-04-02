package com.gxy.common.network.api

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.gxy.common.network.api.ApiService
import com.zyxcoder.mvvmroot.network.ApiException

/**
 * @author zhangyuxiang
 * @date 2024/2/29
 */
@Keep
data class ApiResult<T>(
    @SerializedName("code")
    var statusCode: String?,
    @SerializedName("desc")
    val statusDesc: String?,
    @SerializedName("hasMore")
    val hasMore: Boolean?,
    @SerializedName("data")
    private val data: T?,
    @SerializedName("recordsTotal")
    val listCount: Int?
) {
    fun apiData(): T {
        if (data != null && statusCode == ApiService.RESPONSE_CODE_SUCCESS) {
            return data
        } else {
            throw ApiException(statusCode?.toIntOrNull() ?: -1, statusDesc ?: "")
        }
    }

    fun apiNoData(): T? {
        if (statusCode == ApiService.RESPONSE_CODE_SUCCESS) {
            return data
        } else {
            throw ApiException(statusCode?.toIntOrNull() ?: -1, statusDesc ?: "")
        }
    }
}

@Keep
data class ApiErrotResult(
    @SerializedName("code") var statusCode: String?,
    @SerializedName("desc") val statusDesc: String?
) {
    fun apiErrorData() {
        if (statusCode != ApiService.RESPONSE_CODE_SUCCESS) {
            throw ApiException(statusCode?.toIntOrNull() ?: -1, statusDesc ?: "")
        }
    }
}