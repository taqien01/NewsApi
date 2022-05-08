package id.reza.newsapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.reza.newsapi.BuildConfig
import id.reza.newsapi.model.AllArticles
import id.reza.newsapi.model.NewsSources
import id.reza.newsapi.service.AppRepository
import id.reza.newsapi.service.NetworkConnectionInterceptor
import id.reza.newsapi.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.HttpURLConnection

class ListNewsViewModel(private val appRepository: AppRepository, private val networkConnectionInterceptor: NetworkConnectionInterceptor) : ViewModel() {
    val errorEvent = SingleLiveEvent<String>()
    val loadingEvent = SingleLiveEvent<Boolean>()
    val list = SingleLiveEvent<AllArticles>()
    val pageEvent = SingleLiveEvent<Int>()

    fun getListNewsCategory(page: Int, category: String){
        loadingEvent.value = true

        var response: Response<AllArticles>

        if (!networkConnectionInterceptor.isInternetAvailable()){
            errorEvent.value = "No Internet Access"
            loadingEvent.value = false
        }else {
            viewModelScope.launch {
                with(Dispatchers.IO) {
                    response = appRepository.getEverythingByCategory(BuildConfig.APIKEY, page, 10, category)
                }

                with(Dispatchers.Main){
                    try{
                        when(response.code()) {
                            HttpURLConnection.HTTP_OK -> {
                                list.value = response.body()
                            }
                            else -> {
                                errorEvent.value = response.message()
                            }
                        }
                    }catch (ex : Exception){
                        errorEvent.value = ex.localizedMessage
                    }finally {
                        loadingEvent.value = false

                    }

                }
            }

        }

    }

    fun getListNewsSources(page: Int, sources: String){

        loadingEvent.value = true

        var response: Response<AllArticles>

        if (!networkConnectionInterceptor.isInternetAvailable()){
            errorEvent.value = "No Internet Access"
            loadingEvent.value = false
        }else {
            viewModelScope.launch {
                with(Dispatchers.IO) {
                    response = appRepository.getEverythingBySources(BuildConfig.APIKEY, page, 10, sources)
                }

                with(Dispatchers.Main){
                    try{
                        when(response.code()) {
                            HttpURLConnection.HTTP_OK -> {
                                list.value = response.body()
                            }
                            else -> {
                                errorEvent.value = response.message()
                            }
                        }
                    }catch (ex : Exception){
                        errorEvent.value = ex.localizedMessage
                    }finally {
                        loadingEvent.value = false

                    }

                }
            }

        }

    }

    fun getListNewsSearch(page: Int, sources: String){

        loadingEvent.value = true

        var response: Response<AllArticles>

        if (!networkConnectionInterceptor.isInternetAvailable()){
            errorEvent.value = "No Internet Access"
            loadingEvent.value = false
        }else {
            viewModelScope.launch {
                with(Dispatchers.IO) {
                    response = appRepository.searchNews(BuildConfig.APIKEY, page, 10, sources)
                }

                with(Dispatchers.Main){
                    try{
                        when(response.code()) {
                            HttpURLConnection.HTTP_OK -> {
                                list.value = response.body()
                            }
                            else -> {
                                errorEvent.value = response.message()
                            }
                        }
                    }catch (ex : Exception){
                        errorEvent.value = ex.localizedMessage
                    }finally {
                        loadingEvent.value = false

                    }

                }
            }

        }

    }
}