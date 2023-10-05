package com.example.mad8_63_5b6

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.WindowCompat
import com.example.mad8_63_5b6.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window,false)
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.creatcard.visibility=View.GONE
        binding.creatalarm.setOnClickListener{
            showTimerDialog()
        }
        binding.alarmcanclebtn.setOnClickListener {
            setAlarm(-1,"Stop")
            binding.creatcard.visibility=View.GONE
        }
    }
    private fun showTimerDialog(){
        val cldr:Calendar=Calendar.getInstance()
        val hour:Int=cldr.get(Calendar.HOUR_OF_DAY)
        val minutes:Int=cldr.get(Calendar.MINUTE)
        val picker =TimePickerDialog(this,
            {tp,sHour,sMinute->sendDialogDataToActivity(sHour,sMinute)},
            hour,minutes,false

        )
        picker.show()
    }
    private fun sendDialogDataToActivity(hour:Int,minute:Int){
        val alarmCalender=android.icu.util.Calendar.getInstance()
        val year:Int=alarmCalender.get(Calendar.YEAR)
        val month:Int=alarmCalender.get(Calendar.MONTH)
        val day:Int=alarmCalender.get(Calendar.DATE)
        alarmCalender.set(year,month,day,hour,minute,0)
        binding.newsetalarm.text=SimpleDateFormat("hh:mm ss a").format(alarmCalender)
        binding.creatcard.visibility=View.VISIBLE
        setAlarm(alarmCalender.timeInMillis,"Start")
    }
    private fun setAlarm(millisTime:Long,str:String){
        val intent=Intent(this,AlarmBrodcastReceiver::class.java)
        intent.putExtra("Service1",str)
        val pendingIntent=PendingIntent.getBroadcast(applicationContext,234324243,intent,PendingIntent.FLAG_IMMUTABLE)
        val alarmManager=getSystemService(ALARM_SERVICE) as AlarmManager
        if(str=="Start"){
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,millisTime,pendingIntent
            )
            Toast.makeText(this,"Start Alarm", Toast.LENGTH_SHORT).show()
        }
        else if(str=="Stop"){
            alarmManager.cancel(pendingIntent)
            sendBroadcast(intent);
            Toast.makeText(this,"Stop Alarm",Toast.LENGTH_SHORT).show()
        }

    }
}