package com.valkotova.testassignment.ui.ext

import androidx.annotation.StringRes

class UIError(
    @StringRes val errorId : Int? = null,
    val errorString : String? = null,
    val args : List<Any?> = listOf())
