package com.kakaoapitest.model

import com.ext.getString
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
    var dateToString: String = ""
    get() {
        return date?.run {
            Calendar.getInstance().run {
                var today = time.getString()
                add(Calendar.DAY_OF_YEAR, -1)
                var yesterday = time.getString()
                var day = getString()
                return when(day) {
                    today -> {
                        "오늘"
                    }
                    yesterday -> {
                        "어제"
                    }
                    else -> {
                        day
                    }
                }
            }
        } ?: ""
    }
}