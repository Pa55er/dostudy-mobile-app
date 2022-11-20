package com.example.androidteamproject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi


@RequiresApi(Build.VERSION_CODES.O)
object NotificationChannelManager : android.app.Application() {

    object StudyNotificationChannel {
        const val uniqueId = "StudyNotification"
        const val channelName = "StudyNotificationChannel"
        const val description = "This is a channel for study notification"
        const val importance = NotificationManager.IMPORTANCE_HIGH

        @RequiresApi(Build.VERSION_CODES.O)
        val notificationChannel = NotificationChannel(uniqueId, channelName, importance)
    }

    // 더 추가할 채널이 있으면 이 밑에 추가
}