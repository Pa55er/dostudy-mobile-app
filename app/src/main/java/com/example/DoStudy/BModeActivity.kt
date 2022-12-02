package com.example.DoStudy

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

class BModeActivity : AppCompatActivity() {
    lateinit var LLBModeAppList : LinearLayout // 앱 리스트 동적 추가 할 장소
    lateinit var btnBModeSave : Button // 저장 버튼
    lateinit var bModeDB: BModeDatabase
    lateinit var llAppNameCheckbox : LinearLayout

    companion object {
        var mainActivity: MainActivity? = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_bmode)

        bModeDB = BModeDatabase.getInstance(applicationContext)!!
        val pkgList = runBlocking {
            withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                bModeDB.bModePackageDataDao().getAll()
            }
        }
        val chkboxApps = arrayOfNulls<CheckBox>(pkgList.size)
        val tvAppNames = arrayOfNulls<TextView>(pkgList.size)
        val btnTimeSettings = arrayOfNulls<TextView>(pkgList.size)
        val llSettingNPs = arrayOfNulls<LinearLayout>(pkgList.size)
        val llSettingNPHs = arrayOfNulls<LinearLayout>(pkgList.size)
        val llSettingNPMs = arrayOfNulls<LinearLayout>(pkgList.size)
        val llSettingNPSs = arrayOfNulls<LinearLayout>(pkgList.size)
        val tvHs = arrayOfNulls<TextView>(pkgList.size)
        val tvMs = arrayOfNulls<TextView>(pkgList.size)
        val tvSs = arrayOfNulls<TextView>(pkgList.size)
        val npHs = arrayOfNulls<NumberPicker>(pkgList.size)
        val npMs = arrayOfNulls<NumberPicker>(pkgList.size)
        val npSs = arrayOfNulls<NumberPicker>(pkgList.size)


        LLBModeAppList = findViewById(R.id.LLBModeAppList)
        btnBModeSave = findViewById(R.id.btnBModeSave)

        for (i in pkgList.indices) {
            llAppNameCheckbox = LinearLayout(this)
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            llAppNameCheckbox.layoutParams = params

            chkboxApps[i] = CheckBox(this)
            chkboxApps[i]!!.isChecked = pkgList[i].isChecked
            chkboxApps[i]!!.id = View.generateViewId()

            tvAppNames[i] = TextView(this)
            tvAppNames[i]!!.id = View.generateViewId()
            tvAppNames[i]!!.text = pkgList[i].appName

            btnTimeSettings[i] = Button(this)
            btnTimeSettings[i]!!.id = View.generateViewId()
            btnTimeSettings[i]!!.text = "시간 세부 설정"


            llSettingNPs[i] = LinearLayout(this)
            val llNP = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            llSettingNPs[i]!!.layoutParams = llNP
            llSettingNPs[i]!!.orientation = LinearLayout.HORIZONTAL
            llSettingNPs[i]!!.gravity = Gravity.CENTER_HORIZONTAL
            llSettingNPs[i]!!.id = View.generateViewId()
            llSettingNPs[i]!!.visibility = View.GONE


            llSettingNPHs[i] = LinearLayout(this)
            val llNPH= LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            llSettingNPHs[i]!!.layoutParams = llNPH
            llSettingNPHs[i]!!.orientation = LinearLayout.VERTICAL
            llSettingNPHs[i]!!.gravity = Gravity.CENTER_HORIZONTAL

            tvHs[i] = TextView(this)
            tvHs[i]!!.text = "시"

            npHs[i] = NumberPicker(this)
            npHs[i]!!.maxValue = 23
            npHs[i]!!.minValue = 0
            npHs[i]!!.value = pkgList[i].limitTimeHour
            npHs[i]!!.id = View.generateViewId()


            llSettingNPMs[i] = LinearLayout(this)
            val llNPM = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            llSettingNPMs[i]!!.layoutParams = llNPM
            llSettingNPMs[i]!!.orientation = LinearLayout.VERTICAL
            llSettingNPMs[i]!!.gravity = Gravity.CENTER_HORIZONTAL

            tvMs[i] = TextView(this)
            tvMs[i]!!.text = "분"

            npMs[i] = NumberPicker(this)
            npMs[i]!!.maxValue = 59
            npMs[i]!!.minValue = 0
            npMs[i]!!.value = pkgList[i].limitTimeMinute
            npMs[i]!!.id = View.generateViewId()


            llSettingNPSs[i] = LinearLayout(this)
            val llNPS = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            llSettingNPSs[i]!!.layoutParams = llNPS
            llSettingNPSs[i]!!.orientation = LinearLayout.VERTICAL
            llSettingNPSs[i]!!.gravity = Gravity.CENTER_HORIZONTAL

            tvSs[i] = TextView(this)
            tvSs[i]!!.text = "초"

            npSs[i] = NumberPicker(this)
            npSs[i]!!.maxValue = 59
            npSs[i]!!.minValue = 0
            npSs[i]!!.value = pkgList[i].limitTimeSecond
            npSs[i]!!.id = View.generateViewId()


            //앱 이름과 체크박스 추가
            llAppNameCheckbox.addView(tvAppNames[i])
            llAppNameCheckbox.addView(chkboxApps[i])

            //넘버 피커 추가
            llSettingNPHs[i]!!.addView(tvHs[i])
            llSettingNPHs[i]!!.addView(npHs[i])

            llSettingNPMs[i]!!.addView(tvMs[i])
            llSettingNPMs[i]!!.addView(npMs[i])

            llSettingNPSs[i]!!.addView(tvSs[i])
            llSettingNPSs[i]!!.addView(npSs[i])

            llSettingNPs[i]!!.addView(llSettingNPHs[i])
            llSettingNPs[i]!!.addView(llSettingNPMs[i])
            llSettingNPs[i]!!.addView(llSettingNPSs[i])
            // 리스트 뷰에 전부 추가
            LLBModeAppList.addView(llAppNameCheckbox)
            LLBModeAppList.addView(btnTimeSettings[i])
            LLBModeAppList.addView(llSettingNPs[i])
        }


        for(i in pkgList.indices) {
            btnTimeSettings[i]!!.setOnClickListener { // 시간 세부 설정 버튼 클릭 시 동작
                // 앱 별로 넘버 피커 화면 출력 여부
                if (llSettingNPs[i]!!.isVisible)
                    llSettingNPs[i]!!.visibility = View.GONE
                else llSettingNPs[i]!!.visibility = View.VISIBLE
            }


            chkboxApps[i]!!.setOnClickListener {
                pkgList[i].isChecked = (it as CheckBox).isChecked
            }

        }

        btnBModeSave.setOnClickListener {
            
            // DB에 Update 내용 저장
            runBlocking {
                withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                    pkgList.withIndex().forEach {
                        it.value.limitTimeHour = npHs[it.index]!!.value
                        it.value.limitTimeMinute = npMs[it.index]!!.value
                        it.value.limitTimeSecond = npSs[it.index]!!.value
                        bModeDB.bModePackageDataDao().update(it.value)
                    }
                }
            }

            val blockAppList: MutableMap<String, Int> =
                pkgList
                    .filter { it.isChecked }
                    .associate {
                        it.packageName to
                                it.limitTimeHour * 3600 +
                                it.limitTimeMinute * 60 + it.limitTimeSecond
                    }.toMutableMap()


            if(btnBModeSave.isClickable) {
                btnBModeSave.isClickable = false

                // B모드 활성화된 동안 A모드 비활성화
                val btnAMode = mainActivity!!.findViewById<Button>(R.id.btnAMode)
                btnAMode.isClickable = false

                val builder = NotificationCompat.Builder(this,
                    BModeNotificationChannelManager.StudyNotificationChannel.uniqueId).apply {
                    setSmallIcon(R.drawable.ic_launcher_background)
                    setContentTitle("공부")
                    priority = NotificationCompat.PRIORITY_DEFAULT
                }
                val progressMax = blockAppList.map { it.value }.max()
                var progressCur = progressMax
                NotificationManagerCompat.from(this).apply {
                    val timer = Timer()
                    BModeCheckRunningActivity.condition = true
                    BModeCheckRunningActivity(this@BModeActivity,
                        blockAppList
                    ).start()

                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            --progressCur

                            // 1초마다 각 앱의 차단 시간 1초씩 줄이기
                            // 시간이 다 되었으면 차단 목록에서 제거
                            val blockAppListIter = blockAppList.iterator()
                            while (blockAppListIter.hasNext()) {
                                val next = blockAppListIter.next()
                                if (next.value - 1 <= 0) {
                                    blockAppListIter.remove()

                                    // 차단 목록에서 제거된 앱 이름 Toast로 출력
                                    val handler = Handler(Looper.getMainLooper())
                                    handler.postDelayed({
                                        Toast.makeText(applicationContext,
                                            "${pkgList.find { it.packageName == next.key }!!.appName }앱이 차단 해제 되었습니다.",
                                            Toast.LENGTH_SHORT).show()
                                    }, 0)

                                }
                                else next.setValue(next.value - 1)
                            }

                            Log.d("block2", blockAppList.keys.toList().toString())

                            if (progressCur == -1) {
                                builder.setContentText("종료")
                                    .setProgress(0, 0, false)
                                notify(66, builder.build())
                                timer.cancel()
                                btnBModeSave.isClickable = true
                                // B모드 종료시 A모드 다시 활성화
                                btnAMode.isClickable = true
                                BModeCheckRunningActivity.condition = false
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