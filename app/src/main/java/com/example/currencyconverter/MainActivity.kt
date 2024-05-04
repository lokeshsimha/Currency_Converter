package com.example.currencyconverter

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class MainActivity :AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    lateinit var toolbar : MaterialToolbar
    private lateinit var navigation: DrawerLayout
    private lateinit var calen : Calendar
    private lateinit var picker: MaterialTimePicker
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent : PendingIntent

    var presentDay = 0
    var presentMonth = 0
    var presentyear = 0
    var presentMin =0
    var presentHour =0


    var selectedDay = 0
    var selectedMonth = 0
    var selectedyear = 0
    var selectedHour =0
    var selectedMin =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


            toolBar(this)

            navigation = findViewById(R.id.drawer_layout)
            calen = Calendar.getInstance()
        navigationDrawer(this)
            createNotificationChannel()





        }


        fun toolBar(context: Context){
            toolbar = findViewById(R.id.toolBar)
            toolbar.setOnMenuItemClickListener {
                    menuItem ->
                when(menuItem.itemId){
                    R.id.profile -> {

//                        val profile = ProfileDialog()
//                        profile.show(supportFragmentManager, "ProfileDialog")
                        true
                    }
                    R.id.CurrencyCompare ->{
                        var conr = Intent(this, Convertion::class.java)
                        startActivity(conr)
                        true
                    }
                    else -> {
                        false
                    }
                }

            }

        }


    fun navigationDrawer(context: Context){

        val navDrawer = findViewById<NavigationView>(R.id.navigation_drawer)
        navDrawer.setNavigationItemSelectedListener(this)

        val toogle = ActionBarDrawerToggle(this, navigation, toolbar,0,0)
        navigation.addDrawerListener(toogle)
        toogle.syncState()

    }

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when(item.itemId){
                R.id.profile ->  {

                }
                R.id.feedback -> {
//                    var feedback = Intent(this, FeedBack::class.java)
//                    startActivity(feedback)
//                    var feedback = Intent(this, feedBack::class.java)
//                    startActivity(feedback)
//
                    val feedBack = Intent(this,feed::class.java)
                    startActivity(feedBack)
                }
                R.id.fav ->{
                    Toast.makeText(this, "Fav", Toast.LENGTH_SHORT).show()
                }
                R.id.currencylist ->{
                    var Convertion  = Intent(this, Convertion::class.java)
                    startActivity(Convertion)

                }
                R.id.alarm -> {
                    getCurrentDate()
                    DatePickerDialog(this, this, presentyear,presentMonth,presentDay).show()

                }

            }
            navigation.closeDrawer(GravityCompat.START)
            return true
        }





        private fun createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "androidknowledge"
                val description = "Check For Alarm Manager"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel("androidknowledge", name, importance)
                channel.description = description
                val notificationManager = getSystemService(
                    NotificationManager::class.java
                )
                notificationManager.createNotificationChannel(channel)
            }

        }

    fun getCurrentDate(){
        var calender = Calendar.getInstance()
        presentDay = calender.get(Calendar.DAY_OF_MONTH)
        presentMonth = calender.get(Calendar.MONTH)
        presentyear = calender.get(Calendar.YEAR)
    }
    fun getCurrentTime(){
        var calender = Calendar.getInstance()
        presentHour = calender.get(Calendar.HOUR_OF_DAY)
        presentMin = calender.get(Calendar.MINUTE)
    }


    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        selectedyear = year
        selectedMonth = month
        selectedDay = day
        getCurrentTime()
        TimePickerDialog(this, this, presentHour, presentMin , false).show()
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, min: Int) {
        selectedHour = hour
        selectedMin = min

        calen.apply {
            set(Calendar.YEAR, selectedyear)
            set(Calendar.MONTH, selectedMonth)
            set(Calendar.DAY_OF_MONTH, selectedDay)
            set(Calendar.HOUR_OF_DAY, selectedHour)
            set(Calendar.MINUTE, selectedMin)
            set(Calendar.SECOND, 0)
        }

        setAlarm()
    }


    private fun setAlarm() {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReciver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 123, intent,
            PendingIntent.FLAG_IMMUTABLE)
            var currentMill = System.currentTimeMillis()
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calen.timeInMillis,
                pendingIntent
            )

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            calen.timeInMillis,
            pendingIntent
        )

        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show()
    }



}