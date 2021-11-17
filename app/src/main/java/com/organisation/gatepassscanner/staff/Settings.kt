package com.organisation.gatepassscanner.staff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.organisation.gatepassscanner.R
import com.organisation.gatepassscanner.helperclass.Formatter

class Settings : AppCompatActivity() {

    private var formatter = Formatter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        formatter.customBottomNavigation(this)
        formatter.customToolbar(this, resources.getString(R.string.settings))

    }
}