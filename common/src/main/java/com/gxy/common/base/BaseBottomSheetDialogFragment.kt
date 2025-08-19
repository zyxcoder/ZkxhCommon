package com.gxy.common.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gxy.common.R
import com.gxy.common.utils.adjustFontSize
import com.gxy.common.utils.getScreenHeight
import com.gxy.common.utils.inflateBindingWithBottomSheetDialogFragmentGeneric

/**
 * 通用BottomSheetDialogFragment
 * 除了bottomSheet的默认白色，方便实现各个圆角
 */
abstract class BaseBottomSheetDialogFragment<VB : ViewBinding> : BottomSheetDialogFragment() {

    //该类绑定的 ViewBinding
    private var _binding: VB? = null
    val mViewBind: VB get() = _binding!!

    /**
     * 创建DataBinding
     */
    private fun initDataBind(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = inflateBindingWithBottomSheetDialogFragmentGeneric(inflater, container, false)

        return if (enableBlackBars()) {
            val rootView = mViewBind.root
            adjustFontSize(rootView)
            val screenWidth = resources.displayMetrics.widthPixels
            val screenHeight = resources.displayMetrics.heightPixels
            val screenAspectRatio = screenWidth.toFloat() / screenHeight


            val mContext = context
            // 只在横屏且屏幕比例大于原始比例时添加黑边
            if (screenAspectRatio > originalAspectRatio() && mContext != null) {
                // 计算内容区域的宽度（保持原始比例）
                val contentWidth = (screenHeight * originalAspectRatio()).toInt()

                // 计算左右黑边大小
                val sideMargin = (screenWidth - contentWidth) / 2

                // 创建新的根布局
                val newRoot = FrameLayout(mContext)
                newRoot.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                newRoot.setBackgroundColor(blackBarsColor())

                // 创建内容容器
                val contentContainer = FrameLayout(mContext)
                val params = FrameLayout.LayoutParams(
                    contentWidth,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                params.setMargins(sideMargin, 0, sideMargin, 0)
                contentContainer.layoutParams = params

                // 移动原始内容到新容器
                contentContainer.addView(rootView)

                // 组装新布局
                newRoot.addView(contentContainer)

                newRoot

            } else {
                rootView
            }

        } else {
            mViewBind.root
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 是否背景变暗
        if (!isBackgroundDimEnabled()) {
            setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogFragmentTheme)
        } else {
            setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogFragmentThemeDim)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            attributes = attributes.apply {
                windowAnimations = R.style.popwin_anim_style
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 去除BottomSheet默认的白色背景以实现圆角
        val bottomSheet: View = (dialog as BottomSheetDialog).delegate
            .findViewById(com.google.android.material.R.id.design_bottom_sheet)
            ?: return
        // 初始完全展开
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isHideable = couldScrollDownClose()
        resetPeekHeight()?.let { behavior.peekHeight = it }
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels
        val screenAspectRatio = screenWidth.toFloat() / screenHeight
        bottomSheet.updateLayoutParams<ViewGroup.LayoutParams> {
            height = if (resetDialogHeightPercent() == 0f) {
                ViewGroup.LayoutParams.MATCH_PARENT
            } else {
                ((context?.getScreenHeight()
                    ?: 0) * resetDialogHeightPercent() * if (screenAspectRatio > originalAspectRatio()) originalAspectRatio() else 1F).toInt()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initDataBind(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    open fun initView() {
        // Override if need
    }

    open fun initData() {
        // Override if need
    }

    /**
     * 设置弹窗的高度,不设置就MATCH_PARENT了
     */
    open fun resetDialogHeightPercent(): Float = 0.0f

    /**
     * 设置弹窗的高度,不设置就MATCH_PARENT了
     */
    open fun couldScrollDownClose(): Boolean = true

    /**
     * 重新设置的peekHeight，折叠后的高度
     */
    open fun resetPeekHeight(): Int? = context?.getScreenHeight()

    /**
     * 是否背景变暗，默认变暗
     */
    open fun isBackgroundDimEnabled() = true

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            super.show(manager, tag)
        } catch (e: Exception) {
        }
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


}
