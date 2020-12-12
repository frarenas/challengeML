package com.farenas.challengeml.data

import com.farenas.challengeml.data.model.SearchResult
import com.farenas.challengeml.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MlApiService {
    @GET("sites/{site}/search")
    suspend fun getProducts(
        @Path("site") site: String = Constants.DEFAULT_ML_SITE,
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): SearchResult
}