package com.organisation.gatepassscanner.shared_components

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.organisation.gatepassscanner.R
import com.organisation.gatepassscanner.admin.AdminHome
import com.organisation.gatepassscanner.helperclass.Formatter
import com.organisation.gatepassscanner.staff.MainActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler()
        handler.postDelayed(Runnable {

            val loginStatus = Formatter().checkLoginStatus(this)
            val isLoggedIn = loginStatus.isLoggedIn
            val roles = loginStatus.roles

            if (isLoggedIn){

                //The user is logged in
                if (roles != null){

                    if (roles == "ROLE_ADMIN" || roles == "ROLE_SECURITY"){

                        val intent = Intent(this@SplashScreen, AdminHome::class.java)
                        startActivity(intent)
                        finish()

                    }else{
                        val intent = Intent(this@SplashScreen, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }else{
                    val intent = Intent(this@SplashScreen, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }


            }else{
                val intent = Intent(this@SplashScreen, Login::class.java)
                startActivity(intent)
                finish()
            }



        }, 3000)

    }

}