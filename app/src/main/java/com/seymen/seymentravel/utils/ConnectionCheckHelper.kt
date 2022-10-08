package com.seymen.seymentravel.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.seymen.seymentravel.R
import kotlin.system.exitProcess

object ConnectionCheckHelper {

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
    fun checkNetAndClose(context: Context, activity: Activity){
        if(!isNetworkAvailable(context)){
            Toast.makeText(context, R.string.tst_connct, Toast.LENGTH_LONG).show()
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    activity.finish()
                    exitProcess(0)
                }, 4000)
        }
    }
}