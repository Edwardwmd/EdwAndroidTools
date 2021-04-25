package com.edw.screenadaptation.annotation

import androidx.annotation.StringDef

/**
 ***************************************************************************************************
 * Project Name:    EdwAndroidTools
 *
 * Date:            2021-04-24
 *
 * Author：         EdwardWMD
 *
 * Github:          https://github.com/Edwardwmd
 *
 * Blog:            https://edwardwmd.gitee.io/
 *
 * Description：    根据屏幕宽度高度切换适配
 *
 ***************************************************************************************************
 */
const val HEIGHT = "Screen_Vertical"
const val WIDTH = "Screen_Horizontal"

@StringDef(HEIGHT, WIDTH)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ScreenOrientation