package com.seymen.seymentravel.utils

import android.app.Activity
import android.view.View
import com.seymen.seymentravel.MainActivity

class NavBarHelper {
        companion object {
            fun navBarIsVisible(requireActivity: Activity){
                val mainActivity = requireActivity as MainActivity
                if(mainActivity.bottomNavigationView.visibility != View.VISIBLE){
                    mainActivity.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }


}