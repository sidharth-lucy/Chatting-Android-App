package com.example.letschat.utils

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService

fun isValidEmail(email:String):Boolean{
    val pattern = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    return pattern.matches(email)
}

fun isValidName(name:String):Boolean{
    return if(name.isNullOrBlank()){
        false
    }else{
        true
    }
}

fun isValidPassword(password:String):Boolean{
    if(password.length<6){
        return false
    }
    return true
}

fun hideSoftKeyboard(view: View, context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}