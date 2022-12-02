package com.example.dostudy

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var btnAMode : Button
    lateinit var btnBMode : Button
    lateinit var btnD : Button
    lateinit var btnW : Button
    lateinit var bModeDB: BModeDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        checkUsageStatsPermission()
        checkOverlayPermission()

        val aModeNotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        aModeNotificationManager.createNotificationChannel(
            AModeNotificationChannelManager.StudyNotificationChannel.notificationChannel
        )

        val bModeNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        bModeNotificationManager.createNotificationChannel(
            BModeNotificationChannelManager.StudyNotificationChannel.notificationChannel
        )

        bModeDB = BModeDatabase.getInstance(applicationContext)!!
        databaseSynchronization()

        btnAMode = findViewById<Button>(R.id.btnAMode)
        btnBMode = findViewById<Button>(R.id.btnBMode)
        btnD = findViewById<Button>(R.id.btnD)
        btnW = findViewById<Button>(R.id.btnW)

        AModeActivity.mainActivity = this
        BModeActivity.mainActivity = this

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

    @SuppressLint("QueryPermissionsNeeded")
    private fun databaseSynchronization() {
        // 설치된 패키지들을 가져오기 위한 intent
        var intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val excludePkgList = listOf("com.android.dialer", "com.android.settings",
            "com.google.android.apps.messaging", "com.example.dostudy")

        // 현재 설치된 패키지들
        var pkgList: MutableMap<String, String> =
            applicationContext
                .packageManager
                .queryIntentActivities(intent, 0).associate {
                    it.activityInfo.packageName to it.activityInfo.loadLabel(packageManager)
                        .toString()
                }.toMutableMap()

        AModeActivity.aModeBlockPkgList =
            pkgList.map { it.key }.toMutableSet()

        excludePkgList.forEach { AModeActivity.aModeBlockPkgList!!.remove(it) }

        // DB에 저장된 패키지들
        var pkgListDB = runBlocking {
            withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                bModeDB.bModePackageDataDao().getAll()
            }
        }.map { it.packageName }

        // DB에 저장된 패키지들을 pkgList에서 제거하여 추가된 패키지 들만 남긴다
        // 만약 pkgList에 없다면 삭제된 패키지이므로 DB에서 제거한다
        pkgListDB.forEach {
            if (pkgList.contains(it)) pkgList.remove(it)
            else {
                runBlocking {
                    withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                        bModeDB.bModePackageDataDao().deleteDataByPackageName(it)
                    }
                }
            }
        }

        // 새로 추가된 패키지들을 DB에 추가한다
        pkgList.forEach {
            runBlocking {
                withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                    bModeDB.bModePackageDataDao().insert(
                        BModePackageData(it.key, it.value, false, 0, 0, 0)
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkUsageStatsPermission() {
        var granted = false
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(), packageName)

        if (mode == AppOpsManager.MODE_DEFAULT) {
            granted =
                checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) ==
                        PackageManager.PERMISSION_GRANTED
        }
        else {
            granted = mode == AppOpsManager.MODE_ALLOWED
        }

        if (!granted) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }
    }

    fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                // send user to the device settings
                val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                startActivity(myIntent)
            }
        }
    }
}