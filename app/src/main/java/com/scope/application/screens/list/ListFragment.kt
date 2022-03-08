package com.scope.application.screens.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scope.application.screens.BaseFragment

class ListFragment: BaseFragment() {

    companion object{
        const val USER_ID_SELECTED = "_user_id_selected"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

    }
}