package com.danusuhendra.suitmediatest.model.response


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserResponse(
    @SerializedName("data")
    val `data`: List<Data?>? = null,
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("per_page")
    val perPage: Int? = null,
    @SerializedName("support")
    val support: Support? = null,
    @SerializedName("total")
    val total: Int? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null
)

@Parcelize
@Entity(tableName = "guest_table")
data class Data(
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("first_name")
    val firstName: String? = null,
    @PrimaryKey
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("last_name")
    val lastName: String? = null
) : Parcelable

data class Support(
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("url")
    val url: String? = null
)