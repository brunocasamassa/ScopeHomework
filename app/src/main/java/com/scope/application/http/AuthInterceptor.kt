package com.scope.application.http


import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import java.io.IOException

class AuthInterceptor : Interceptor, KoinComponent {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        return with(chain.request().newBuilder()) {
            chain.proceed(build())
        }
    }
}
