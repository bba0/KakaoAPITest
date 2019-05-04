package com.kakaoapitest.model

import com.google.gson.annotations.SerializedName

class SearchApiModel<T> {
    @SerializedName("documents")
    var documents: ArrayList<T>? = null
    @SerializedName("pageable_count")
    var pageableCount: Int? = 0
}