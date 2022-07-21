package com.amir.notificationdemo

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

class SecondActivity : AppCompatActivity() {
    lateinit var tvUserInput:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        tvUserInput=findViewById(R.id.tv_user_input)
//invoke the fun in onCreate
        recievedInput()

    }

    //write codes to receive the user input and update the notifications
    //Then get the remoteInput using the current intent.
    private fun recievedInput() {
         val KEY_REPLAY="key_replay"
        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            //here we need the key which we create in mainActivity
            val inputString=remoteInput.getCharSequence(KEY_REPLAY).toString()
            tvUserInput.text = inputString
            //let’s write codes to update the notification.
            //we hse the same channel id and notification id we used in MainActivity
            val channelID = "com.amir.notificationdemo.channel1"
            val notificationID = 45
            val repliedNotification=NotificationCompat.Builder(this,channelID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentText("Your replay recieved")
                .build()
            val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationID,repliedNotification)

        }
    }
}