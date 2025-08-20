package com.gxy.common.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;

/**
 * @author zhangyuxiang
 * @date 2025/8/19
 */
public class ContextUtils extends ContextWrapper {

    public ContextUtils(Context base) {
        super(base);
    }

    public static ContextWrapper wrap(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        float fontScale = getCustomFontScale(context); // 获取自定义的字体缩放比例
        configuration.fontScale = fontScale;
        return new ContextUtils(context.createConfigurationContext(configuration));
    }

    // 获取自定义的字体缩放比例，这里可以根据需要进行修改，例如从SharedPreferences读取
    private static float getCustomFontScale(Context context) {
        // 默认字体大小
        float scale = GlobalFontScale.INSTANCE.getScale();
        // 你可以根据自己的需求，从SharedPreferences或其他地方获取缩放比例
        // 例如：
        // scale = SharedPreferencesUtils.getFloat("font_scale", 1.0f);
        return scale;
    }
}