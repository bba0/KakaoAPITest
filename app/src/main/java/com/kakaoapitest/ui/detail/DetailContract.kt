package com.kakaoapitest.ui.detail

import com.kakaoapitest.data.model.Document
import com.kakaoapitest.ui.base.BasePresenter
import com.kakaoapitest.ui.base.BaseView

interface DetailContract {
    interface View: BaseView {
        fun setDocument(document: Document)
    }

    interface Presenter: BasePresenter<View> {
        fun getDocument(url: String)
        fun resume()
    }
}