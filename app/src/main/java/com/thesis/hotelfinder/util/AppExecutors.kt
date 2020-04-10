package com.thesis.hotelfinder.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import android.os.Handler
import android.os.Looper

class AppExecutors{

    companion object {
        private var instance: AppExecutors? = null

        fun getInstance(): AppExecutors {
            if (instance == null) instance = AppExecutors()
            return instance!!
        }
    }

    class MainThreadExecutor: Executor{
        private var mainThreadHandler: Handler = Handler(Looper.getMainLooper())

        override fun execute(p0: Runnable?) {
            mainThreadHandler.post(p0!!)
        }
    }

    private val mDiskIO: Executor = Executors.newSingleThreadExecutor()
    private val mMainThreadExecutor: MainThreadExecutor = MainThreadExecutor()

    fun diskIO(): Executor {
        return mDiskIO
    }

    fun mainThread(): MainThreadExecutor {
        return mMainThreadExecutor
    }

}