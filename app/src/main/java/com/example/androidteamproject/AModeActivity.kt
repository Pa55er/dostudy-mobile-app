package com.example.androidteamproject

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.LongSparseArray
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*


class AModeActivity : AppCompatActivity() {

    lateinit var numPickerHour : NumberPicker
    lateinit var numPickerMinute : NumberPicker
    lateinit var numPickerSecond : NumberPicker
    lateinit var btn1 : Button

    companion object {
        var mainActivity: MainActivity? = null
        var aModeBlockPkgList: MutableSet<String>? = null
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amode)

        numPickerHour = findViewById<NumberPicker>(R.id.numPickerHour)
        numPickerMinute = findViewById<NumberPicker>(R.id.numPickerMinute)
        numPickerSecond = findViewById<NumberPicker>(R.id.numPickerSecond)
        btn1 = findViewById<Button>(R.id.btn1)

        numPickerHour.minValue = 0
        numPickerHour.maxValue = 23
        numPickerMinute.minValue = 0
        numPickerMinute.maxValue = 59
        numPickerSecond.minValue = 0
        numPickerSecond.maxValue = 59

        var result = "공부타임"

        btn1.setOnClickListener{
            if(btn1.isClickable) {
                btn1.isClickable = false

                // A모드 활성화된 동안 B모드 비활성화
                var btnBMode = mainActivity!!.findViewById<Button>(R.id.btnBMode)
                btnBMode.isClickable = false

                val builder = NotificationCompat.Builder(this,
                    AModeNotificationChannelManager.StudyNotificationChannel.uniqueId).apply {
                    setSmallIcon(R.drawable.ic_launcher_background)
                    setContentTitle(result)
                    priority = NotificationCompat.PRIORITY_DEFAULT
                }
                val progressMax = numPickerHour.value * 3600 +
                        numPickerMinute.value * 60 +
                        numPickerSecond.value
                var progressCur = progressMax
                NotificationManagerCompat.from(this).apply {
                    val timer = Timer()
                    AModeCheckRunningActivity.condition = true
                    AModeCheckRunningActivity(this@AModeActivity, aModeBlockPkgList)
                        .start()

                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            --progressCur
                            if (progressCur == -1) {
                                builder.setContentText("종료")
                                    .setProgress(0, 0, false)
                                notify(66, builder.build())
                                timer.cancel()
                                btn1.isClickable = true
                                // A모드 종료시 B모드 다시 활성화
                                btnBMode.isClickable = true
                                AModeCheckRunningActivity.condition = false
                            }
                            builder.setProgress(progressMax, progressCur, false)
                            notify(66, builder.build())
                            builder.setContentText(progressCur.toString() + "초 남음")
                        }
                    }, 0, 1000)
                }
            }
        }

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
