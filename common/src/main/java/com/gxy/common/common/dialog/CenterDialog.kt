package com.gxy.common.common.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import com.gxy.common.R
import com.gxy.common.utils.adjustFontSize

/**
 * 需要缩放的dialog继承此类
 */
open class CenterDialog(context: Context) : Dialog(context, R.style.TransparentProgressDialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.attributes?.windowAnimations = R.style.centerDialogZoomAnim
        window?.decorView?.findViewById<ViewGroup>(android.R.id.content)?.let {
            adjustFontSize(it)
        }
    }
}