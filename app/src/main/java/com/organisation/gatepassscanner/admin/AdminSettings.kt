package com.organisation.gatepassscanner.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.organisation.gatepassscanner.R
import com.organisation.gatepassscanner.helperclass.Formatter

class AdminSettings : AppCompatActivity() {

    private var formatter = Formatter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_settings)

        formatter.customToolbar(this, resources.getString(R.string.admin_settings))
        formatter.customAdminBottomNavigation(this)
    }
}