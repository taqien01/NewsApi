package id.reza.newsapi.model

import com.google.gson.annotations.SerializedName

class NewsSources {
    @SerializedName("status")
    val status : String = ""

    @SerializedName("code")
    val code : String = ""

    @SerializedName("message")
    val message : String = ""

    @SerializedName("sources")
    val list : List<Sources>? = null
}

class Sources{
    @SerializedName("id")
    val id : String = ""

    @SerializedName("name")
    val name : String = ""

    @SerializedName("description")
    val description : String = ""

    @SerializedName("url")
    val url : String = ""

    @SerializedName("category")
    val category : String = ""

    @SerializedName("language")
    val language : String = ""

    @SerializedName("country")
    val country : String = ""
}