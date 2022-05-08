package id.reza.newsapi.model

import com.google.gson.annotations.SerializedName

class AllArticles {

    @SerializedName("status")
    val status : String = ""

    @SerializedName("message")
    val message : String = ""

    @SerializedName("articles")
    val list : List<Articles>? = null

    @SerializedName("totalResults")
    val totalResults : Int = 0
}

class Articles{
    @SerializedName("source")
    val source : SourcesArticle? = null

    @SerializedName("author")
    val author : String = ""

    @SerializedName("title")
    val title : String = ""

    @SerializedName("description")
    val description : String = ""

    @SerializedName("url")
    val url : String = ""

    @SerializedName("urlToImage")
    val urlToImage : String = ""

    @SerializedName("publishedAt")
    val publishedAt : String = ""

    @SerializedName("content")
    val content : String = ""
}

class SourcesArticle{
    @SerializedName("id")
    val id : String = ""

    @SerializedName("name")
    val name : String = ""
}