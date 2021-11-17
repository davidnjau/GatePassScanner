package com.organisation.gatepassscanner.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.organisation.gatepassscanner.R
import com.organisation.gatepassscanner.helperclass.Formatter

class AdminAllScans : AppCompatActivity() {

    private var formatter = Formatter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_all_scans)

        formatter.customToolbar(this, resources.getString(R.string.settings))
        formatter.customAdminBottomNavigation(this)
    }
}