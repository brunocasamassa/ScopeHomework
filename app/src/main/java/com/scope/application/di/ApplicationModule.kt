package com.scope.application.di

import com.scope.application.remote.ApplicationApi
import com.scope.application.remote.ApplicationRepository
import com.scope.application.remote.ApplicationUseCase
import com.scope.application.remote.ApplicationUseCaseImpl
import com.scope.application.http.CustomHttpClient
import com.scope.application.screens.ApplicationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object ApplicationModule {
    private val modules = module {


        factory { CustomHttpClient() }
        factory { get<CustomHttpClient>().create(ApplicationApi::class.java) }
        factory { ApplicationRepository(get()) }
        factory<ApplicationUseCase> { ApplicationUseCaseImpl(get()) }
        viewModel { ApplicationViewModel(get(),get()) }
    }


    fun loadModules() {
        loadKoinModules(modules)
    }
}