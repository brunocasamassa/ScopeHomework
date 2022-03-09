package com.scope.application.screens.list.adapter

import com.scope.application.domain.models.Driver

interface OnItemClickListener {

    fun onDriverCardClicked(driver: Driver)

}