package br.com.android.consultainstituicaofinanceira.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Bank(
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("favorite")
    val favorite: Boolean,
    @SerializedName("image")
    val image: String?,
    @SerializedName("imageName")
    val imageName: String?
): Serializable