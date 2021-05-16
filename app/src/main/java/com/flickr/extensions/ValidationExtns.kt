package com.flickr.extensions

fun isNullOrEmpty(value: String?): Boolean =
    value == null || value.isEmpty() || value.equals("null", ignoreCase = true)

