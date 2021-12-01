package com.organisation.gatepassscanner.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.organisation.gatepassscanner.R
import com.organisation.gatepassscanner.helperclass.Formatter
import kotlinx.android.synthetic.main.activity_admin_home.*

class AdminHome : AppCompatActivity() {

    private var formatter = Formatter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        formatter.customMainToolbar(this)
        formatter.customAdminBottomNavigation(this)

        cardViewScan.setOnClickListener {

            val intent = Intent(this, AdminScanQRCode::class.java)
            startActivity(intent)

        }

    }
}