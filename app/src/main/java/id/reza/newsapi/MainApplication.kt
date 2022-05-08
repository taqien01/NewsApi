package id.reza.newsapi

import android.content.Context
import androidx.multidex.MultiDexApplication
import id.reza.newsapi.di.appRepoModule
import id.reza.newsapi.di.listNewsModule
import id.reza.newsapi.di.mainModule
import id.reza.newsapi.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        startKoin {

            androidContext(this@MainApplication)

            modules(
                networkModule,
                mainModule,
                listNewsModule,
                appRepoModule
            )
        }

    }

    companion object {
        private lateinit var context: Context

        fun getContext(): Context {
            return context
        }
    }
}