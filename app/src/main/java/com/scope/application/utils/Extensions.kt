package com.scope.application.utils

import android.content.res.Resources
import android.util.Log
import android.util.TypedValue

fun String?.getOrSafe():String{
    return this ?:""
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
