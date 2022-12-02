package com.example.dostudy

import android.app.ActivityManager
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.collection.LongSparseArray


class AModeCheckRunningActivity(context: Context, val blockList: MutableSet<String>?) : Thread() {
    var am: ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    var context: Context = context

    companion object {
        var condition: Boolean = true
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun run() {
        if (Looper.myLooper() == null)
            Looper.prepare()


        val intent = Intent(context, OverlayService::class.java)
        while (condition) {
            val packageName = getPackageName(context)
            if (packageName != "com.android.chrome") Log.d("ChkRunningActivity", packageName)
            if (blockList!!.contains(packageName)) {
                if (!OverlayService.isRunning) {
                    context.startForegroundService(intent)
                    OverlayService.isRunning = true
                }
            }
            else {
                if (OverlayService.isRunning) {
                    context.stopService(intent)
                    OverlayService.isRunning = false
                }
            }
        }

        if (OverlayService.isRunning) {
            context.stopService(intent)
            OverlayService.isRunning = false
        }
        Looper.loop()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getPackageName(context: Context): String {
        var usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        var lastRunAppTimeStamp = 0L
        val interval = 3600000L // 1시간
        val end = System.currentTimeMillis()
        val begin = end - interval

        var packageNameMap = LongSparseArray<String>()

        val usageEvents = usageStatsManager.queryEvents(begin, end)

        while (usageEvents.hasNextEvent()) {
            var event = UsageEvents.Event();
            usageEvents.getNextEvent(event)

            if (isForegoundEvent(event)) {
                packageNameMap.put(event.timeStamp, event.packageName)
                if (event.timeStamp > lastRunAppTimeStamp) lastRunAppTimeStamp = event.timeStamp
            }
        }

        return packageNameMap.get(lastRunAppTimeStamp, "").toString()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun isForegoundEvent(event: UsageEvents.Event?): Boolean {
        if (event == null) return false
        if (BuildConfig.VERSION_CODE >= 29)
            return event.eventType == UsageEvents.Event.ACTIVITY_RESUMED
        else
            return event.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND
    }

}