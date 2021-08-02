package com.sample.imagesearch.model

import com.google.gson.annotations.SerializedName

data class ImageDataModel (
        @SerializedName("id") val id : Int,
        @SerializedName("title") val title : String,
        @SerializedName("hex") val hex : String,
        @SerializedName("imageUrl") val imageUrl : String,
        var isLiked:Boolean
)