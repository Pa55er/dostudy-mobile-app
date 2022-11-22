package com.example.androidteamproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class BModeActivity : AppCompatActivity() {
    lateinit var tvBModeSettingTime : TextView // 현재 상태 표시용 텍스트 뷰
    lateinit var LLBModeAppList : LinearLayout // 앱 리스트 동적 추가 할 장소
    lateinit var btnBModeSave : Button // 저장 버튼
    lateinit var bModeDB: BModeDatabase

    lateinit var tvAppState : TextView
    lateinit var btnTimeSetting : Button
    lateinit var tpAppTimeSetting : TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmode)

        bModeDB = BModeDatabase.getInstance(applicationContext)!!

        tvBModeSettingTime = findViewById(R.id.tvBModeSettingTime)
        LLBModeAppList = findViewById(R.id.LLBModeAppList)
        btnBModeSave = findViewById(R.id.btnBModeSave)


        var pkgListDB = runBlocking {
            withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                bModeDB.bModePackageDataDao().getAll()
            }
        }

        //checkBoxes =

        // 앱 리스트 가져오기 필요
        var strApp = pkgListDB.map { it.appName }
        for(i in 0..strApp.size-1){
            tvAppState = TextView(this) //앱 상태 출력 텍스트 뷰
            tvAppState.text = strApp[i]
            // bias 10000 -> 앱 이름 TextView
            // bias 100000 -> 시간세팅 버튼
            // bias 20000 -> Checkbox
            tvAppState.id = i+10000

            btnTimeSetting = Button(this) // 앱 시간 세부 설정 버튼
            btnTimeSetting.text = "시간 세부 설정"
            btnTimeSetting.id = i

            tpAppTimeSetting = TimePicker(this) // 앱 시간 설정 타임피커
            tpAppTimeSetting.visibility = View.GONE
            tpAppTimeSetting.id = i+100000
//            tpAppTimeSetting.hour = pkgListDB[i].limitHour
//            tpAppTimeSetting.minute = (pkgListDB[i].limitMinute
//
//            // 리스트 뷰에 추가
//            LLBModeAppList.addView(tvAppState)
//            LLBModeAppList.addView(btnTimeSetting)
//            LLBModeAppList.addView(tpAppTimeSetting)
//
//            //nujkjjhfjfjfj
//            if(pkgListDB[i].isChecked) ~~~~.isChecked = true
//            else ~~~.isChecked = false
        }

        for(i in 0..strApp.size-1) {
            findViewById<Button>(i).setOnClickListener { // 시간 세부 설정 버튼 클릭 시 동작
                // 앱 별로 타임 피커 화면 출력 여부
                if (findViewById<TimePicker>(i+100000).isVisible)
                    findViewById<TimePicker>(i+100000).visibility = View.GONE
                else findViewById<TimePicker>(i+100000).visibility = View.VISIBLE
            }

        }
        btnBModeSave.setOnClickListener {
            // 저장 버튼 클릭 시 동작
            for(i in 0..strApp.size-1) {
                if(findViewById<CheckBox>(i + 200000).isChecked) {
                    var target = findViewById<NumberPicker>(i+100000)
                    pkgListDB[i].isChecked = true
                    // 수정 필요
//                    pkgListDB[i].limitHour = tpAppTimeSetting.hour
//                    pkgListDB[i].limitMinute = tpAppTimeSetting.minute
                }
                else {
                    pkgListDB[i].isChecked = false
                }
            }
            finish()
        }
    }
}