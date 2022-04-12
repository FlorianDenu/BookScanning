package com.floriandenu.bookscanning.di

import com.floriandenu.bookscanning.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path

interface BookService {

    @GET("books/v1/volumes/q=isbn:{isbn}/key=")
    fun volumeByIsbn(@Path("isbn") isbn: String)
}

