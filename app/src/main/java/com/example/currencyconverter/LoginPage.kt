package com.example.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.currencyconverter.DataClass.LoginRegister

class LoginPage :AppCompatActivity(), LoginRegister {

    lateinit var fragmentManager : FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page2)




            var login2 = Login2()
            fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.logRegFragment,login2).commit()




    }

    override fun LogRegFlag(flag: Boolean) {
        if(flag){
            var login2 = Login2()
            fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.logRegFragment,login2).commit()
        }
        else{
            var register = Registration()
            fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.logRegFragment,register).commit()
        }
    }
}