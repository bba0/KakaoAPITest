package com.kakaoapitest.data.model

data class SearchQuery(var query: String) {
    var time: Long? = 0
    init {
        time = System.currentTimeMillis()
    }
}