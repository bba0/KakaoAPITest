package com.kakaoapitest.ui.main

import com.kakaoapitest.model.Document
import com.kakaoapitest.ui.base.BasePresenter
import com.kakaoapitest.ui.base.BaseView

interface MainContract {
    interface View: BaseView {
        fun setDocument(documentList: List<Document>)
    }

    interface Presenter: BasePresenter<View> {
        fun search(query: String)
    }
}