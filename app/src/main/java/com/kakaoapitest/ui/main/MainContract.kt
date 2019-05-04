package com.kakaoapitest.ui.main

import com.kakaoapitest.ui.base.BasePresenter
import com.kakaoapitest.ui.base.BaseView

interface MainContract {
    interface View: BaseView {

    }

    interface Presenter: BasePresenter<View> {

    }
}