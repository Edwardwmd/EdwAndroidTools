package com.edw.screenadaptation.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.util.DisplayMetrics
import android.util.Log

import com.edw.screenadaptation.annotation.ScreenOrientation
import com.edw.screenadaptation.annotation.WIDTH

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
 * Description：    此方式是今日头条屏幕适配的核心方案，入侵性小，配置简单
 *
 ***************************************************************************************************
 */
object AtomScreenAdaptationManager {

	private const val TAG: String = "AtomScreenAdaptationManager"

	//单位像素点与标准像素点比例因子（系统中:DensityDPI/160）
	private var mDensity: Float = 0.0F

	//字体缩放因数
	private var mScaleDensity: Float = 0.0F

	//全局显示指标
	private var appDm: DisplayMetrics? = null

	//设计图纸给的标准宽度（以宽度为标准）
	private const val DESIGN_WIDTH: Float = 360F

	//设计图纸给出的标准宽度
	private const val DESIGN_HEIGHT: Float = 640F


	/**
	 * 先要在Application初始化
	 * 在Activity中手动onConfigurationChanged查看屏幕方向，
	 * 字体变化等参数需要在 AndroidManifest.xml中的activity
	 * 里面配置android:configChanges="相关参数，并添加
	 *  <uses-permission
	 *  android:name="android.permission.CHANGE_CONFIGURATION"
	 *  tools:ignore="ProtectedPermissions" />权限
	 */
	fun initAtomScreenAdaptation(app: Application) {
		appDm = app.resources.displayMetrics
		if (mDensity == 0.0F || mScaleDensity == 0.0F) {
			mDensity = appDm!!.density
			mScaleDensity = appDm!!.scaledDensity
			app.registerComponentCallbacks(object : ComponentCallbacks {
				//屏幕状态改变或系统字体改变
				override fun onConfigurationChanged(newConfig: Configuration) {
					//字体改变时重新获取字体缩放因数
					if (newConfig.fontScale > 0) {
						mScaleDensity =
							app.resources.displayMetrics.scaledDensity
						val targetScaledDensity =
							(appDm!!.widthPixels / DESIGN_WIDTH) * (mScaleDensity / mDensity)
						Log.d(
							"TAG",
							"基于宽的缩放--》${appDm!!.widthPixels / DESIGN_WIDTH}   字体前后缩放之比---》$mScaleDensity ÷ $mDensity = ${mScaleDensity / mDensity} --->最终字体缩放--》$targetScaledDensity"
						)
					}
				}

				override fun onLowMemory() {

				}
			})
		}

	}

	/**
	 * 这个在Activity或Fragment中初始化并设置相关参数
	 * 此方法必须要放在布局初始化之前
	 */
	@SuppressLint("LongLogTag")
	fun initScreenOrientation(
		activity: Activity,
		@ScreenOrientation orientation: String = WIDTH,
		designWidth: Float = DESIGN_WIDTH,
		designHeight: Float = DESIGN_HEIGHT
	) {
		if (appDm != null) {

            //不同手机长或宽的真实像素值与设计方案长或宽给的标准之比（其实就是基于长或宽的缩放因子）
			val targetDensity: Float = if (orientation == WIDTH) {
				appDm!!.widthPixels / designWidth
			} else {
				(appDm!!.heightPixels - ScreenManager.getStatusBarHeight(activity)) / designHeight
			}
			//这是以宽度为基准的文字缩放因子(正常情况下mScaleDensity=mDensity，是1：1的关系，
			// 只有字体在系统修改时mScaleDensity发生变化),这段代码当在系统修改文字时点击home键返回页面时
			//会出现字体无法一起按实际缩放的效果，应该使得targetDensity
            //val targetScaledDensity = targetDensity * (mScaleDensity / mDensity)

			//这是以宽度为基准的每英寸像素
			val targetDensityDpi = (160 * targetDensity).toInt()
			val actDM = activity.resources.displayMetrics
			actDM.density = targetDensity
			actDM.scaledDensity = targetDensity
			actDM.densityDpi = targetDensityDpi
		} else {
			Log.e(
				TAG,
				"AtomScreenAdaptationManager中的initAtomScreenAdaptation方法未在Application中初始化 !-_-!"
			)
		}
	}

}