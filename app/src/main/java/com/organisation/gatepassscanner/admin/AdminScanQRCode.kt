package com.organisation.gatepassscanner.admin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import com.organisation.gatepassscanner.R
import com.organisation.gatepassscanner.helperclass.Formatter
import kotlinx.android.synthetic.main.activity_admin_scan_qrcode.*
import android.widget.Toast

import com.organisation.gatepassscanner.staff.MainActivity


import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.organisation.gatepassscanner.dto.DbStaffTime
import com.organisation.gatepassscanner.helperclass.CustomDialogToast
import com.organisation.gatepassscanner.network_request.requests.RetrofitCallsAuthentication
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class AdminScanQRCode : AppCompatActivity() {

    private var formatter = Formatter()
    private val MY_PERMISSIONS_REQUEST_CODE = 123
    private lateinit var codeScanner: CodeScanner
    private var retrofitCallsAuthentication: RetrofitCallsAuthentication = RetrofitCallsAuthentication()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_scan_qrcode)

        formatter.customToolbar(this, resources.getString(R.string.scan_qr))
        formatter.customAdminBottomNavigation(this)

        radioGroup.setOnCheckedChangeListener { _, _ ->
            linearScan.visibility = View.VISIBLE
        }

        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view1)
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
//                chipStatus.text = "Scanning.."

                val scannedResult = it.text
                formatData(scannedResult)

            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                checkForPermission()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

    }

    private fun formatData(scannedResult: String?) {

        if (scannedResult != null){

            try {

                val scannedJson = JSONObject(scannedResult)
                val userName = scannedJson.getString("fullName")
                val position = scannedJson.getString("position")
                val userId = scannedJson.getString("id")

//            linearChip.visibility = View.GONE
                linearDetails.visibility = View.VISIBLE

                val date = Date()
                val sdf = SimpleDateFormat("hh:mm a")
                val sdfServer = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a ")
                val time = sdf.format(date)
                val serverDate = sdfServer.format(date)

                tvTime.text = time
                tvName.text = userName
                tvRole.text = position

//            val newKey = formatter.hashWithMD5(userName)

                val selectedId = radioGroup.checkedRadioButtonId
                val radioButton = findViewById<RadioButton>(selectedId)
                val selectedStatus = radioButton.text

                var  timeStatus = ""
                if (selectedStatus == "Arrival Time"){ timeStatus = "arrivalTime" }
                if (selectedStatus == "Departure Time"){ timeStatus = "departureTime" }

                val dbStaffTime = DbStaffTime(userId, timeStatus, serverDate)
                retrofitCallsAuthentication.addTimeStatus(this, dbStaffTime)


            }catch (e: Exception){
                CustomDialogToast().customDialogToast(this, "Please try again")
            }



        }

    }


    override fun onStart() {
        super.onStart()

        checkForPermission()
    }

    private fun checkForPermission() {

        if (
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            +
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

            != PackageManager.PERMISSION_GRANTED
        ) {

            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                ||
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

            ) {
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                val builder = AlertDialog.Builder(this)
                builder.setMessage(
                    "Read and Write External" +
                            " Storage permissions are required for the app."
                )
                builder.setTitle("Please grant these permissions")
                builder.setPositiveButton(
                    "OK"
                ) { _, _ ->
                    ActivityCompat.requestPermissions(this,
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE

                        ),
                        MY_PERMISSIONS_REQUEST_CODE
                    )
                }
                builder.setNeutralButton(
                    "Cancel"
                ) { _, _ ->
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                val dialog = builder.create()
                dialog.show()
            } else {
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    MY_PERMISSIONS_REQUEST_CODE
                )
            }
        }

    }


}