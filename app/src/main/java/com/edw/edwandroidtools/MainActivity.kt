package com.edw.edwandroidtools

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.edw.screenadaptation.utils.AtomScreenAdaptationManager

class MainActivity : AppCompatActivity() {

	companion object {

		const val TAG: String = "MainActivity"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		AtomScreenAdaptationManager.initScreenOrientation(this)
		setContentView(R.layout.activity_main)
		val btn = findViewById<Button>(R.id.btn_te)
		btn!!.setOnClickListener {
			startActivity(Intent(this, SeActivity::class.java))
		}

	}


}