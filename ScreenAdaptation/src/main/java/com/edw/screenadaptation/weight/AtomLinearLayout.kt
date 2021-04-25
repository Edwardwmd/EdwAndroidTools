package com.edw.screenadaptation.weight

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import com.edw.screenadaptation.annotation.NO_STATUS_AND_BOTTOM_BAR
import com.edw.screenadaptation.utils.ScreenAdaptationManager

class AtomLinearLayout @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

	companion object {
		const val TAG: String = "AtomLinearLayout"
	}

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec)

		val scaleXValue = ScreenAdaptationManager.getInstance(rootView.context, NO_STATUS_AND_BOTTOM_BAR).scaleXValue()
		Log.e(TAG,"Error Message------> ScaleX:  ${scaleXValue}")
		for (i in 0 until childCount) {
			val childAt = getChildAt(i)
			val layoutParams = childAt.layoutParams
			layoutParams.width=(layoutParams.width*scaleXValue).toInt()
			layoutParams.height=(layoutParams.height*scaleXValue).toInt()
		}

	}
}