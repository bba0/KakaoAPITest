package com.kakaoapitest.model

import com.google.gson.annotations.SerializedName

class SearchApiModel {
    @SerializedName("documents")
    var documents: ArrayList<BlogModel>? = null
    @SerializedName("pageable_count")
    var pageableCount: Int? = 0
}