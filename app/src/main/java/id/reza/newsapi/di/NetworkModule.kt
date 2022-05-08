package id.reza.newsapi.di

import id.reza.newsapi.service.AppService
import id.reza.newsapi.service.NetworkConnectionInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    single { NetworkConnectionInterceptor(get()) }

    single(named(appService)) { AppService.getServices(get())  }
}

const val appService = "APP_SERVICE"