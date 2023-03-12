package com.valkotova.presenter.views.lists

interface Item {
    fun areItemsTheSame(other : Item) : Boolean
    fun areContentsTheSame(other : Item) : Boolean
}