@file:Suppress("UNCHECKED_CAST")

package com.ljubinkovicd.comicdroid.network

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.ljubinkovicd.comicdroid.model.Comic
import com.ljubinkovicd.comicdroid.model.Comics
import com.ljubinkovicd.comicdroid.model.Hero
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import java.lang.reflect.Type

/**
 * Created by ljubinkovicd on 11/6/17.
 */
class MarvelApiClient {
    val BASE_URL = "https://gateway.marvel.com/v1/public/"

    val marvelApiService: MarvelApiService

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original: Request = chain.request()

            val request: Request = original.newBuilder()
                    .header("Referer", "https://developer.marvel.com/docs")
                    .method(original.method(), original.body())
                    .build()

            Log.d("REQUEST", request.toString())
            Log.d("HEADERS", request.headers().toString())

            val response = chain.proceed(request)
            Log.d("RESPONSE", response.toString())
            response
        }

        val client: OkHttpClient = httpClient.build()

        /*
        * By default, Gson is strict and only accepts JSON as specified by
        * <a href="http://www.ietf.org/rfc/rfc4627.txt">RFC 4627</a>. This option (setLenient()) makes the parser
        * liberal in what it accepts.
        * */
        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // allows Rx integration
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

        marvelApiService = retrofit.create<MarvelApiService>(MarvelApiService::class.java)
    }

    fun getMarvelHeroes(apiKey: String) : Observable<Hero> {

        return marvelApiService.getMarvelHeroes(apiKey)
            .flatMap { marvelResponse ->
                val results = marvelResponse.data?.results
                Observable.from(results)
            }
            .flatMap { heroResponse ->
                Observable.just(
                        Hero(
                                heroResponse?.thumbnail,
                                heroResponse?.urls,
                                heroResponse?.stories,
                                heroResponse?.series,
                                heroResponse?.comics,
                                heroResponse?.name,
                                heroResponse?.description,
                                heroResponse?.modified,
                                heroResponse?.id,
                                heroResponse?.resourceURI,
                                heroResponse?.events
                        )
                )
            }
    }

    fun getMarvelComics(characterId: Int, apiKey: String) : Observable<Comic> {

        return marvelApiService.getMarvelHeroFeaturedInComics(characterId, apiKey)
                .flatMap { marvelResponse ->
                    val results = marvelResponse.data?.results
                    Observable.from(results)
                }
                .flatMap { comicResponse ->
                    Observable.just(
                            Comic(
                                    comicResponse?.creators,
                                    comicResponse?.issueNumber,
                                    comicResponse?.isbn,
                                    comicResponse?.description,
                                    comicResponse?.variants,
                                    comicResponse?.title,
                                    comicResponse?.diamondCode,
                                    comicResponse?.characters,
                                    comicResponse?.urls,
                                    comicResponse?.ean,
                                    comicResponse?.collections,
                                    comicResponse?.modified,
                                    comicResponse?.id,
                                    comicResponse?.prices,
                                    comicResponse?.events,
                                    comicResponse?.collectedIssues,
                                    comicResponse?.pageCount,
                                    comicResponse?.thumbnail,
                                    comicResponse?.images,
                                    comicResponse?.stories,
                                    comicResponse?.textObjects,
                                    comicResponse?.digitalId,
                                    comicResponse?.format,
                                    comicResponse?.upc,
                                    comicResponse?.dates,
                                    comicResponse?.resourceURI,
                                    comicResponse?.variantDescription,
                                    comicResponse?.issn,
                                    comicResponse?.series
                            )
                    )
                }
    }
}