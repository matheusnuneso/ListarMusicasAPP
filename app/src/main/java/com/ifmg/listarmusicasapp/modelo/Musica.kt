package com.ifmg.listarmusicasapp.modelo

import com.google.gson.annotations.SerializedName

data class Musica (

    @SerializedName("trackName") val trackName: String,
    @SerializedName("collectionName") val collectionName: String,
    @SerializedName("releaseDate") val releaseDate: String


)