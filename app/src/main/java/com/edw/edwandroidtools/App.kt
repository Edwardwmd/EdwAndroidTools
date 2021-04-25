package com.edw.edwandroidtools

import android.app.Application
import android.content.Context
import com.edw.screenadaptation.utils.AtomScreenAdaptationManager
import java.lang.ref.WeakReference
import kotlin.properties.Delegates

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
class App : Application() {

	companion object {

		private var mCRef by Delegates.notNull<WeakReference<Context>>()
		fun appContext(): Context {
			return mCRef.get()!!
		}
	}

	override fun onCreate() {
		super.onCreate()
		mCRef = WeakReference(this)
		AtomScreenAdaptationManager.initAtomScreenAdaptation(mCRef.get() as App)
	}

	override fun onTerminate() {
		super.onTerminate()
		mCRef.clear()
	}
}