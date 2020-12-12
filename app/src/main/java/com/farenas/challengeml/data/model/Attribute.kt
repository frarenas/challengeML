package com.farenas.challengeml.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Entity(
    primaryKeys = [
        "productId",
        "name"
    ],
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Parcelize
@JsonClass(generateAdapter = true)
data class Attribute(

    @Json(name = "name")
    var name: String,

    @Json(name = "value_name")
    var value: String?,

    var productId: String = ""
) : Parcelable