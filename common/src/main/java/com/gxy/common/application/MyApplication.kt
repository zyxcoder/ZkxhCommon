package com.gxy.owner.application

import com.facebook.drawee.backends.pipeline.Fresco
import com.gxy.owner.common.loadsir.DefaultLoadingCallback
import com.gxy.owner.common.loadsir.EmptyCallback
import com.gxy.owner.common.loadsir.ErrorCallback
import com.kingja.loadsir.core.LoadSir


/**
 * @author zhangyuxiang
 * @date 2024/2/22
 */
open class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        LoadSir.beginBuilder()
            .addCallback(DefaultLoadingCallback())
            .addCallback(EmptyCallback())
            .addCallback(ErrorCallback())
            .commit()
    }
}