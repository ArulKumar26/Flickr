package com.flickr.extensions

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


fun AppCompatActivity.showToastMessage(message: String, duration: Int = Toast.LENGTH_LONG) =
    Toast.makeText(this, message, duration).show()

fun showErrorMessage(context: Context, message: String, duration: Int = Toast.LENGTH_LONG) =
    Toast.makeText(context, message, duration).show()
