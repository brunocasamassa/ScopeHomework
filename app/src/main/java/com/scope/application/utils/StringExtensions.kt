package com.scope.application.utils

fun String?.getOrSafe():String{
    return this ?:""
}