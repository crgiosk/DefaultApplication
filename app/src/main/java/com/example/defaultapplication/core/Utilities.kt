package com.example.defaultapplication.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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

@BindingAdapter("android:loadImage")
fun ImageView.loadImage(imageUrl: String?) {
    val view  = this
    Glide.with(this.context)
        .asBitmap()
        .load(imageUrl).apply(RequestOptions().circleCrop())
        .into(object : CustomTarget<Bitmap>() {

            override fun onLoadFailed(errorDrawable: Drawable?) {
                view.setImageDrawable(errorDrawable)
            }
            override fun onLoadCleared(placeholder: Drawable?) {
                view.setImageDrawable(placeholder)
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                view.setImageBitmap(resource)
            }
        })
}
