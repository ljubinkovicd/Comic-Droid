package com.ljubinkovicd.comicdroid.network

import com.ljubinkovicd.comicdroid.model.Hero
import com.ljubinkovicd.comicdroid.model.MarvelResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * Created by ljubinkovicd on 11/6/17.
 */
interface MarvelApiService {

    @GET("characters")
    fun getMarvelHeroes(@Query("apikey") apiKey: String): Observable<MarvelResponse>

    @GET("characters/{characterId}/comics")
    fun getMarvelHeroFeaturedInComics(@Path("characterId") characterId : Int, @Query("apikey") apiKey: String) : Observable<Hero>
}