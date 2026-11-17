package com.example.quizapp.utils

import android.text.Html
import android.os.Build

fun decodeHtml(value: String): String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY).toString() else @Suppress("DEPRECATION") Html.fromHtml(value).toString()
