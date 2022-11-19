package com.example.androidteamproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var btnAMode : Button
    lateinit var btnBMode : Button
    lateinit var btnSetting : Button
    lateinit var btnD : Button
    lateinit var btnW : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAMode = findViewById<Button>(R.id.btnAMode)
        btnBMode = findViewById<Button>(R.id.btnBMode)
        btnSetting = findViewById<Button>(R.id.btnSetting)
        btnD = findViewById<Button>(R.id.btnD)
        btnW = findViewById<Button>(R.id.btnW)

        btnAMode.setOnClickListener{
            val nextIntent = Intent(this@MainActivity, AModeActivity::class.java)
            // A모드 시작
            startActivity(nextIntent)
        }

        btnBMode.setOnClickListener {
            val nextIntent = Intent(this@MainActivity, BModeActivity::class.java)
            // B모드 시작
            startActivity(nextIntent)
        }

        btnD.setOnClickListener{
            ThemeManager.applyTheme(ThemeManager.ThemeMode.DARK)
            finish()//인텐트 종료
            overridePendingTransition(0, 0)//인텐트 효과 없애기
            val intent = intent //인텐트
            startActivity(intent) //액티비티 열기
            overridePendingTransition(0, 0)//인텐트 효과 없애기
        }
        btnW.setOnClickListener{
            ThemeManager.applyTheme(ThemeManager.ThemeMode.LIGHT)
            finish()//인텐트 종료
            overridePendingTransition(0, 0)//인텐트 효과 없애기
            val intent = intent //인텐트
            startActivity(intent) //액티비티 열기
            overridePendingTransition(0, 0)//인텐트 효과 없애기
        }

    }
}