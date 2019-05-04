package com.kakaoapitest.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class CafeModel(@SerializedName("url") override var url: String? = "") : Document() {
    @SerializedName("title")
    override var title: String? = null
    @SerializedName("cafename")
    override var name: String? = null
    @SerializedName("thumbnail")
    override var imageUrl: String? = null
    override var label: String = "Cafe"
    @SerializedName("datetime")
    override var date: Date? = null
}