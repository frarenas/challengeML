package com.farenas.challengeml.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
@JsonClass(generateAdapter = true)
data class Product @JvmOverloads constructor(

    @Ignore
    @Json(name = "address")
    var address: Address? = null,

    @Ignore
    @Json(name = "attributes")
    var attributes: List<Attribute>? = null,

    @Json(name = "currency_id")
    var currencyId: String,

    @PrimaryKey(autoGenerate = false)
    @Json(name = "id")
    var id: String,

    @Json(name = "price")
    var price: Double,

    @Json(name = "thumbnail")
    var thumbnail: String,

    @Json(name = "title")
    var title: String,

    var lastViewed: Date? = null
) : Parcelable