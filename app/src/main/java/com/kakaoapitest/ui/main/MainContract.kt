package com.kakaoapitest.ui.main

import com.kakaoapitest.data.model.Document
import com.kakaoapitest.ui.base.BasePresenter
import com.kakaoapitest.ui.base.BaseView

interface MainContract {
    interface View: BaseView {
        fun setDocument(documentList: List<Document>)
        fun addDocument(documentList: List<Document>)
    }

    interface Presenter: BasePresenter<View> {
        fun search(query: String, isMore: Boolean)
        fun changeSortType(index: Int)
        fun changeApiType(index: Int)
        fun resume()
        fun pause()
    }
}