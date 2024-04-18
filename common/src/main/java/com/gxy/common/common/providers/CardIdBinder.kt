package com.gxy.common.common.providers

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.gxy.common.R
import com.gxy.common.databinding.ViewTpeCardBinding
import com.gxy.common.entity.common.CardIdItemEntity
import com.zyxcoder.mvvmroot.utils.ImageOptions
import com.zyxcoder.mvvmroot.utils.loadImage

/**
 * @author zhangyuxiang
 * @date 2024/4/18
 */
class CardIdBinder(
    private val onUpLoadRightSidePic: ((data: CardIdItemEntity) -> Unit)? = null,
    private val onUpLoadReverseSidePic: ((data: CardIdItemEntity) -> Unit)? = null
) : QuickViewBindingItemBinder<CardIdItemEntity, ViewTpeCardBinding>() {
    override fun convert(holder: BinderVBHolder<ViewTpeCardBinding>, data: CardIdItemEntity) {
        holder.viewBinding.apply {
            ivRightSide.loadImage(data.rightCradImageUrl, ImageOptions().apply {
                placeholder = R.drawable.ic_card_right_side
                error = R.drawable.ic_card_right_side
                fallback = R.drawable.ic_card_right_side
            })
            ivReverseSide.loadImage(data.reverseCradImageUrl, ImageOptions().apply {
                placeholder = R.drawable.ic_card_reverse_side
                error = R.drawable.ic_card_reverse_side
                fallback = R.drawable.ic_card_reverse_side
            })
            ivRightSideBoder.setOnClickListener {
                onUpLoadRightSidePic?.invoke(data)
            }
            ivReverseSideBoder.setOnClickListener {
                onUpLoadReverseSidePic?.invoke(data)
            }
        }
    }

    override fun onCreateViewBinding(
        layoutInflater: LayoutInflater, parent: ViewGroup, viewType: Int
    ): ViewTpeCardBinding = ViewTpeCardBinding.inflate(layoutInflater, parent, false)
}