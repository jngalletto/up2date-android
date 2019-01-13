package com.mapright.android.helper

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat

fun Context.isPermissionGranted(permission: String) =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
