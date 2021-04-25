@file:Suppress("DEPRECATION")

package com.edw.screenadaptation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import java.math.BigDecimal
import kotlin.math.pow
import kotlin.math.sqrt

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
 * Description：    ToDo
 *
 ***************************************************************************************************
 */
object ScreenManager {

	/**
	 * 获取窗口管理器
	 */
	private fun getWindowManger(mC: Context): WindowManager {
		return mC.getSystemService(Context.WINDOW_SERVICE) as WindowManager
	}

	/**
	 * 四舍五入精确到小数点后一位
	 */
	private fun formatDouble(num: Double, reservedDigits: Int): Double {
		return BigDecimal(num).setScale(reservedDigits, BigDecimal.ROUND_HALF_UP).toDouble()
	}

	/**
	 * 获取系统状态栏的高度
	 */
	fun getStatusBarHeight(mC: Context): Float {
		var result = -1F
		val id = mC.resources.getIdentifier("status_bar_height", "dimen", "android")
		if (id > 0) {
			result = (mC.resources.getDimensionPixelSize(id).toString()).toFloat()
		}
		return result
	}

	/**
	 * 获取手机屏幕实际高度
	 */
	fun getScreenHeight(mC: Context): Float {
		val windowManager = getWindowManger(mC)
		val dm =mC.resources!!.displayMetrics
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			mC.display!!.getRealMetrics(dm)

		} else {
			if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				windowManager.defaultDisplay.getRealMetrics(dm)
			} else {
				windowManager.defaultDisplay.getMetrics(dm)
			}
		}
		return dm.heightPixels.toFloat()
	}

	/**
	 * 获取手机屏幕实际宽度
	 */
	fun getScreenWidth(mC: Context): Float {
		val windowManager = getWindowManger(mC)
		val dm = mC.resources!!.displayMetrics
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			mC.display!!.getRealMetrics(dm)

		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				windowManager.defaultDisplay.getRealMetrics(dm)
			} else {
				windowManager.defaultDisplay.getMetrics(dm)
			}
		}
		return dm.widthPixels.toFloat()
	}

	/**
	 * 获取屏幕实际宽度
	 */
	@SuppressLint("ObsoleteSdkInt")
	fun getScreenX(mC: Context): Int {
		val windowManger = getWindowManger(mC)
		val point = Point()
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			mC.display!!.getRealSize(point)
		} else {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
				windowManger.defaultDisplay!!.getSize(point)
			} else {
				windowManger.defaultDisplay!!.getRealSize(point)
			}
		}
		return point.x
	}

	/**
	 * 获取屏幕实际高度
	 */
	@SuppressLint("ObsoleteSdkInt")
	fun getScreenY(mC: Context): Int {
		val windowManger = getWindowManger(mC)
		val point = Point()
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			mC.display!!.getRealSize(point)
		} else {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
				windowManger.defaultDisplay!!.getSize(point)
			} else {
				windowManger.defaultDisplay!!.getRealSize(point)
			}
		}
		return point.y
	}

	/**
	 * dp转换px
	 */
	fun dp2Px(dp: Float, mC: Context): Int {
		val scale = mC.resources!!.displayMetrics.density
		return (scale * dp + 0.5F).toInt()
	}

	/**
	 * px转换dp
	 */
	fun px2Dp(px: Float, mC: Context): Int {
		val scale = mC.resources!!.displayMetrics.density
		return (px / scale + 0.5F).toInt()
	}

	/**
	 * sp转换px
	 */
	fun sp2Px(sp: Float, mC: Context): Int {
		val textScale = mC.resources!!.displayMetrics.scaledDensity
		return (sp * textScale + 0.5F).toInt()
	}

	/**
	 * px转换sp
	 */
	fun px2Sp(px: Float, mC: Context): Int {
		val textScale = mC.resources!!.displayMetrics.scaledDensity
		return (px / textScale + 0.5F).toInt()
	}

	/**
	 * 没有底部虚拟按键栏的屏幕高度
	 */
	fun getNoBottomBarScreenHeight(mC: Context): Int {
		val windowManager = getWindowManger(mC)
		val dm = mC.resources!!.displayMetrics
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			mC.display!!.getMetrics(dm)

		} else {
			windowManager.defaultDisplay.getMetrics(dm)
		}
		return dm.heightPixels
	}

	/**
	 * 没有底部虚拟按键栏和顶部状态栏的实际显示区域的宽度
	 */
	fun getNoStatusAndBottomBarScreenHeight(mC: Context): Float {
		return getNoBottomBarScreenHeight(mC) - getStatusBarHeight(mC)
	}

	/**
	 * 获取底部虚拟按键栏的高度
	 */
	fun getBottomBarHeight(mC: Context): Float {
		return getScreenHeight(mC) - getNoBottomBarScreenHeight(mC)
	}

	/**
	 * 像素密度因子（density=densityDpi/160dpi）
	 */
	fun getScreenDensity(): Float = Resources.getSystem().displayMetrics.density

	/**
	 * 屏幕密度（densityDpi=density*160dpi）
	 */
	fun getScreenDpi(): Int = Resources.getSystem().displayMetrics.densityDpi

	/**
	 * 获取屏幕的尺寸（对角线长度），单位：inch
	 *
	 * 计算公式：手机尺寸=  √（手机高度尺寸的平方+手机宽度尺寸的平方）
	 */
	@SuppressLint("ObsoleteSdkInt")
	fun getScreenInch(mC: Context): Double {
		val realHeight: Double
		val realWidth: Double
		val windowManager: WindowManager = mC.getSystemService(Context.WINDOW_SERVICE) as WindowManager
		val displayMetrics: DisplayMetrics = mC.resources!!.displayMetrics
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			mC.display!!.getRealMetrics(displayMetrics)
			realHeight = displayMetrics.heightPixels.toDouble()
			realWidth = displayMetrics.widthPixels.toDouble()

		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				windowManager.defaultDisplay.getRealMetrics(displayMetrics)
				realHeight = displayMetrics.heightPixels.toDouble()
				realWidth = displayMetrics.widthPixels.toDouble()

			} else {
				windowManager.defaultDisplay.getMetrics(displayMetrics)
				realHeight = displayMetrics.heightPixels.toDouble()
				realWidth = displayMetrics.widthPixels.toDouble()

			}
		}

		return formatDouble(
			sqrt(
				((realHeight / displayMetrics.ydpi).pow(2.0)
						+ (realWidth / displayMetrics.xdpi).pow(2.0)),
			), 1
		)
	}

}