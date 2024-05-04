package com.example.currencyconverter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.currencyconverter.DataClass.LoginRegister


class Login2 :Fragment() {


    lateinit var flag : LoginRegister
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var layout = inflater.inflate(R.layout.fragment_login2, container, false)

        var userName = layout.findViewById<TextView>(R.id.username)
        var password = layout.findViewById<TextView>(R.id.password)

        sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        flag = activity as LoginRegister
        layout.findViewById<Button>(R.id.loginButton).setOnClickListener(View.OnClickListener {

            var nameInput = userName.text.toString()
            var passwordInput = password.text.toString()

            if(nameInput.isNotBlank() && passwordInput.isNotBlank()){
                validateCredentials(nameInput,passwordInput)

            }else{
                Toast.makeText(requireActivity(),"Enter the credentials",Toast.LENGTH_SHORT).show()
            }
        })

        layout.findViewById<TextView>(R.id.newSign).setOnClickListener {
                flag.LogRegFlag(false)
        }


        return layout
    }

    private fun validateCredentials(name: String, password: String) {
        val storedName = sharedPreferences.getString("username", "")
        val storedPassword = sharedPreferences.getString("userPassword", "")



        if (name.equals(storedName) && password.equals(storedPassword)) {
            // Login successful!
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()

        } else {
            // Login failed
            Toast.makeText(requireActivity(), "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }
    
    
}