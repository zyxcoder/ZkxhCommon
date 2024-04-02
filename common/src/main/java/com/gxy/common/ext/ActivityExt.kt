package com.gxy.common.ext

import androidx.appcompat.app.AppCompatActivity
import com.gxy.common.common.dialog.PhotoSelectedDialog
import com.gxy.common.common.engine.GlideEngine
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener

/**
 * @author zhangyuxiang
 * @date 2024/3/13
 */
fun AppCompatActivity.PicSelectHelper(
    onTakePictureSuccessCallback: ((result: ArrayList<LocalMedia>) -> Unit)? = null,
    onPhotoAlbumSuccessCallback: ((result: ArrayList<LocalMedia>) -> Unit)? = null,
    onTakePictureCancelCallback: (() -> Unit)? = null,
    onPhotoAlbumCancelCallback: (() -> Unit)? = null
) {
    PhotoSelectedDialog().apply {
        setOnItemClickListener { _, position ->
            when (position) {
                PhotoSelectedDialog.TAKE_PICTURE_TYPE -> {
                    PictureSelector.create(activity).openCamera(SelectMimeType.ofImage())
                        .forResult(object : OnResultCallbackListener<LocalMedia> {
                            override fun onResult(result: ArrayList<LocalMedia>) {
                                if (result.isNotEmpty()) {
                                    onTakePictureSuccessCallback?.invoke(result)
                                }
                            }

                            override fun onCancel() {
                                onTakePictureCancelCallback?.invoke()
                            }

                        })
                }

                PhotoSelectedDialog.PHOTO_ALBUM_TYPE -> {
                    PictureSelector.create(activity).openGallery(SelectMimeType.ofImage())
                        .setImageEngine(GlideEngine.createGlideEngine())
                        .setSelectionMode(SelectModeConfig.SINGLE)
                        .forResult(object : OnResultCallbackListener<LocalMedia> {
                            override fun onResult(result: ArrayList<LocalMedia>) {
                                if (result.isNotEmpty()) {
                                    onPhotoAlbumSuccessCallback?.invoke(result)
                                }
                            }

                            override fun onCancel() {
                                onPhotoAlbumCancelCallback?.invoke()
                            }

                        })
                }
            }
        }
    }.show(supportFragmentManager, "PhotoSelectedDialog")
}