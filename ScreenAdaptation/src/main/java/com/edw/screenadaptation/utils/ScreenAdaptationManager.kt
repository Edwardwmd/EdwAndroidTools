package com.edw.screenadaptation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.edw.screenadaptation.annotation.FULL_SCREEN
import com.edw.screenadaptation.annotation.NO_BOTTOM_BAR
import com.edw.screenadaptation.annotation.NO_STATUS_AND_BOTTOM_BAR
import com.edw.screenadaptation.annotation.NO_STATUS_BAR
import com.edw.screenadaptation.annotation.ScreenType

/**
 ***************************************************************************************************
 * Project Name:    EdwAndroidTools
 *
 * Date:            2021-04-23
 *
 * Author：         EdwardWMD
 *
 * Github:          https://github.com/Edwardwmd
 *
 * Blog:            https://edwardwmd.gitee.io/
 *
 * Description：    屏幕适配，此方法需要在布局中做修改，入侵性大
 *
 ***************************************************************************************************
 */
class ScreenAdaptationManager private constructor(mC: Context, @ScreenType state: Int) {

	companion object {

		private var standardHeight: Float = 640F
		private var standardWidth: Float = 320F
		private var realHeight: Float = 0F
		private var realWidth: Float = 0F

		@SuppressLint("StaticFieldLeak")
		@Volatile
		private var instance: ScreenAdaptationManager? = null
		fun getInstance(mC: Context, @ScreenType state: Int) = instance ?: synchronized(this) {
			instance ?: ScreenAdaptationManager(mC, state).apply {
				instance = this
			}
		}
	}

	init {
		if ((realHeight == 0F) or (realWidth == 0F)) {
			when (state) {
				FULL_SCREEN -> {
					if (ScreenManager.getScreenHeight(mC) > ScreenManager.getScreenWidth(mC)) {
						realHeight = ScreenManager.getScreenHeight(mC)
						realWidth = ScreenManager.getScreenWidth(mC)
					} else {
						realHeight = ScreenManager.getScreenWidth(mC)
						realWidth = ScreenManager.getScreenHeight(mC)
					}
				}

				NO_STATUS_BAR -> {
					if (ScreenManager.getScreenHeight(mC) > ScreenManager.getScreenWidth(mC)) {
						realHeight =
							ScreenManager.getScreenHeight(mC) - ScreenManager.getStatusBarHeight(mC)
						realWidth = ScreenManager.getScreenWidth(mC)
					} else {
						realHeight = ScreenManager.getScreenWidth(mC)
						realWidth =
							ScreenManager.getScreenHeight(mC) - ScreenManager.getStatusBarHeight(mC)
					}
				}

				NO_BOTTOM_BAR -> {
					if (ScreenManager.getScreenHeight(mC) > ScreenManager.getScreenWidth(mC)) {
						realHeight = ScreenManager.getNoBottomBarScreenHeight(mC).toFloat()
						realWidth = ScreenManager.getScreenWidth(mC)
					} else {
						realHeight = ScreenManager.getScreenWidth(mC)
						realWidth = ScreenManager.getNoBottomBarScreenHeight(mC).toFloat()
					}
					Log.e("TAG", "Error Message------> $realWidth")
				}

				NO_STATUS_AND_BOTTOM_BAR -> {
					if (ScreenManager.getScreenHeight(mC) > ScreenManager.getScreenWidth(mC)) {
						realHeight = ScreenManager.getNoStatusAndBottomBarScreenHeight(mC)
						realWidth = ScreenManager.getScreenWidth(mC)
					} else {
						realHeight = ScreenManager.getScreenWidth(mC)
						realWidth = ScreenManager.getNoStatusAndBottomBarScreenHeight(mC)
					}
				}
			}
		}

	}

	fun scaleXValue(): Float {
		return realWidth / standardWidth
	}

	fun scaleYValue(): Float {
		return realHeight / standardHeight
	}
}