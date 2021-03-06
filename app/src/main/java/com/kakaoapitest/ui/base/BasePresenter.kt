package com.kakaoapitest.ui.base

interface BasePresenter<T> {
    var mView: T
    fun resume()
    fun pause()
}