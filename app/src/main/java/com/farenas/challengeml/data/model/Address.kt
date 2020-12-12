package com.farenas.challengeml.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Entity(
    foreignKeys = [
        ForeignKey(entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE)
    ]
)
@Parcelize
@JsonClass(generateAdapter = true)
data class Address(

    @Json(name = "city_name")
    var cityName: String,

    @Json(name = "state_name")
    var stateName: String,

    @PrimaryKey(autoGenerate = false)
    var productId: String = ""
) : Parcelable