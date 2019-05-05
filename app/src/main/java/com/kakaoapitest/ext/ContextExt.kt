package com.kakaoapitest.ext

import android.content.Context
import android.widget.Toast


fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Context.toast(resId: Int) {
    toast(getString(resId))
}