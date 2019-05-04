package com.kakaoapitest.model

import java.util.*

abstract class Document {
    open var url: String? = null
    open var title: String? = null
    open var name: String? = null
    open var imageUrl: String? = null
    open var label: String = "Document"
    open var date: Date? = null
}