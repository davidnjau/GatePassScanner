package com.organisation.gatepassscanner.shared_components

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import com.organisation.gatepassscanner.R
import com.organisation.gatepassscanner.dto.*
import com.organisation.gatepassscanner.network_request.requests.RetrofitCallsAuthentication
import com.organisation.gatepassscanner.staff.MainActivity

class Login : AppCompatActivity() {

    private lateinit var cardViewLogin: CardView
    private lateinit var frameLayout: FrameLayout

    private lateinit var etEmailAddress: EditText
    private lateinit var etPassword: EditText
    private var retrofitCallsAuthentication: RetrofitCallsAuthentication = RetrofitCallsAuthentication()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        cardViewLogin = findViewById(R.id.cardViewLogin)

        etEmailAddress = findViewById(R.id.etEmailAddress)
        etPassword = findViewById(R.id.etPassword)
        frameLayout = findViewById(R.id.frameLayout)

        cardViewLogin.setOnClickListener { startLogin() }
        frameLayout.setOnClickListener { startLogin() }


    }

    private fun startLogin() {

        val emailAddress = etEmailAddress.text.toString()
        val passWord = etPassword.text.toString()

        if (!TextUtils.isEmpty(emailAddress) && !TextUtils.isEmpty(passWord)){

            val userLogin = UserLogin(emailAddress, passWord)
            retrofitCallsAuthentication.loginUser(this, userLogin)

        }else{
            if (TextUtils.isEmpty(emailAddress))
                etEmailAddress.error = "Field cannot be empty."

            if (TextUtils.isEmpty(passWord))
                etPassword.error = "Field cannot be empty."

        }


    }
}