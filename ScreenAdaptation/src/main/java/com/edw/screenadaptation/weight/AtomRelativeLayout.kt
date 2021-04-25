package com.edw.screenadaptation.weight

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.RelativeLayout
import com.edw.screenadaptation.annotation.NO_STATUS_AND_BOTTOM_BAR
import com.edw.screenadaptation.utils.ScreenAdaptationManager

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
class AtomRelativeLayout @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec)

		val scaleXValue = ScreenAdaptationManager.getInstance(rootView.context, NO_STATUS_AND_BOTTOM_BAR).scaleXValue()
		Log.e(AtomLinearLayout.TAG,"Error Message------> ScaleX:  ${scaleXValue}")
		for (i in 0 until childCount) {
			val childAt = getChildAt(i)
			val layoutParams = childAt.layoutParams
			layoutParams.width=(layoutParams.width*scaleXValue).toInt()
			layoutParams.height=(layoutParams.height*scaleXValue).toInt()
		}

	}
}