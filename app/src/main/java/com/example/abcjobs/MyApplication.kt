package com.example.abcjobs

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import net.gotev.uploadservice.BuildConfig
import net.gotev.uploadservice.UploadServiceConfig

class MyApplication : Application() {
    companion object {
        const val notificationChannelId = "UploadServiceChannel"
    }
    private fun createNotificationChannel() {
        val name = "Upload Service Channel"
        val descriptionText = "Upload Service Channel"
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(notificationChannelId, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        UploadServiceConfig.initialize(
            context = this,
            defaultNotificationChannel = notificationChannelId,
            debug = BuildConfig.DEBUG
        )
    }
}