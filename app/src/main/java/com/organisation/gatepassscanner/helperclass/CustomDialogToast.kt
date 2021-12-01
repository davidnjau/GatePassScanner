package com.organisation.gatepassscanner.helperclass

import android.app.Activity
import android.os.Message
import android.widget.TextView
import android.widget.Toast
import com.organisation.gatepassscanner.R

class CustomDialogToast {

    private lateinit var textView : TextView

    public fun customDialogToast(activity: Activity, message: String){

        val layoutInflater = activity.layoutInflater
        val layout = layoutInflater.inflate(R.layout.custom_dialog, activity.findViewById(R.id.custom_toast_layout_id))
        textView = layout.findViewById(R.id.tvMessage)
        textView.text = message
        val toast = Toast(activity)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()


    }

}