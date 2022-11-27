package com.example.androidteamproject

<<<<<<< HEAD
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
=======
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
>>>>>>> ab3769fe7c00dd7f937b8464e0923dc69fdbf1bb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmode)

<<<<<<< HEAD
=======
        bModeDB = BModeDatabase.getInstance(applicationContext)!!

        tvBModeSettingTime = findViewById(R.id.tvBModeSettingTime)
>>>>>>> ab3769fe7c00dd7f937b8464e0923dc69fdbf1bb
        LLBModeAppList = findViewById(R.id.LLBModeAppList)
        btnBModeSave = findViewById(R.id.btnBModeSave)


<<<<<<< HEAD

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
=======
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
>>>>>>> ab3769fe7c00dd7f937b8464e0923dc69fdbf1bb
            tvAppState.id = i+10000

            btnTimeSetting = Button(this) // 앱 시간 세부 설정 버튼
            btnTimeSetting.text = "시간 세부 설정"
            btnTimeSetting.id = i

<<<<<<< HEAD
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
=======
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
>>>>>>> ab3769fe7c00dd7f937b8464e0923dc69fdbf1bb
        }

        for(i in 0..strApp.size-1) {
            findViewById<Button>(i).setOnClickListener { // 시간 세부 설정 버튼 클릭 시 동작
<<<<<<< HEAD
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
=======
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
>>>>>>> ab3769fe7c00dd7f937b8464e0923dc69fdbf1bb
        }
    }
}