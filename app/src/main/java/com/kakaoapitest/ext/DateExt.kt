package com.kakaoapitest.ext

import java.text.SimpleDateFormat
import java.util.*

fun Date.getString(format: String = "yyyy-MM-dd") : String = SimpleDateFormat(format).format(this)