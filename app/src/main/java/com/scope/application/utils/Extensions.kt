package com.scope.application.utils

import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng

fun String?.getOrSafe(): String {
    return this ?: ""
}


fun View.show(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Int.toPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
}

fun Int.toDp(): Float {

    val px = this.toPx()
    val dp = px / Resources.getSystem().displayMetrics.density

    Log.d("dpFromPx_Metric", dp.toString())

    return dp
}


fun Int.toDpInt(): Int {

    return this.toDp().toInt()
}



@RequiresApi(Build.VERSION_CODES.M)
tailrec fun Activity.invokePermissioned(
    permission: String,
    requestCode: Int,
    callGranted: () -> Unit = { }
):Boolean {

    return if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
        callGranted.invoke()
        true

    } else {
        requestPermissions(arrayOf(permission), requestCode)
        return Handler().postDelayed({invokePermissioned(permission, requestCode, callGranted)}, 500)

    }
}
