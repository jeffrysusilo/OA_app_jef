package com.example.sinauopencvkotlin.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.sinauopencvkotlin.MainActivity
import com.example.sinauopencvkotlin.R
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.*
import java.util.concurrent.TimeUnit
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainMenuActivity : AppCompatActivity() {

    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val buttonMain = findViewById<Button>(R.id.buttonMain)
        val buttonNews = findViewById<Button>(R.id.buttonNews)
        val buttonForum = findViewById<Button>(R.id.buttonForum)

        buttonMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonNews.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }

        buttonForum.setOnClickListener {
            val intent = Intent(this, ForumActivity::class.java)
            startActivity(intent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Request the permission
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), NOTIFICATION_PERMISSION_REQUEST_CODE)
            } else {
                // Permission already granted
                scheduleNotificationWork()
            }
        } else {
            // For devices below Android 13, we don't need to request the permission
            scheduleNotificationWork()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                scheduleNotificationWork()
            } else {
                // Permission denied
                Toast.makeText(this, "Notification permission is required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun scheduleNotificationWork() {
        val workManager = WorkManager.getInstance(applicationContext)

        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        // Menjadwalkan pekerjaan pada tanggal 1 dan 16
        scheduleWorkAtSpecificDate(workManager, 1, currentMonth, currentYear)
        scheduleWorkAtSpecificDate(workManager, 16, currentMonth, currentYear)
        scheduleWorkAtSpecificDate(workManager, 7, currentMonth, currentYear)
    }

    private fun scheduleWorkAtSpecificDate(workManager: WorkManager, dayOfMonth: Int, month: Int, year: Int) {
        val calendar = Calendar.getInstance().apply {
            set(year, month, dayOfMonth, 13, 50, 0)
            set(Calendar.MILLISECOND, 0)
        }


        val delayInMillis = calendar.timeInMillis - System.currentTimeMillis()
        Log.d("NotificationSchedule", "Scheduled for: ${calendar.time}, delayInMillis: $delayInMillis")

        if (delayInMillis > 0) {
            val workRequest: WorkRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
                .build()

            workManager.enqueue(workRequest)
        }
    }
}
