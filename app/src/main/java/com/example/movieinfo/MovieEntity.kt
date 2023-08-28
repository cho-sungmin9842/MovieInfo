package com.example.movieinfo

import com.google.gson.annotations.SerializedName

data class MovieEntity(
    @SerializedName("Data")
    val data: ArrayList<Data>,
)

data class Data(
    @SerializedName("Result")
    val result: List<Result>
)

data class Result(
    @SerializedName("title")
    val title: String,
    @SerializedName("directors")
    val directors: Director,
    @SerializedName("prodYear")
    val prodYear: String,
    @SerializedName("actors")
    val actors: Actor,
    @SerializedName("nation")
    val nation: String,
    @SerializedName("plots")
    val plots: Plot,
    @SerializedName("runtime")
    val runtime: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("kmdbUrl")
    val kmdbUrl: String,
    @SerializedName("posters")
    val posters: String,
    @SerializedName("stlls")
    val stlls: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("repRlsDate")
    val repRlsDate: String,
    @SerializedName("keywords")
    val keywords: String
)

data class Director(
    @SerializedName("director")
    val director: List<DirectorEntity>
)

data class DirectorEntity(
    @SerializedName("directorNm")
    val directorNm: String
)

data class Actor(
    @SerializedName("actor")
    val actor: List<ActorEntity>
)

data class ActorEntity(
    @SerializedName("actorNm")
    val actorNm: String
)

data class Plot(
    @SerializedName("plot")
    val plot: ArrayList<PlotContent>
)

data class PlotContent(
    @SerializedName("plotText")
    val plotText: String
)