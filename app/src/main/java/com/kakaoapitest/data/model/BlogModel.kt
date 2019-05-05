package com.kakaoapitest.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class BlogModel(@SerializedName("url") override var url: String? = "") : Document() {
    @SerializedName("title")
    override var title: String? = null
    @SerializedName("blogname")
    override var name: String? = null
    @SerializedName("thumbnail")
    override var imageUrl: String? = null
    override var label: String = "Blog"
    @SerializedName("datetime")
    override var date: Date? = null
    @SerializedName("contents")
    override var content: String? = null
}