package ru.xen.productslist.domain

import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun products(@Query("skip") skip: Int, @Query("limit") limit: Int): ProductsListResponse

}