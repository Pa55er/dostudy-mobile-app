package com.example.androidteamproject

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*


class AModeActivity : AppCompatActivity() {

    lateinit var tv1 : TextView
    lateinit var tv2 : TextView
    lateinit var sw1 : Switch
    lateinit var numPickerHour : NumberPicker
    lateinit var numPickerMinute : NumberPicker
    lateinit var numPickerSecond : NumberPicker
    lateinit var btn1 : Button



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amode)

        tv1 = findViewById<TextView>(R.id.tv1)
        tv2 = findViewById<TextView>(R.id.tv2)
        sw1 = findViewById<Switch>(R.id.sw1)
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

        sw1.setOnCheckedChangeListener{CompoundButton, b ->
            if(sw1.isChecked == true) {
                tv1.visibility = View.INVISIBLE
                tv2.visibility = View.VISIBLE
                result = "쉬는타임"
            }
            else {
                tv1.visibility = View.VISIBLE
                tv2.visibility = View.INVISIBLE
                result = "공부타임"
            }
        }

        btn1.setOnClickListener{
            if(btn1.isClickable) {
                btn1.isClickable = false
                val builder = NotificationCompat.Builder(this,
                    NotificationChannelManager.StudyNotificationChannel.uniqueId).apply {
                    setSmallIcon(R.drawable.ic_launcher_background)
                    setContentTitle(result)
                    priority = NotificationCompat.PRIORITY_DEFAULT
                }
                val progressMax = numPickerSecond.value - 1
                var progressCur = progressMax
                NotificationManagerCompat.from(this).apply {
                    val timer = Timer()
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            --progressCur
                            if (progressCur == -1) {
                                builder.setContentText("종료")
                                    .setProgress(0, 0, false)
                                notify(66, builder.build())
                                timer.cancel()
                                btn1.isClickable = true
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
}
