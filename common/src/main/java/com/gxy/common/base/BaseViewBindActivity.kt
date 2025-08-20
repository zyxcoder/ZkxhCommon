package com.gxy.common.base

import android.R
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isNotEmpty
import androidx.viewbinding.ViewBinding
import com.gxy.common.ext.dismissLoadingExt
import com.gxy.common.ext.showLoadingExt
import com.gxy.common.utils.ContextUtils
import com.gxy.common.utils.adjustFontSize
import com.gyf.immersionbar.ImmersionBar
import com.zyxcoder.mvvmroot.base.activity.BaseVmVbActivity
import com.zyxcoder.mvvmroot.base.viewmodel.BaseViewModel

/**
 * @author zhangyuxiang
 * @date 2024/2/22
 */
abstract class BaseViewBindActivity<VM : BaseViewModel, VB : ViewBinding> :
    BaseVmVbActivity<VM, VB>() {


    abstract fun init(savedInstanceState: Bundle?)

    /**
     * 是否需要状态栏
     */
    open fun needStatusBar() = true

    /**
     * 状态是否为深色
     */
    open fun statusBarIsDarkFont() = true

    /**
     * 解决软键盘与底部输入框冲突问题,默认为false
     */
    open fun keyboardEnable() = false

    override fun createObserver() {
    }

    override fun showLoading(message: String) {
        showLoadingExt()
    }

    override fun dismissLoading() {
        dismissLoadingExt()
    }

    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        if (needStatusBar()) {
            ImmersionBar.with(this).keyboardEnable(keyboardEnable())
                .statusBarDarkFont(statusBarIsDarkFont()) //状态栏字体是深色，不写默认为亮色
                .init() //必须调用方可沉浸式
        } else {
            ImmersionBar.with(this).init()
        }
        init(savedInstanceState)
    }


    /**
     * 是否启用黑边适配（保持原始比例）
     */
    open fun enableBlackBars() = true

    /**
     * 原始屏幕比例（竖屏比例）
     */
    open fun originalAspectRatio() = 9f / 16f  // 1080x1920 = 9:16

    /**
     * 黑边背景颜色
     */
    open fun blackBarsColor() = 0xFF000000.toInt()  // 纯黑色

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(ContextUtils.wrap(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.post {
            if (enableBlackBars()) {
                applyBlackBars()
            }
        }
    }

    /**
     * 应用黑边适配（保持原始比例）
     */
    private fun applyBlackBars() {
        val rootView = window.decorView.findViewById<ViewGroup>(R.id.content)
        adjustFontSize(rootView)
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels
        val screenAspectRatio = screenWidth.toFloat() / screenHeight

        // 只在横屏且屏幕比例大于原始比例时添加黑边
        if (screenAspectRatio > originalAspectRatio()) {
            // 计算内容区域的宽度（保持原始比例）
            val contentWidth = (screenHeight * originalAspectRatio()).toInt()

            // 计算左右黑边大小
            val sideMargin = (screenWidth - contentWidth) / 2

            // 创建新的根布局
            val newRoot = FrameLayout(this)
            newRoot.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            newRoot.setBackgroundColor(blackBarsColor())

            // 创建内容容器
            val contentContainer = FrameLayout(this)
            val params = FrameLayout.LayoutParams(
                contentWidth,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            params.setMargins(sideMargin, 0, sideMargin, 0)
            contentContainer.layoutParams = params

            // 移动原始内容到新容器
            if (rootView.isNotEmpty()) {
                val originalContent = rootView.getChildAt(0)
                rootView.removeView(originalContent)
                contentContainer.addView(originalContent)
            }

            // 组装新布局
            newRoot.addView(contentContainer)

            // 设置新布局
            setContentView(newRoot)
        }
    }
}