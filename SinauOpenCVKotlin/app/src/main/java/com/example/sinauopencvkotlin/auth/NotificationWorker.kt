package com.example.sinauopencvkotlin.auth

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.sinauopencvkotlin.R

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Log untuk memverifikasi bahwa pekerjaan dijalankan
        Log.d("NotificationWorker", "Notification work started")
        showNotification(applicationContext)
        return Result.success()
    }

    private fun showNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "sendi_reminder_channel"

        // Buat Notification Channel (hanya diperlukan untuk Android 8.0 ke atas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Sendi Reminder Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Setup untuk membuka aplikasi ketika notifikasi diklik
        val intent = Intent(context, LoginActivity::class.java)
        // Menambahkan FLAG_IMMUTABLE untuk PendingIntent
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Buat dan tampilkan notifikasi
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Reminder")
            .setContentText("Ayo cek sendi lagi!")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }
}
