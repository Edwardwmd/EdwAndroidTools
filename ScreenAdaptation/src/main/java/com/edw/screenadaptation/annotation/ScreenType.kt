package com.edw.screenadaptation.annotation

import androidx.annotation.IntDef

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
 * Description：    ToDo
 *
 ***************************************************************************************************
 */
const val NO_STATUS_BAR = 0x11
const val FULL_SCREEN = 0x12
const val NO_BOTTOM_BAR = 0x13
const val NO_STATUS_AND_BOTTOM_BAR = 0x14

/**
 * 按照是否需要状态栏和导航栏去适配屏幕
 */
@IntDef(
	FULL_SCREEN,
	NO_STATUS_BAR,
	NO_BOTTOM_BAR,
	NO_STATUS_AND_BOTTOM_BAR
)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class ScreenType