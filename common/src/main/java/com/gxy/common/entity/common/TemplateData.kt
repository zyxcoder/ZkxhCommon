package com.gxy.common.entity.common

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import com.gxy.common.R
import com.gxy.common.common.providers.inter.ServerKeyInner

/**
 * @author zhangyuxiang
 * @date 2024/4/10
 */

/**
#type=-101 隐藏参数
#type=0 （iOS自定义，可输入字符）
#type=101 普通输入框
#type=102 普通输入框禁用
#type=103 普通输入框（iOS自定义，可输入字符）
#type=201 普通下拉框
#type=210 模糊搜索下拉框
#type=230 特殊下拉框写死值
#type=501 日期框 年月日
#type=503 日期框 年月日时分秒
#type=852 文件上传
#type=853 图片上传
 */
enum class InputLayoutType {
    DEFAULT,//普通无单位
    DEFAULT_UNIT,//普通带单位
    DEFAULT_DIALOG,//底部弹窗
    DIALOG_DATE_YMD,//日期弹窗，年月日
    DIALOG_DATE_YMDHMS//日期弹窗,年月日时分秒
}

@Keep
data class InputItemEntity(
    val lableName: String?,
    val isRequireds: Boolean? = false,
    val isCanEdit: Boolean? = true,
    val isCanInput: Boolean? = true,
    val unitContent: String? = null,
    val isShowSearchBox: Boolean? = false,
    val inputLayoutType: InputLayoutType,
    val textInputText: Int? = null,
    val postServerKey: String,
    var selectId: Int? = null,
    var selectContent: String? = null,
    val showBottomLine: Boolean? = true,
    val dialogListInfo: ArrayList<BottomListEntity>? = null,
    var result: Any? = null//结果，初始化时如果有初始值，请将值附上
) : ServerKeyInner {
    override fun getServerKey() = postServerKey
    override fun getServerValue() = result
}

@Keep
data class CheckItemEntity(
    val lableName: String?,
    val isRequireds: Boolean? = false,
    var isCheck: Boolean? = false,
    val postServerKey: String
) : ServerKeyInner {
    override fun getServerKey() = postServerKey
    override fun getServerValue() = isCheck
}

@Keep
data class FileItemEntity(
    val lableName: String?,
    val hintContent: String? = null,
    val isRequireds: Boolean? = false,
    val postServerKey: String,
    var fileAddress: String? = null,
    val fileIsPic: Boolean? = true
) : ServerKeyInner {
    override fun getServerKey() = postServerKey
    override fun getServerValue() = fileAddress
}

@Keep
data class TitleItemEntity(
    val lableName: String?,
    val postServerKey: String? = null,
    var lableValue: String? = null,
    val isShowBottomLine: Boolean? = false
) : ServerKeyInner {
    override fun getServerKey() = postServerKey
    override fun getServerValue() = lableValue
}

@Keep
data class DialogSelectItemEntity(
    val lableName: String?,
    @DrawableRes val drawableRes: Int,
    val postServerKey: String,
    val isRequireds: Boolean? = true,
    var selectId: Int? = null,
    var selectContent: String? = null,
    val showEnter: Boolean? = true,
    val dialogListInfo: ArrayList<BottomListEntity>? = null,
    val marginTop: Int? = 0,
    val marginBottom: Int? = 0
) : ServerKeyInner {
    override fun getServerKey() = postServerKey
    override fun getServerValue() = selectId
}

@Keep
data class MoneyItemEntity(
    val lableName: String?,
    val postServerKey: String,
    var currentMoney: Double? = Double.MIN_VALUE,
    val isRequireds: Boolean? = false
) : ServerKeyInner {
    override fun getServerKey() = postServerKey
    override fun getServerValue() = if (currentMoney == Double.MIN_VALUE) {
        null
    } else {
        currentMoney
    }
}

@Keep
data class TxtItemEntity(
    val lableName: String?,
    var lableValue: String? = null,
    val postServerKey: String? = null,
    @DrawableRes val backgroundRes: Int? = R.drawable.round_solid_6d8df2_top_8
) : ServerKeyInner {
    override fun getServerKey() = postServerKey
    override fun getServerValue() = lableValue
}

@Keep
data class SelectTimeItemEntity(
    val lableName: String?,
    var startTime: String? = null,
    var endTime: String? = null,
    val postServerKey: String? = null,
    val isRequireds: Boolean? = false,
    val isShowBottomLine: Boolean? = false,
    val startTimePostServerKey: String? = null,
    val endTimePostServerKey: String? = null
) : ServerKeyInner {
    override fun getServerKey() = postServerKey
    override fun getServerValue() = arrayListOf(startTime, endTime)
}

@Keep
data class IconTitleItemEntity(
    val lableName: String?,
    var lableValue: String? = "",
    @DrawableRes val iconRes: Int? = R.drawable.ic_contract_num,
    val postServerKey: String? = null,
    val isShowBottomLine: Boolean = true,
) : ServerKeyInner {
    override fun getServerKey() = postServerKey
    override fun getServerValue() = lableValue
}

