package com.gxy.common.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.gxy.common.R
import com.gxy.common.databinding.ViewToolbarBinding

/**
 * @author zhangyuxiang
 * @date 2024/3/1
 */
class ToolbarLayout constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var onBackListener: (() -> Unit)? = null
    var onRightClickListener: (() -> Unit)? = null
    var onTitleClickListener: (() -> Unit)? = null
    private var mBinding: ViewToolbarBinding

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.ToolbarLayout)
        orientation = VERTICAL
        mBinding = ViewToolbarBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.apply {

            toolbar.setBackgroundColor(
                attr.getColor(
                    R.styleable.ToolbarLayout_toolbar_themecolor,
                    ContextCompat.getColor(context, R.color.transparent)
                )
            )

            tvTitle.setOnClickListener {
                onTitleClickListener?.invoke()
            }
            ivBack.setOnClickListener {
                if (attr.getBoolean(R.styleable.ToolbarLayout_need_self_handle_back, false)) {
                    onBackListener?.invoke()
                } else {
                    //直接返回之前的页面
                    (context as? Activity)?.finish()
                }
            }
            tvRightFun.setOnClickListener { onRightClickListener?.invoke() }
            setToolbarThemeColor(
                attr.getBoolean(
                    R.styleable.ToolbarLayout_toolbar_themecolor_isdark, true
                )
            )
            setRightTitleColor(
                attr.getColor(
                    R.styleable.ToolbarLayout_right_title_color,
                    ContextCompat.getColor(context, R.color.color_3662EC)
                )
            )
            setRightTitleVisibility(
                attr.getBoolean(
                    R.styleable.ToolbarLayout_right_title_isvisible, false
                )
            )
            setRightTitleContent(attr.getString(R.styleable.ToolbarLayout_right_title_content))
            setTitleContent(attr.getString(R.styleable.ToolbarLayout_title_content))
        }
        attr.recycle()
    }


    /**
     * 设置返回按钮和标题的颜色
     * @param isDark 是否为深色
     */
    fun setToolbarThemeColor(isDark: Boolean = true) {
        mBinding.apply {
            ivBack.setColorFilter(
                ContextCompat.getColor(
                    context, if (isDark) R.color.color_333333 else R.color.white
                )
            )
            tvTitle.setTextColor(
                ContextCompat.getColor(
                    context, if (isDark) R.color.color_333333 else R.color.white
                )
            )
        }
    }

    /**
     * 设置右边标题的颜色
     * @param color 色值
     */
    fun setRightTitleColor(@ColorInt color: Int) {
        mBinding.apply {
            tvRightFun.setTextColor(color)
        }
    }

    /**
     * 设置右边标题是否可见
     * @param isVisible 可见性
     */
    fun setRightTitleVisibility(isVisible: Boolean) {
        mBinding.apply {
            tvRightFun.isVisible = isVisible
        }
    }

    /**
     * 设置右边标题文案
     * @param content 文案
     */
    fun setRightTitleContent(content: String?) {
        mBinding.apply {
            tvRightFun.text = content
        }
    }

    /**
     * 设置标题文案
     * @param content 文案
     */
    fun setTitleContent(content: String?) {
        mBinding.apply {
            tvTitle.text = content
        }
    }
}