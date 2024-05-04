package com.example.currencyconverter

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.currencyconverter.DataClass.LoginRegister
import org.w3c.dom.Text


class Registration :Fragment() {
    lateinit var flag : LoginRegister
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var layout = inflater.inflate(R.layout.fragment_registration, container, false)

        var newNameInput = layout.findViewById<EditText>(R.id.NewUsername)
        var newPassInput = layout.findViewById<EditText>(R.id.newpassword)
        var newPhoneInput = layout.findViewById<EditText>(R.id.newPhoneNumber)
        var newemailInput = layout.findViewById<EditText>(R.id.newEmail)
        var registerbtn = layout.findViewById<Button>(R.id.signUpbtn)
        var existUser = layout.findViewById<TextView>(R.id.AlreadyLogin)

        existUser.setOnClickListener {
            flag.LogRegFlag(true)
        }

        sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        flag = activity as LoginRegister

        registerbtn.setOnClickListener {

            val name = newNameInput.text.toString()
            val phone = newPhoneInput.text.toString()
            val email = newemailInput.text.toString()
            val password = newPassInput.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                checkAndRegisterUser(name, phone,email,  password)
                flag.LogRegFlag(true)
            } else {
                showToast("Please enter all the details")
            }

        }
        return layout
    }

    private fun showToast(s: String) {
        Toast.makeText(requireActivity(),s, Toast.LENGTH_SHORT).show()
    }

    private fun checkAndRegisterUser(name:String, phone: String,email:String, password: String) {
        val existingPhone = sharedPreferences.getString("username", "")

        if (existingPhone.equals(phone)  ) {
            showToast("User already exists")
        } else {
            // Register new user
            saveUserData(name,phone,email, password)
            showToast("Registration successful. Please Login")

        }
    }

    private fun saveUserData(name:String,phone: String,email:String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", name)
        editor.putString("userPhone", phone)
        editor.putString("userEmail", email)
        editor.putString("userPassword", password)  // Consider hashing for security
        editor.apply()
    }



}