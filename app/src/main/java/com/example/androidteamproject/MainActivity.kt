package com.example.androidteamproject

import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var btnAMode : Button
    lateinit var btnBMode : Button
    lateinit var btnSetting : Button
    lateinit var btnD : Button
    lateinit var btnW : Button
    lateinit var aModeDB: AModeDatabase
    lateinit var bModeDB: BModeDatabase


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(
            NotificationChannelManager.StudyNotificationChannel.notificationChannel
        )

        aModeDB = AModeDatabase.getInstance(applicationContext)!!
        bModeDB = BModeDatabase.getInstance(applicationContext)!!
        databaseSynchronization()

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

    private fun databaseSynchronization() {
        // 설치된 패키지들을 가져오기 위한 intent
        var intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        // 현재 설치된 패키지들
        var pkgList: MutableMap<String, String> =
            applicationContext
                .packageManager
                .queryIntentActivities(intent, 0).associate {
                    it.activityInfo.packageName to it.activityInfo.loadLabel(packageManager)
                        .toString()
                }.toMutableMap()

        // DB에 저장된 패키지들
        var pkgListDB = runBlocking {
            withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                aModeDB.aModePackageDataDao().getAll()
            }
        }.map { it.packageName }

        // DB에 저장된 패키지들을 pkgList에서 제거하여 추가된 패키지 들만 남긴다
        // 만약 pkgList에 없다면 삭제된 패키지이므로 DB에서 제거한다
        pkgListDB.forEach {
            if (pkgList.contains(it)) pkgList.remove(it)
            else {
                runBlocking {
                    withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                        aModeDB.aModePackageDataDao().deleteDataByPackageName(it)
                        bModeDB.bModePackageDataDao().deleteDataByPackageName(it)
                    }
                }
            }
        }

        // 새로 추가된 패키지들을 DB에 추가한다
        pkgList.forEach {
            runBlocking {
                withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                    aModeDB.aModePackageDataDao().insert(
                        AModePackageData(it.key, it.value, false)
                    )
                    bModeDB.bModePackageDataDao().insert(
                        BModePackageData(it.key, it.value, false, 0)
                    )
                }
            }
        }
    }
}