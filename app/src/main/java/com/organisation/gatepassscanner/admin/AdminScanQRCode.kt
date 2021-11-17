package com.organisation.gatepassscanner.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.organisation.gatepassscanner.R
import com.organisation.gatepassscanner.helperclass.Formatter

class AdminScanQRCode : AppCompatActivity() {

    private var formatter = Formatter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_scan_qrcode)

        formatter.customToolbar(this, resources.getString(R.string.scan_qr))
        formatter.customAdminBottomNavigation(this)
    }
}