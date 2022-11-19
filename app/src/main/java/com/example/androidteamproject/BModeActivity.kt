package com.example.androidteamproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.view.isVisible

class BModeActivity : AppCompatActivity() {
    lateinit var tvBModeSettingTime : TextView // 현재 상태 표시용 텍스트 뷰
    lateinit var LLBModeAppList : LinearLayout // 앱 리스트 동적 추가 할 장소
    lateinit var btnBModeCancel : Button // 취소 버튼
    lateinit var btnBModeSave : Button // 저장 버튼


    lateinit var tvAppState : TextView
    lateinit var btnTimeSetting : Button
    lateinit var tpAppTimeSetting : TimePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmode)

        tvBModeSettingTime = findViewById(R.id.tvBModeSettingTime)
        LLBModeAppList = findViewById(R.id.LLBModeAppList)
        btnBModeCancel = findViewById(R.id.btnBModeCancel)
        btnBModeSave = findViewById(R.id.btnBModeSave)


        // 앱 리스트 가져오기 필요
        var strApp = arrayListOf<String>("app1","app2","app3","app4","app5","app6","app7","app8","app9","app10")
        for(i in 0..strApp.size-1){
            tvAppState = TextView(this) //앱 상태 출력 텍스트 뷰
            tvAppState.text = strApp[i]
            tvAppState.id = i+10000

            btnTimeSetting = Button(this) // 앱 시간 세부 설정 버튼
            btnTimeSetting.text = "시간 세부 설정"
            btnTimeSetting.id = i

            tpAppTimeSetting = TimePicker(this) // 앱 시간 설정 타임피커
            tpAppTimeSetting.visibility = View.GONE
            tpAppTimeSetting.id = i+100000

            // 리스트 뷰에 추가
            LLBModeAppList.addView(tvAppState)
            LLBModeAppList.addView(btnTimeSetting)
            LLBModeAppList.addView(tpAppTimeSetting)
        }

        for(i in 0..strApp.size-1) {
            findViewById<Button>(i).setOnClickListener { // 시간 세부 설정 버튼 클릭 시 동작
                // 앱 별로 타임 피커 화면 출력 여부
                if (findViewById<TimePicker>(i+100000).isVisible)
                    findViewById<TimePicker>(i+100000).visibility = View.GONE
                else findViewById<TimePicker>(i+100000).visibility = View.VISIBLE
            }
        }

        btnBModeCancel.setOnClickListener {
            // 취소 버튼 클릭 시 동작

        }
        btnBModeSave.setOnClickListener {
            // 저장 버튼 클릭 시 동작
        }
    }
}