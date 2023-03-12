package com.valkotova.testassignment.ui.ext

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.valkotova.testassignment.R

fun View.showError(error: UIError){
    val errorString = if(error.errorId != null)
        resources.getString(error.errorId, *(error.args.toTypedArray()))
    else
        error.errorString?:""
    Snackbar
        .make(this.context, this, errorString, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(resources.getColor(R.color.colorError, this.context.theme))
        .show()
}