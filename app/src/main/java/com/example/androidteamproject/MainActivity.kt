package com.example.androidteamproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var swAppEnabled : Switch
    lateinit var tvDisabled : TextView
    lateinit var tvEnabled : TextView
    lateinit var btnAMode : Button
    lateinit var btnBMode : Button
    lateinit var btnSetting : Button
    fun swAppCheck(b:Boolean){
        //앱 활성화, 비활성화 체크 스위치 동작 -> 활성화, 비활성화 텍스트 설정
        if(b){
            tvDisabled.setTextColor(Color.WHITE)
            tvEnabled.setTextColor(Color.BLACK)
        }
        else{
            tvDisabled.setTextColor(Color.BLACK)
            tvEnabled.setTextColor(Color.WHITE)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swAppEnabled = findViewById(R.id.swAppEnabled)
        tvDisabled = findViewById(R.id.tvDisabled)
        tvEnabled = findViewById(R.id.tvEnabled)
        btnAMode = findViewById(R.id.btnAMode)
        btnBMode = findViewById(R.id.btnBMode)
        btnSetting = findViewById(R.id.btnSetting)

        swAppCheck(swAppEnabled.isChecked)

        swAppEnabled.setOnCheckedChangeListener { compoundButton, b ->
            swAppCheck(b)//활성화, 비활성화 텍스트 설정
            if(b){
                //앱 활성화 시 동작
            }
            else{
                //앱 비활성화 시 동작
            }
        }

        btnAMode.setOnClickListener {
            //A 모드 버튼 클릭 시 동작
            var intent = Intent(applicationContext,AModeActivity::class.java)
            startActivity(intent)
        }
        btnBMode.setOnClickListener {
            //B 모드 버튼 클릭 시 동작
            var intent = Intent(applicationContext,BModeActivity::class.java)
            startActivity(intent)
        }
        btnSetting.setOnClickListener {
            //설정 버튼 클릭 시 동작
        }
    }
}