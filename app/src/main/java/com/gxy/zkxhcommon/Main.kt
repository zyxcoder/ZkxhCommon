package com.gxy.zkxhcommon

import android.os.Bundle
import android.widget.ImageView
import com.gxy.common.common.table.BaseTableActivity
import com.gxy.common.databinding.ActivityBaseTableBinding
import com.gxy.common.entity.common.CardIdItemEntity
import com.gxy.common.entity.common.GroupTableEntity
import com.gxy.common.entity.common.IconTitleItemEntity
import com.gxy.common.entity.common.InputItemEntity
import com.gxy.common.entity.common.InputLayoutType
import com.gxy.common.entity.common.SelectTimeItemEntity
import com.gxy.common.entity.common.TxtItemEntity
import com.zyxcoder.mvvmroot.ext.showToast

/**
 * @author zhangyuxiang
 * @date 2024/4/2
 */
class Main : BaseTableActivity<MainViewModel, ActivityBaseTableBinding>() {
    override fun provideTitleContent(): String {
        return "表单"
    }

    override fun provideIsAddOrModify() = true

    override fun handleSubmitBySelf() = true


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        provideGroupTables(
            arrayListOf(
                GroupTableEntity(
                    tables = arrayListOf(
                        IconTitleItemEntity("合同号"),
                        TxtItemEntity("测试"),
                        CardIdItemEntity(

                        ),
                        InputItemEntity(
                            lableName = "采购",
                            isRequireds = true,
                            postServerKey = "purchaseId",
                            inputLayoutType = InputLayoutType.DEFAULT_DIALOG
                        ), InputItemEntity(
                            lableName = "销售",
                            isRequireds = true,
                            postServerKey = "saleId",
                            inputLayoutType = InputLayoutType.DEFAULT_DIALOG,
                        ), InputItemEntity(
                            lableName = "物料",
                            isRequireds = true,
                            postServerKey = "materialId",
                            inputLayoutType = InputLayoutType.DEFAULT_DIALOG,
                        )
                    )
                ), GroupTableEntity(
                    tables = arrayListOf(
                        SelectTimeItemEntity(
                            lableName = "卸货时间",
                            isRequireds = false,
                            isShowBottomLine = false,
                            startTimePostServerKey = "START_TIME_POST_SERVER_KEY",
                            endTimePostServerKey = "END_TIME_POST_SERVER_KEY"
                        )
                    ), paddingBottom = 0
                ), GroupTableEntity(
                    tables = arrayListOf(
                        InputItemEntity(
                            lableName = "备注",
                            isRequireds = false,
                            isCanInput = true,
                            inputLayoutType = InputLayoutType.DEFAULT,
                            postServerKey = "SAMPLE_DESC",
                            showBottomLine = false
                        )
                    ), paddingBottom = 0
                )
            )
        )
    }

    override var provideUpLoadReverseSidePicClickListener: ((data: CardIdItemEntity, ivHodler: ImageView) -> Unit)? =
        { data, ivHodler ->
            showToast("反面")
        }

    override var provideUpLoadRightSidePicClickListener: ((data: CardIdItemEntity, ivHodler: ImageView) -> Unit)? =
        { data, ivHodler ->
            showToast("正面")
        }
}