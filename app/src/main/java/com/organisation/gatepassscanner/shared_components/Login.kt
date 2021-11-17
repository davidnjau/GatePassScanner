package com.organisation.gatepassscanner.shared_components

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.organisation.gatepassscanner.R
import com.organisation.gatepassscanner.staff.MainActivity

class Login : AppCompatActivity() {

    private lateinit var cardViewLogin: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        cardViewLogin = findViewById(R.id.cardViewLogin)

        cardViewLogin.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }
}