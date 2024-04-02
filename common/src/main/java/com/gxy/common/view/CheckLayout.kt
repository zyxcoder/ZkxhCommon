package com.gxy.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.gxy.common.R
import com.gxy.common.databinding.ViewCheckYesOrNotBinding

/**
 * @author zhangyuxiang
 * @date 2024/3/11
 */
class CheckLayout(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var mBinding: ViewCheckYesOrNotBinding

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.CheckLayout)
        mBinding = ViewCheckYesOrNotBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.apply {
            rbYes.isChecked = attr.getBoolean(R.styleable.CheckLayout_check_yes, false)
            rbNo.isChecked = attr.getBoolean(R.styleable.CheckLayout_check_yes, false).not()
            tvTitle.text = attr.getString(R.styleable.CheckLayout_checklayout_title)
        }
        attr.recycle()
    }

    /**
     * 获取选择结果，true代表选中"是"，false代表选中"否"
     */
    fun getCheckResult() = mBinding.rbYes.isChecked

    fun setCheck(isYes: Boolean = false) {
        if (isYes) mBinding.rbYes.isChecked else mBinding.rbNo.isChecked
    }
}