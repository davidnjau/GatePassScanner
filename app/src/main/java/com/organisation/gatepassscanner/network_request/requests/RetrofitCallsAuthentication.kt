package com.organisation.gatepassscanner.network_request.requests

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.organisation.gatepassscanner.R
import com.organisation.gatepassscanner.admin.AdminHome
import com.organisation.gatepassscanner.dto.*
import com.organisation.gatepassscanner.helperclass.CustomDialogToast
import com.organisation.gatepassscanner.helperclass.Formatter
import com.organisation.gatepassscanner.network_request.interfaces.Interface
import com.organisation.gatepassscanner.network_request.builder.RetrofitBuilder
import com.organisation.gatepassscanner.staff.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class RetrofitCallsAuthentication {

    var customDialogToast = CustomDialogToast()

    fun loginUser(context: Context, userLogin: UserLogin){

        CoroutineScope(Dispatchers.Main).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                startLogin(context, userLogin)

            }.join()
        }

    }
    private suspend fun startLogin(context: Context, userLogin: UserLogin) {

        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)
        val editor :SharedPreferences.Editor =  sharedPreferences.edit()

        val job1 = Job()
        CoroutineScope(Dispatchers.Main + job1).launch {

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please wait..")
            progressDialog.setMessage("Login in progress..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            var messageToast = ""
            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                val baseUrl = context.getString(UrlData.BASE_URL.message)
                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
                val apiInterface = apiService.loginUser(userLogin)
                apiInterface.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {

                        CoroutineScope(Dispatchers.Main).launch { progressDialog.dismiss() }

                        if (response.isSuccessful) {

                            var role = ""
                            messageToast = "You have been logged in successfully."

                            val responseBody = response.body()
                            if (responseBody != null){

                                val accessToken = responseBody.accessToken
                                val userId = responseBody.userId
                                val emailAddress = responseBody.emailAddress
                                val firstName = responseBody.firstName
                                val lastName = responseBody.lastName
                                val phoneNumber = responseBody.phoneNumber
                                val imageUrl = responseBody.imageUrl

                                val rolesList = responseBody.roles
                                if (rolesList.isNotEmpty()) {
                                    role = when {
                                        rolesList.contains("ROLE_ADMIN") -> {
                                            "ROLE_ADMIN"
                                        }
                                        rolesList.contains("ROLE_SECURITY") -> {
                                            "ROLE_SECURITY"
                                        }
                                        else -> {
                                            "ROLE_USER"
                                        }
                                    }

                                }

                                var departmentName = ""
                                var positionName =""
                                var timezoneName =""

                                val administration = responseBody.administration
                                if (administration != null){
                                    departmentName = administration.departmentName.toString()
                                    positionName = administration.positionName.toString()
                                    timezoneName = administration.timezoneName.toString()

                                }

                                editor.putBoolean("isLoggedIn", true)
                                editor.putString("accessToken",accessToken)
                                editor.putString("userId",userId)
                                editor.putString("emailAddress",emailAddress)
                                editor.putString("firstName",firstName)
                                editor.putString("lastName",lastName)
                                editor.putString("phoneNumber",phoneNumber)
                                editor.putString("roles", role)
                                editor.putString("imageUrl", imageUrl)
                                editor.putString("departmentName", departmentName)
                                editor.putString("positionName", positionName)
                                editor.putString("timezoneName", timezoneName)
                                editor.apply()
                            }

                            CoroutineScope(Dispatchers.Main).launch {
                                customDialogToast.customDialogToast(
                                    context as Activity,
                                    messageToast
                                )

                                if (role == "ROLE_ADMIN" || role == "ROLE_SECURITY"){

                                    val intent = Intent(context, AdminHome::class.java)
                                    context.startActivity(intent)
                                    context.finish()

                                }else{
                                    val intent = Intent(context, MainActivity::class.java)
                                    context.startActivity(intent)
                                    context.finish()
                                }
                                
                                
                                
                            }

                        } else {

                            editor.putBoolean("isLoggedIn", false)
                            editor.apply()

                            val code = response.code()

                            if (code != 500) {

                                val gson = Gson()
                                val type = object : TypeToken<ErrorDetails>() {}.type
                                val errorDetails : ErrorDetails? = gson.fromJson(response.errorBody()!!.charStream(), type)
                                messageToast = errorDetails?.details ?: "Please try again later."

                                CoroutineScope(Dispatchers.IO).launch {

                                    CoroutineScope(Dispatchers.Main).launch {
                                        customDialogToast.customDialogToast(
                                            context as Activity,
                                            messageToast
                                        )
                                    }



                                }

                            } else {
                                messageToast =
                                    "We are experiencing some server issues. Please try again later"

                                CoroutineScope(Dispatchers.Main).launch {
                                    customDialogToast.customDialogToast(
                                        context as Activity,
                                        messageToast
                                    )
                                }
                            }



                        }


                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.e("-*-*error ", t.localizedMessage)

                        editor.putBoolean("isLoggedIn", false)
                        editor.apply()

                        messageToast = "There is something wrong. Please try again"
                        CoroutineScope(Dispatchers.Main).launch {
                            customDialogToast.customDialogToast(
                                context as Activity,
                                messageToast
                            )
                        }

                        progressDialog.dismiss()
                    }
                })



            }.join()

        }

    }

    fun addTimeStatus(context: Context, dbStaffTime: DbStaffTime){

        CoroutineScope(Dispatchers.Main).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                startTimeStatus(context, dbStaffTime)

            }.join()
        }

    }
    private suspend fun startTimeStatus(context: Context, dbStaffTime: DbStaffTime) {

        val job1 = Job()
        CoroutineScope(Dispatchers.Main + job1).launch {

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please wait..")
            progressDialog.setMessage("Time save in progress..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            val stringMap = Formatter().getHeaders(context)

            var messageToast = ""
            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                val baseUrl = context.getString(UrlData.BASE_URL.message)
                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
                val apiInterface = apiService.addTimeStatus(dbStaffTime,stringMap)
                apiInterface.enqueue(object : Callback<Results> {
                    override fun onResponse(
                        call: Call<Results>,
                        response: Response<Results>
                    ) {

                        CoroutineScope(Dispatchers.Main).launch { progressDialog.dismiss() }

                        if (response.isSuccessful) {

                            val responseBody = response.body()
                            if (responseBody != null){
                                messageToast = responseBody.details
                            }

                            CoroutineScope(Dispatchers.Main).launch {
                                customDialogToast.customDialogToast(
                                    context as Activity,
                                    messageToast
                                )

                                val intent = Intent(context, AdminHome::class.java)
                                context.startActivity(intent)
                                context.finish()

                            }

                        } else {

                            val code = response.code()

                            if (code != 500) {

                                val gson = Gson()
                                val type = object : TypeToken<ErrorDetails>() {}.type
                                val errorDetails : ErrorDetails? = gson.fromJson(response.errorBody()!!.charStream(), type)
                                messageToast = errorDetails?.details ?: "Please try again later."

                                CoroutineScope(Dispatchers.IO).launch {

                                    CoroutineScope(Dispatchers.Main).launch {
                                        customDialogToast.customDialogToast(
                                            context as Activity,
                                            messageToast
                                        )
                                    }



                                }

                            } else {
                                messageToast =
                                    "We are experiencing some server issues. Please try again later"

                                CoroutineScope(Dispatchers.Main).launch {
                                    customDialogToast.customDialogToast(
                                        context as Activity,
                                        messageToast
                                    )
                                }
                            }



                        }


                    }

                    override fun onFailure(call: Call<Results>, t: Throwable) {
                        Log.e("-*-*error ", t.localizedMessage)

                        messageToast = "There is something wrong. Please try again"
                        CoroutineScope(Dispatchers.Main).launch {
                            customDialogToast.customDialogToast(
                                context as Activity,
                                messageToast
                            )
                        }

                        progressDialog.dismiss()
                    }
                })



            }.join()

        }

    }

}

