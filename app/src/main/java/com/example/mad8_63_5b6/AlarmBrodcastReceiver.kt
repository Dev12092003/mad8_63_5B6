package com.example.mad8_63_5b6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmBrodcastReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context,intent: Intent){
        val str1=intent.getStringExtra("Service1")
        if(str1=="Start" || str1=="Stop"){
            val intentService=Intent(context,AlarmService::class.java)
            intentService.putExtra("Service1",intent.getStringExtra("alarmkey"))
            if(str1=="start"){
                context.startService(intentService)
            }
            else if(str1=="stop") {
                context.stopService(intentService)
            }
        }
    }
}