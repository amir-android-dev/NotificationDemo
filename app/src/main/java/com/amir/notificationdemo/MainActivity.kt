package com.amir.notificationdemo

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
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

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun displayNotification() {
        //any Int value
        val notificationID = 45
        //configured an intent to launch that activity
        val tapResultIntent=Intent(this,SecondActivity::class.java)
        //creating pending Intent including this intent
        //What this flag does is , When the system create a new intent, if the pendingIntent already exists in the memory, system. keep it but replace its extra data with what is in this new Intent.
        val pendingIntent:PendingIntent= PendingIntent.getActivity(this,0,tapResultIntent,PendingIntent.FLAG_UPDATE_CURRENT)
        //action button 1
        val intent2=Intent(this,DetailsActivity::class.java)
        val pendingIntent2:PendingIntent= PendingIntent.getActivity(this,0,intent2,PendingIntent.FLAG_UPDATE_CURRENT)
        val aciton2 : NotificationCompat.Action= NotificationCompat.Action.Builder(0,"Details",pendingIntent2).build()
        //action button 2
        val intent3=Intent(this,SettingsActivity::class.java)
        val pendingIntent3:PendingIntent= PendingIntent.getActivity(this,0,intent3,PendingIntent.FLAG_UPDATE_CURRENT)
        val aciton3 : NotificationCompat.Action= NotificationCompat.Action.Builder(0,"Setting",pendingIntent3).build()

        //notifivation compat to create the notification object
        val notification = NotificationCompat.Builder(this@MainActivity, channelID)
            .setContentTitle("Demo Title")
            .setContentText("This is a demo notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .addAction(aciton2)
            .addAction(aciton3)
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