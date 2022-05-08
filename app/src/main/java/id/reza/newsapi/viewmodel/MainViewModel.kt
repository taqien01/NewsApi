package id.reza.newsapi.viewmodel

import android.util.Log
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

class MainViewModel(private val appRepository : AppRepository, private val networkConnectionInterceptor: NetworkConnectionInterceptor) : ViewModel() {

    val errorEvent = SingleLiveEvent<String>()
    val loadingEvent = SingleLiveEvent<Boolean>()
    val list = SingleLiveEvent<AllArticles>()
    val listSources = SingleLiveEvent<NewsSources>()
    val pageEvent = SingleLiveEvent<Int>()

    fun getSource(){
        loadingEvent.value = true

        var response: Response<NewsSources>

        if (!networkConnectionInterceptor.isInternetAvailable()){
            errorEvent.value = "No Internet Access"
            loadingEvent.value = false
        }else{
            viewModelScope.launch {
                with(Dispatchers.IO){
                    response = appRepository.getSources(BuildConfig.APIKEY)
                }

                with(Dispatchers.Main){
                    try{
                        when(response.code()) {
                            HttpURLConnection.HTTP_OK -> {
                                listSources.value = response.body()
                                Log.e("MV View", ""+ response.body()!!.list!![0].id)
                            }
                            else -> {
                                errorEvent.value = response.message()
                            }
                        }
                    }catch (ex : Exception){
                        errorEvent.value = ex.localizedMessage
                        ex.printStackTrace()
                    }finally {
                        loadingEvent.value = false

                    }

                }
            }
        }
    }

    fun getListNews(page: Int){
        loadingEvent.value = true

        var response: Response<AllArticles>

        viewModelScope.launch {
            with(Dispatchers.IO) {
                response = appRepository.getAllNews(BuildConfig.APIKEY, page, 6, "id")
            }

            with(Dispatchers.Main){
                try{
                    Log.e("Vm Main", "Data : "+ response.body()!!.list!![0].title)
                    when(response.code()) {
                        HttpURLConnection.HTTP_OK -> {
                            list.value = response.body()
                        }
                        else -> {
                            errorEvent.value = response.message()
                        }
                    }
                }catch (ex : Exception){
                    Log.e("Vm Main", "Data : "+ ex.localizedMessage)
                    ex.printStackTrace()

                    errorEvent.value = ex.localizedMessage
                }finally {
                    loadingEvent.value = false

                }

            }
        }



    }

}