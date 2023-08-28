package com.example.movieinfo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInfoService {
    // 기본적인 랜덤한 영화리스트 가져오기
    @GET("openapi-data2/wisenut/search_api/search_json2.jsp")
    fun getMovieList(
        @Query("collection") collection: String,
        @Query("ServiceKey") serviceKey: String,
    ): Call<MovieEntity>

    // 카테고리로 검색하여 영화리스트 가져오기
    @GET("openapi-data2/wisenut/search_api/search_json2.jsp")
    fun getMovieListForCategory(
        @Query("collection") collection: String,
        @Query("ServiceKey") serviceKey: String,
        @Query("actor") actor: String,
        @Query("title") title: String,
        @Query("director") director: String
    ): Call<MovieEntity>
}