package com.valkotova.testassignment.ui.views.lists

import android.view.ViewGroup

interface Item {
    fun areItemsTheSame(other : Item) : Boolean
    fun areContentsTheSame(other : Item) : Boolean
}