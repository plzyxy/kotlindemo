package com.teayork.module_main.http.api

import com.teayork.module_main.bean.GankBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GankApi {

    @GET("data/{cate}/10/{page}")
    fun getGank(@Path("cate") cate: String, @Path("page") page: Int): Observable<GankBean>

//    @GET("today")
//    fun getToday(): Observable<GankToadyBean>
}