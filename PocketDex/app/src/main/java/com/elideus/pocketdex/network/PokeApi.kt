package com.elideus.pocketdex.network

import com.elideus.pocketdex.network.data.ItemDetailedData
import com.elideus.pocketdex.network.data.TypeDetailedData
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://pokeapi.co/api/v2/"

//Build the moshi adapter
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


//Build the retrofit service
private fun retrofit(): Retrofit {
    val okHttpClientBuilder = OkHttpClient.Builder()

    //Create http client logging
    okHttpClientBuilder.addInterceptor(
        HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BASIC
        )
    )

    //Build retrofit with moshi adapter and client logging
    return Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(okHttpClientBuilder.build())
        .build()
}


interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): GlobalSearchData

    @GET("pokemon/{pokemonName}/")
    suspend fun getPokemonByName(@Path("pokemonName") name: String): PokemonDetailedData

    @GET("item")
    suspend fun getItems(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): GlobalSearchData

    @GET("item/{itemName}/")
    suspend fun getItemByName(@Path("itemName") name: String): ItemDetailedData

    @GET("type/{typeName}/")
    suspend fun getTypeByName(@Path("typeName") name: String): TypeDetailedData
}

object PokeApi {
    val retrofitService: PokeApiService by lazy { retrofit().create(PokeApiService::class.java) }
}