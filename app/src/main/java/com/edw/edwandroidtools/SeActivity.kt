package com.edw.edwandroidtools

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.edw.screenadaptation.utils.AtomScreenAdaptationManager

class SeActivity : AppCompatActivity() {


	companion object {

		const val TAG: String = "SeActivity"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		AtomScreenAdaptationManager.initScreenOrientation(this)
		setContentView(R.layout.activity_se)
	}



}