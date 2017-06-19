package com.pawel.database_php

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class DataBody {

    @SerializedName("products")
    @Expose
    private var product: ArrayList<Product>?= null

    fun getProduct(): ArrayList<Product>? {
        return product
    }




    class Product {

        @SerializedName("pid")
        @Expose
        var pid: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("price")
        @Expose
        var price: String? = null

        @SerializedName("description")
        @Expose
        var description: String? = null

        @SerializedName("tekst")
        @Expose
        var textOfSong: String? = null


    }




}

