package id.reza.newsapi.di

import id.reza.newsapi.viewmodel.ListNewsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listNewsModule = module {
    viewModel { ListNewsViewModel(get(), get()) }
}