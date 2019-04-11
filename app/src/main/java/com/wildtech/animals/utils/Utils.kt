package com.wildtech.animals.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class Utils {

    companion object {

        fun showSnack(v: View, text: String) {
            Snackbar.make(v, text, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

         fun showToast(context:Context, text: String) {
            Toast.makeText(context,
                text, Toast.LENGTH_SHORT).show()
        }
    }



}
