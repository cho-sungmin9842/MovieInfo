package com.example.movieinfo

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository {
    companion object {
        private const val BASE_URL = "http://api.koreafilm.or.kr/"
    }

    // gson 객체 생성 및 설정
    var gson = GsonBuilder().setLenient().create()

    // retrofit 객체 생성 및 설정
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}