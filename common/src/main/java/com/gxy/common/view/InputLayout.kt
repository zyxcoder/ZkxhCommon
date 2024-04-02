package com.gxy.common.view

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import com.gxy.common.R
import com.gxy.common.databinding.ViewInputBinding
import com.gxy.common.entity.common.BottomListEntity
import com.gxy.common.view.bottomdialog.BottomListDialogFragment
import com.zyxcoder.mvvmroot.ext.showToast

/**
 * @author zhangyuxiang
 * @date 2024/3/4
 */
class InputLayout(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var dialogTitle: String? = null
    private var dialogListInfo: ArrayList<BottomListEntity>? = null
    private var mBinding: ViewInputBinding

    private var hintContent: String? = null

    private var contentTag: Int? = null

    var onInputChangeListener: ((content: String?) -> Unit)? = null

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.InputLayout)
        mBinding = ViewInputBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.apply {
            tvTitle.text = attr.getString(R.styleable.InputLayout_title)
            tvStar.isVisible = attr.getBoolean(R.styleable.InputLayout_is_requireds, false)
            val isCanInput = attr.getBoolean(R.styleable.InputLayout_is_input, false)
            val isShowEnter = attr.getBoolean(R.styleable.InputLayout_is_show_enter, true)

            if (isShowEnter) {
                groupName.isVisible = isCanInput.not()
            } else {
                groupName.visibility = GONE
                tvMessage.visibility = VISIBLE
            }

            etName.isVisible = isCanInput
            etName.inputType =
                attr.getInt(R.styleable.InputLayout_android_inputType, InputType.TYPE_CLASS_TEXT)
            hintContent = attr.getString(R.styleable.InputLayout_input_hint)
            etName.hint = hintContent
            val selectContent = attr.getString(R.styleable.InputLayout_select_content)
            tvName.text = if (selectContent.isNullOrEmpty()) hintContent else selectContent
            tvName.setTextColor(
                ContextCompat.getColor(
                    context,
                    if (selectContent.isNullOrEmpty()) R.color.color_BCBCBD else R.color.color_333333
                )
            )
            tvUnit.isVisible = attr.getBoolean(R.styleable.InputLayout_hase_unit, false)
            tvUnit.text = attr.getString(R.styleable.InputLayout_unit_content)
            //默认需要展示底部弹窗
            val isNeedShowBottomDialog =
                attr.getBoolean(R.styleable.InputLayout_is_need_show_bottom_dialog, true)
            val isShowSearchBox = attr.getBoolean(R.styleable.InputLayout_show_search_box, false)
            if (!isCanInput && isNeedShowBottomDialog) {
                clRoot.setOnClickListener {
                    showBottomDialog(
                        (context as? AppCompatActivity)?.supportFragmentManager, isShowSearchBox
                    )
                }
            }
            //设置EditText能否编辑
            etName.isFocusable = attr.getBoolean(R.styleable.InputLayout_editTextCanEdit, true)
            etName.isFocusableInTouchMode =
                attr.getBoolean(R.styleable.InputLayout_editTextCanEdit, true)
            etName.doAfterTextChanged {
                onInputChangeListener?.invoke(it.toString())
            }
        }
        attr.recycle()
    }

    /**
     * 设置EditText是否可编辑
     */
    fun setEditTextCanEdit(isCanEdit: Boolean) {
        mBinding.etName.isFocusable = isCanEdit
        mBinding.etName.isFocusableInTouchMode = isCanEdit
        if (!isCanEdit) {
            mBinding.tvStar.isVisible = false
        }
    }

    fun setMessageText(content: String?) {
        if (!content.isNullOrEmpty()) mBinding.tvMessage.text = content
    }

    fun setSelectText(content: String?, tag: Int? = null) {
        mBinding.tvName.text = if (content.isNullOrEmpty()) hintContent else content
        mBinding.tvName.setTextColor(
            ContextCompat.getColor(
                context, if (content.isNullOrEmpty()) R.color.color_BCBCBD else R.color.color_333333
            )
        )
        mBinding.etName.setText(content)
        contentTag = tag
    }

    fun getContent(): String? {
        mBinding.apply {
            return if (groupName.isVisible) {
                if (tvName.text.toString() == hintContent) {
                    null
                } else {
                    tvName.text.toString()
                }
            } else if (etName.isVisible) etName.text.toString() else tvMessage.text.toString()
        }
    }

    fun getContentTag() = contentTag

    /**
     * 判断是否为空，并且为空弹toast
     */
    fun contentIsEmptyAndShowToast(): Boolean {
        mBinding.apply {
            return if (groupName.isVisible) {
                tvName.text.toString().let {
                    if (it == hintContent || it.isEmpty()) {
                        hintContent?.also { hintContent -> context.showToast(hintContent) }
                        return true
                    }
                    it.isEmpty()
                }
            } else {
                if (etName.text.toString().isEmpty()) {
                    hintContent?.also { hintContent -> context.showToast(hintContent) }
                }
                etName.text.toString().isEmpty()
            }
        }
    }

    fun setBottomDialogData(
        dialogTitle: String?, dialogListInfo: ArrayList<BottomListEntity>?
    ) {
        this.dialogTitle = dialogTitle
        this.dialogListInfo = dialogListInfo
    }

    private fun showBottomDialog(manager: FragmentManager?, isShowSearchBox: Boolean?) {
        dialogListInfo?.find {
            it.id == contentTag
        }?.isCheck = true
        manager?.also {
            if (dialogListInfo.isNullOrEmpty()) {
                return
            }
            BottomListDialogFragment.newInstance(
                dialogTitle = this.dialogTitle,
                dialogListInfo = this.dialogListInfo,
                isShowSearchBox = isShowSearchBox
            ).apply {
                onChooseClickListener = { bottomListEntity ->
                    setSelectText(bottomListEntity.name, bottomListEntity.id)
                    onEntityChangeListener?.invoke(bottomListEntity.data)
                }
            }.show(it)
        }
    }

    var onEntityChangeListener: ((content: Any?) -> Unit)? = null
}