package com.amir.notificationdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipDescription
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {
    lateinit var btnNotification: Button

    // Before write codes to send a notification, we have to first write codes to create a notification channel.
    private val channelID = "com.amir.notificationdemo.channel1"

    //notification manager instance, required to create a notification channel as well as notification instance
    private var notificationManager: NotificationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnNotification = findViewById(R.id.btn_notification)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(channelID, "Demo Channel", "this is a demo")
        btnNotification.setOnClickListener {
            displayNotification()
        }
    }

    private fun displayNotification() {
        //any Int value
        val notificationID = 45
        //notifivation compat to create the notification object
        val notification = NotificationCompat.Builder(this@MainActivity, channelID)
            .setContentTitle("Demo Title")
            .setContentText("This is a demo notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager?.notify(notificationID, notification)
    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        //A notification channel has an ID , a channel name and a channel description.
        //SDK verion must be android oreo or above
        //adding a validation to avoid app crashing for lower versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//notification channel has a importance level
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }


    }
}