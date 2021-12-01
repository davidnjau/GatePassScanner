
package com.organisation.gatepassscanner.helperclass

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Point
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.zxing.WriterException
import com.organisation.gatepassscanner.R
import com.organisation.gatepassscanner.admin.AdminAllScans
import com.organisation.gatepassscanner.admin.AdminHome
import com.organisation.gatepassscanner.admin.AdminScanQRCode
import com.organisation.gatepassscanner.admin.AdminSettings
import com.organisation.gatepassscanner.dto.LoginStatus
import com.organisation.gatepassscanner.dto.StaffDetails
import com.organisation.gatepassscanner.shared_components.Login
import com.organisation.gatepassscanner.shared_components.SplashScreen
import com.organisation.gatepassscanner.staff.Settings
import com.organisation.gatepassscanner.staff.BadgeDetails
import com.organisation.gatepassscanner.staff.MainActivity
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.util.*


class Formatter {

    private lateinit var context: Context

    fun customToolbar(context: Context, title: String){

        val customToolbar = (context as Activity).findViewById<View>(R.id.customToolbarMain)
        val tvToolBarTitle = customToolbar.findViewById<TextView>(R.id.tvToolBarTitle)
        val backButton = customToolbar.findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {

            when (title) {
                context.resources.getString(R.string.badge) -> {
                    val intent = Intent(context, BadgeDetails::class.java)
                    context.startActivity(intent)
                }
                context.resources.getString(R.string.settings) -> {
                    val intent = Intent(context, Settings::class.java)
                    context.startActivity(intent)
                }
                context.resources.getString(R.string.main) -> {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }

                context.resources.getString(R.string.all_scans) -> {
                    val intent = Intent(context, AdminHome::class.java)
                    context.startActivity(intent)
                }
                context.resources.getString(R.string.scan_details) -> {
                    val intent = Intent(context, AdminHome::class.java)
                    context.startActivity(intent)
                }
                context.resources.getString(R.string.scan_qr) -> {
                    val intent = Intent(context, AdminHome::class.java)
                    context.startActivity(intent)
                }
                context.resources.getString(R.string.admin_settings) -> {
                    val intent = Intent(context, AdminHome::class.java)
                    context.startActivity(intent)
                }
                else ->{
                    val intent = Intent(context, SplashScreen::class.java)
                    context.startActivity(intent)
                }
            }

        }
        tvToolBarTitle.text = title
    }
    fun customBottomNavigation(context1: Context){

        context = context1

        val bottomNavigation = (context1 as Activity).findViewById<View>(R.id.customBottomToolbar)
        val bottomNavigationView : BottomNavigationView = bottomNavigation.findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener)

    }



    private var navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)

                }
                R.id.navigation_card -> {

                    val intent = Intent(context, BadgeDetails::class.java)
                    context.startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_settings -> {
                    val intent = Intent(context, Settings::class.java)
                    context.startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    fun saveCurrentActivity(title: String){

    }

    fun customMainToolbar(context: Context){

        val customToolbar = (context as Activity).findViewById<View>(R.id.customToolbar)
        val tvToolBarTitle = customToolbar.findViewById<TextView>(R.id.tvToolBarTitle)
        val imageViewProfile = customToolbar.findViewById<ImageView>(R.id.imageViewProfile)
        imageViewProfile.setOnClickListener {
            createDialog(context)
        }

        val staffDetails = getProfileDetails(context)
        if (staffDetails != null){

            val greeting = "Hello, ${staffDetails.fullName}"
            tvToolBarTitle.text = greeting

            val imageUrl = staffDetails.imageUrl
            if (imageUrl != ""){
                Picasso.get().load(imageUrl).fit().centerCrop()
                    .placeholder(R.drawable.profile1)
                    .error(R.drawable.profile1)
                    .into(imageViewProfile)
            }

        }

    }

    private fun createDialog(context: Context) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Log out?")
        builder.setMessage("Are you sure you want to log out?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

            val sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)
            val editor :SharedPreferences.Editor =  sharedPreferences.edit()

            editor.putBoolean("isLoggedIn", false)
            editor.apply()

            val intent = Intent(context, Login::class.java)
            context.startActivity(intent)
            (context as Activity).finish()

            CustomDialogToast().customDialogToast(  context, "You have been logged out.")


        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

            CustomDialogToast().customDialogToast(context as Activity, "Log out has been cancelled.")


        }

        builder.show()


    }


    public fun getProfileDetails(context: Context): StaffDetails? {

        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)
        val id = sharedPreferences.getString("userId",null)
        val phoneNumber = sharedPreferences.getString("phoneNumber",null)
        val firstName = sharedPreferences.getString("firstName","")
        val lastName = sharedPreferences.getString("lastName","")
        val emailAddress = sharedPreferences.getString("emailAddress",null)
        val imageUrl = sharedPreferences.getString("imageUrl",null)
        val department = sharedPreferences.getString("departmentName",null)
        val position = sharedPreferences.getString("positionName",null)


        var fullName = if (firstName != "" && lastName != ""){
            "$firstName $lastName"
        }else{
            null
        }

        return if (id != null && phoneNumber != null && fullName != null
            && emailAddress != null && imageUrl != null && department != null && position != null
        ) {
            StaffDetails(id, phoneNumber, fullName, emailAddress, imageUrl,department, position)
        }else{
            null
        }

    }

    fun checkLoginStatus(context: Context): LoginStatus {

        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name),0)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false)

        val staffDetails = getProfileDetails(context)
        return if (staffDetails != null) {
            val department = staffDetails.department
            LoginStatus(isLoggedIn, department)
        }else{
            LoginStatus(isLoggedIn, null)
        }


    }


    fun customAdminBottomNavigation(context1: Context){

        context = context1

        val bottomNavigation = (context1 as Activity).findViewById<View>(R.id.bottom_navigation)
        val bottomNavigationView : BottomNavigationView = bottomNavigation.findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener1)

    }

    private var navigationItemSelectedListener1 =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(context, AdminHome::class.java)
                    context.startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_scans -> {
                    val intent = Intent(context, AdminAllScans::class.java)
                    context.startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_camera -> {

                    val intent = Intent(context, AdminScanQRCode::class.java)
                    context.startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_settings -> {
                    val intent = Intent(context, AdminSettings::class.java)
                    context.startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    fun generateCafeteriaQRCode(qrCodeIV: ImageView, context: Context) {

        val userDetails = getProfileDetails(context)
        val gson = Gson()
        val jsonObject = gson.toJson(userDetails)

        Log.e("*-*-*-* ", jsonObject)

        val manager = context.getSystemService(AppCompatActivity.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width: Int = point.x
        val height: Int = point.y
        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4
        val qrgEncoder = QRGEncoder(jsonObject, null, QRGContents.Type.TEXT, dimen)
        try {
            val bitmap = qrgEncoder.encodeAsBitmap()
            qrCodeIV.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Log.e("Tag", e.toString())
        }

    }




}


