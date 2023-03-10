package com.valkotova.testassignment.ui.ext

import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import com.valkotova.testassignment.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

@ExperimentalCoroutinesApi
fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = doOnTextChanged { text, _, _, _ ->
            trySend(text)
        }
        awaitClose {
            removeTextChangedListener(listener)
        }
    }.onStart { emit(text) }
}

fun EditText.updateState(isError : Boolean){
    if(isError)
        this.background = ResourcesCompat.getDrawable(resources, R.drawable.background_edit_text_error, this.context.theme)
    else
        this.background = ResourcesCompat.getDrawable(resources, R.drawable.background_edit_text, this.context.theme)

}