package com.scope.application.domain.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ListDrivers(
    @SerializedName("data")
    val drivers: List<Driver>
)

@Serializable
data class Driver(
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("userid")
    val userid: Int,
    @SerializedName("vehicles")
    val vehicles: List<Vehicle>
)

@Serializable
data class Owner(
    @SerializedName("foto")
    val foto: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("surname")
    val surname: String
)

@Serializable
data class Vehicle(
    @SerializedName("color")
    val color: String,
    @SerializedName("foto")
    val foto: String,
    @SerializedName("make")
    val make: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("vehicleid")
    val vehicleid: Int,
    @SerializedName("vin")
    val vin: String,
    @SerializedName("year")
    val year: String
)