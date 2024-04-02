package com.gxy.owner.view.bottomdialog

import androidx.core.content.ContextCompat
import com.gxy.common.R
import com.gxy.common.databinding.ItemBottomSelectListBinding
import com.gxy.owner.entity.common.BottomListEntity
import com.zyxcoder.mvvmroot.base.adapter.BaseViewBindingAdapter
import com.zyxcoder.mvvmroot.base.adapter.BaseViewBindingHolder

/**
 * @author zhangyuxiang
 * @date 2024/3/11
 */
class BottomListAdapter : BaseViewBindingAdapter<BottomListEntity, ItemBottomSelectListBinding>(
    ItemBottomSelectListBinding::inflate, R.layout.item_bottom_select_list
) {

    var onClickListener: ((item: BottomListEntity) -> Unit)? = null
    override fun convert(
        holder: BaseViewBindingHolder<ItemBottomSelectListBinding>,
        item: BottomListEntity
    ) {
        holder.viewBind.apply {
            tvContent.text = item.name
            tvContent.setTextColor(
                ContextCompat.getColor(
                    context,
                    if (item.isCheck) R.color.color_2C7AF7 else R.color.color_333333
                )
            )
//            ivChoose.isVisible = item.isCheck
            clRoot.setOnClickListener { onClickListener?.invoke(item) }
        }
    }
}