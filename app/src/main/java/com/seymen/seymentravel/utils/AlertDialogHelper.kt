package com.seymen.seymentravel.utils

import android.app.AlertDialog
import android.content.Context

class AlertDialogHelper {

    companion object {

        fun createSimpleAlertDialog(
            context: Context, title: String, message: String?, positiveButtonText: String
        ) {
            val builder = AlertDialog.Builder(context)
            if (title.isNotEmpty()) builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(positiveButtonText) { _, _ -> }
            builder.show()
        }

    }

}