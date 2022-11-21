package com.example.androidteamproject

import android.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display.Mode
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.view.isVisible
import org.w3c.dom.Text

class BModeActivity : AppCompatActivity() {
    lateinit var LLBModeAppList : LinearLayout // 앱 리스트 동적 추가 할 장소
    lateinit var btnBModeSave : Button // 저장 버튼
    lateinit var chbxApp : CheckBox//활성화 체크박스

    lateinit var llAppNameCheckbox : LinearLayout
    lateinit var tvAppState : TextView
    lateinit var btnTimeSetting : Button
    lateinit var llSettingNP : LinearLayout// number picker 영역
    lateinit var llSettingNPH : LinearLayout
    lateinit var llSettingNPM : LinearLayout
    lateinit var llSettingNPS : LinearLayout
    lateinit var tvH : TextView
    lateinit var tvM : TextView
    lateinit var tvS : TextView
    lateinit var npH : NumberPicker
    lateinit var npM : NumberPicker
    lateinit var npS : NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmode)

        LLBModeAppList = findViewById(R.id.LLBModeAppList)
        btnBModeSave = findViewById(R.id.btnBModeSave)



        // 앱 리스트 가져오기 필요
        var strApp = arrayListOf<String>("app1","app2","app3","app4","app5","app6","app7","app8","app9","app10")
        for(i in 0..strApp.size-1){
            llAppNameCheckbox = LinearLayout(this)
            //llAppNameCheckbox.orientation = LinearLayout.HORIZONTAL
            var ll = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            llAppNameCheckbox.layoutParams = ll

            chbxApp = CheckBox(this)//앱 활성화 체크 박스
            chbxApp.id = i+20000

            tvAppState = TextView(this) //앱 상태 출력 텍스트 뷰
            tvAppState.text = strApp[i]
            tvAppState.id = i+10000

            btnTimeSetting = Button(this) // 앱 시간 세부 설정 버튼
            btnTimeSetting.text = "시간 세부 설정"
            btnTimeSetting.id = i

            //Number Picker Setting
            llSettingNP = LinearLayout(this)
            var llNP = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            llSettingNP.layoutParams = llNP
            llSettingNP.orientation = LinearLayout.HORIZONTAL
            llSettingNP.gravity = Gravity.CENTER_HORIZONTAL
            llSettingNP.id = i+200000
            llSettingNP.visibility = View.GONE

            //시간 추가
            var llNPH = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            llSettingNPH = LinearLayout(this)
            llSettingNPH.layoutParams = llNPH
            llSettingNPH.orientation = LinearLayout.VERTICAL
            llSettingNPH.gravity = Gravity.CENTER_HORIZONTAL

            tvH = TextView(this)
            tvH.text = "시"

            npH = NumberPicker(this)
            npH.maxValue = 23
            npH.minValue = 0
            npH.id = i+30000

            //분 추가
            var llNPM = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            llSettingNPM = LinearLayout(this)
            llSettingNPM.layoutParams = llNPM
            llSettingNPM.orientation = LinearLayout.VERTICAL
            llSettingNPM.gravity = Gravity.CENTER_HORIZONTAL

            tvM = TextView(this)
            tvM.text = "분"

            npM = NumberPicker(this)
            npM.maxValue = 59
            npM.minValue = 0
            npM.id = i+40000
            //초 추가
            var llNPS = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            llSettingNPS = LinearLayout(this)
            llSettingNPS.layoutParams = llNPS
            llSettingNPS.orientation = LinearLayout.VERTICAL
            llSettingNPS.gravity = Gravity.CENTER_HORIZONTAL

            tvS = TextView(this)
            tvS.text = "초"

            npS = NumberPicker(this)
            npS.maxValue = 59
            npS.minValue = 0
            npS.id = i+50000

            //앱 이름과 체크박스 추가
            llAppNameCheckbox.addView(tvAppState)
            llAppNameCheckbox.addView(chbxApp)

            //넘버 피커 추가
            llSettingNPH.addView(tvH)
            llSettingNPH.addView(npH)

            llSettingNPM.addView(tvM)
            llSettingNPM.addView(npM)

            llSettingNPS.addView(tvS)
            llSettingNPS.addView(npS)

            llSettingNP.addView(llSettingNPH)
            llSettingNP.addView(llSettingNPM)
            llSettingNP.addView(llSettingNPS)
            // 리스트 뷰에 전부 추가
            LLBModeAppList.addView(llAppNameCheckbox)
            LLBModeAppList.addView(btnTimeSetting)
            LLBModeAppList.addView(llSettingNP)
        }

        for(i in 0..strApp.size-1) {
            findViewById<Button>(i).setOnClickListener { // 시간 세부 설정 버튼 클릭 시 동작
                // 앱 별로 넘버 피커 화면 출력 여부
                if (findViewById<LinearLayout>(i+200000).isVisible)
                    findViewById<LinearLayout>(i+200000).visibility = View.GONE
                else findViewById<LinearLayout>(i+200000).visibility = View.VISIBLE
            }
        }
        btnBModeSave.setOnClickListener {
            // 저장 버튼 클릭 시 동작
            /*
            * 설정 시간 접근 법
            * 시 : findViewById<NumberPicker>(index+30000)
            * 분 : findViewById<NumberPicker>(index+40000)
            * 초 : findViewById<NumberPicker>(index+50000)
            * */
        }
    }
}