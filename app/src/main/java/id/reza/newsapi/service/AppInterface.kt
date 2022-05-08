package id.reza.newsapi.service

import id.reza.newsapi.model.AllArticles
import id.reza.newsapi.model.NewsSources
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppInterface {

    //get list all news
    @GET("top-headlines")
//    top-headlines?apiKey=0eadfe7949b64d3c841111c4869b9f4f&category=sports

//    The category you want to get headlines for. Possible options:
//    business entertainment general health science sports technology.
//    Note: you can't mix this param with the sources param.
    suspend fun getAllNews(@Query("apiKey") apiKey: String,
                                        @Query("page") page: Int,
                                        @Query("pageSize") pageSize: Int,
                                        @Query("country") country: String): Response<AllArticles>

    //search news
    @GET("everything")
    suspend fun searchNews(@Query("apiKey") apiKey: String,
                                       @Query("page") page: Int,
                                       @Query("pageSize") pageSize: Int,
                                       @Query("q") q: String): Response<AllArticles>

    //get list news by sources
    @GET("everything")
    suspend fun getEverythingBySources(@Query("apiKey") apiKey: String,
                                       @Query("page") page: Int,
                                       @Query("pageSize") pageSize: Int,
                                       @Query("sources") sources: String): Response<AllArticles>

    //get list news by category
    @GET("top-headlines")
//    top-headlines?apiKey=0eadfe7949b64d3c841111c4869b9f4f&category=sports

//    The category you want to get headlines for. Possible options:
//    business entertainment general health science sports technology.
//    Note: you can't mix this param with the sources param.
    suspend fun getEverythingByCategory(@Query("apiKey") apiKey: String,
                                        @Query("page") page: Int,
                                        @Query("pageSize") pageSize: Int,
                                        @Query("category") category: String): Response<AllArticles>

    //get list sources
    @GET("top-headlines/sources")
    suspend fun getSources(@Query("apiKey") apiKey: String): Response<NewsSources>

}