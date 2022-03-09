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
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng

fun String?.getOrSafe():String{
    return this ?:""
}


fun View.show(isVisible:Boolean){
    this.visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun Int.toPx(): Int{
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics).toInt()
}

fun Int.toDp(): Float {

    val px = this.toPx()
    val dp = px / Resources.getSystem().displayMetrics.density

    Log.d("dpFromPx_Metric", dp.toString())

    return dp
}


fun Int.toDpInt():Int{

    return this.toDp().toInt()
}


fun Context.getLocation():LatLng{

        var currentlyLatLng = LatLng(0.0,0.0)

        var locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        var locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                var latitute = location!!.latitude
                var longitute = location!!.longitude

                currentlyLatLng = LatLng(latitute,longitute)

                Log.i("geo", "Latitute: $latitute ; Longitute: $longitute")

            }


            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onProviderDisabled(provider: String) {
            }

        }

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch (ex:SecurityException) {

           }


        return currentlyLatLng
    }


 fun Activity.checkPermission(permission: String, requestCode: Int) {
    if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
        // Requesting the permission
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
    } else {
        Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show()
    }
}
