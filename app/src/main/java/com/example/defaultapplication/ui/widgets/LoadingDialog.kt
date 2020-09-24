package com.example.defaultapplication.ui.widgets

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.defaultapplication.R
import com.example.defaultapplication.core.GlideApp
import kotlinx.android.synthetic.main.dialog_loading.*


class LoadingDialog : DialogFragment() {

    // Message to show
    internal var message = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        messageLoading.text = message
        GlideApp.with(this).load(R.drawable.lo3).into(iconLoading)
    }

    companion object {
        const val TAG = "LoadingDialog"

        fun newInstance(message: String): LoadingDialog {
            val dialog = LoadingDialog()
            dialog.message = message
            return dialog
        }
    }
}