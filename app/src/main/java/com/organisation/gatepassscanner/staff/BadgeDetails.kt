package com.organisation.gatepassscanner.staff

import android.graphics.Bitmap
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.google.zxing.WriterException
import com.organisation.gatepassscanner.R
import com.organisation.gatepassscanner.dto.StaffDetails
import com.organisation.gatepassscanner.helperclass.Formatter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_badge_details.*

class BadgeDetails : AppCompatActivity() {

    private var formatter = Formatter()
    private lateinit var qrCodeIV: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badge_details)

        qrCodeIV = findViewById(R.id.idIVQrcode)

        formatter.customBottomNavigation(this)
        formatter.customToolbar(this, resources.getString(R.string.badge))

    }

    override fun onStart() {
        super.onStart()

        getUserDetails()
        formatter.generateQRCode(qrCodeIV, this)
    }

    private fun getUserDetails() {

        val staffDetails = formatter.getProfileDetails(this)
        if (staffDetails != null){

            val fullName = staffDetails.fullName
            val position = staffDetails.position

            val imageUrl = staffDetails.imageUrl

            tvFullNames.text = fullName
            tvPosition.text = position


            if (imageUrl != ""){

                Picasso.get().load(imageUrl).fit().centerCrop()
                    .placeholder(R.drawable.profile1)
                    .error(R.drawable.profile1)
                    .into(imageViewProfile)
            }

        }

    }

}