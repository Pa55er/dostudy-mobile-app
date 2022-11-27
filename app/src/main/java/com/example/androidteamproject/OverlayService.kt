package com.example.androidteamproject

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


class OverlayService : Service() {

    companion object {
        var isRunning = false
    }

    lateinit var wm: WindowManager
    var mView: View? = null

    override fun onBind(intent: Intent): IBinder? {
        TODO("Return the communication channel to the service.")
        return null
    }

    @SuppressLint("InflateParams")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        val uniqueId = "OverlayChannel"
        val channelName = "OverlayChannel"
        val description = "This is a channel for Overlay"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(uniqueId, channelName, importance)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        val notification = NotificationCompat.Builder(this, uniqueId).build()
        startForeground(1, notification);

        var inflate = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        wm = getSystemService(WINDOW_SERVICE) as WindowManager

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,  // Android O 이상인 경우 TYPE_APPLICATION_OVERLAY 로 설정
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.LEFT or Gravity.TOP
        mView = inflate.inflate(R.layout.view_in_service, null)
        wm.addView(mView, params)
        isRunning = true
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        if (mView != null) {
            wm.removeView(mView)
            mView = null
        }
        isRunning = false
    }
}