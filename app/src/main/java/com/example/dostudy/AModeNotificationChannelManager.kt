package com.example.dostudy

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi


@RequiresApi(Build.VERSION_CODES.O)
object AModeNotificationChannelManager : android.app.Application() {

    object StudyNotificationChannel {
        const val uniqueId = "AModeStudyNotification"
        const val channelName = "AModeStudyNotificationChannel"
        const val description = "This is a channel for study notification"
        const val importance = NotificationManager.IMPORTANCE_HIGH

        @RequiresApi(Build.VERSION_CODES.O)
        val notificationChannel = NotificationChannel(uniqueId, channelName, importance)
    }

    // 더 추가할 채널이 있으면 이 밑에 추가
}

@RequiresApi(Build.VERSION_CODES.O)
object BModeNotificationChannelManager : android.app.Application() {

    object StudyNotificationChannel {
        const val uniqueId = "BModeStudyNotification"
        const val channelName = "BModeStudyNotificationChannel"
        const val description = "This is a channel for study notification"
        const val importance = NotificationManager.IMPORTANCE_DEFAULT

        @RequiresApi(Build.VERSION_CODES.O)
        val notificationChannel = NotificationChannel(uniqueId, channelName, importance)
    }

    // 더 추가할 채널이 있으면 이 밑에 추가
}