package com.organisation.gatepassscanner.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.organisation.gatepassscanner.R
import com.organisation.gatepassscanner.helperclass.Formatter

class AdminHome : AppCompatActivity() {

    private var formatter = Formatter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        formatter.customMainToolbar(this)
        formatter.customAdminBottomNavigation(this)

    }
}