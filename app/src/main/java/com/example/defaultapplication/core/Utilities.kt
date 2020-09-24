package com.example.defaultapplication.core

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.defaultapplication.R

object Utilities {

    /**
     * Show Alert Dialog
     */
    fun showAlertDialog(context: Context, message: String, title: Int = R.string.title_alert_warning, closure: (() -> Unit)? = null) {
        val dialog = AlertDialog.Builder(context, R.style.DialogTheme)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.title_button_ok) { dialog, _ ->
                dialog.dismiss()
                closure?.invoke()
            }
            .create()

        dialog.show()

        val messageView = dialog.findViewById<TextView>(android.R.id.message)
        messageView?.gravity = Gravity.CENTER

        val titleView = dialog.findViewById<TextView>(androidx.appcompat.R.id.alertTitle)
        titleView?.textSize = 18F
    }

}