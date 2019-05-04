package com.kakaoapitest.model

import com.google.gson.annotations.SerializedName
import java.util.*

abstract class Document {
    @SerializedName("document_url")
    open var url: String? = null
    @SerializedName("document_title")
    open var title: String? = null
    @SerializedName("document_name")
    open var name: String? = null
    @SerializedName("document_image_url")
    open var imageUrl: String? = null
    @SerializedName("document_label")
    open var label: String = "Document"
    @SerializedName("document_date")
    open var date: Date? = null
}