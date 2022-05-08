package id.reza.newsapi.service

import id.reza.newsapi.model.AllArticles
import id.reza.newsapi.model.NewsSources
import retrofit2.Response

class AppRepository(private val appInterface: AppInterface) {

    suspend fun getAllNews(apiKey: String,
                           page: Int,
                           pageSize: Int,
                           country: String) : Response<AllArticles> =
        appInterface.getAllNews(apiKey, page, pageSize, country)

    suspend fun searchNews(apiKey: String,
                           page: Int,
                           pageSize: Int,
                           q: String) : Response<AllArticles> =
        appInterface.searchNews(apiKey, page, pageSize, q)

    suspend fun getEverythingBySources(apiKey: String,
                           page: Int,
                           pageSize: Int,
                           sources: String) : Response<AllArticles> =
        appInterface.getEverythingBySources(apiKey, page, pageSize, sources)

    suspend fun getEverythingByCategory(apiKey: String,
                           page: Int,
                           pageSize: Int,
                           category: String) : Response<AllArticles> =
        appInterface.getEverythingByCategory(apiKey, page, pageSize, category)

    suspend fun getSources(apiKey: String) : Response<NewsSources> =
        appInterface.getSources(apiKey)

}