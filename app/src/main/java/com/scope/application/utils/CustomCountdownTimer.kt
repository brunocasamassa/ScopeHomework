package com.scope.application.utils

import android.os.CountDownTimer

class CustomCountdownTimer(millisInFuture: Long, countDownInterval: Long = 1000) :
    CountDownTimer(millisInFuture, countDownInterval) {

    var onTickCallback: (secondsToFinish: String) -> Unit = { it }
    var onFinishCallback: () -> Unit = { }

    override fun onTick(millisUntilFinished: Long) {

        onTickCallback.invoke("${millisUntilFinished/1000}")
    }

    override fun onFinish() {

        onFinishCallback.invoke()

    }


}