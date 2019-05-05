package com.kakaoapitest.ui.detail

import com.kakaoapitest.data.source.DocumentRepository

class DetailPresenter(override var mView: DetailContract.View) : DetailContract.Presenter {
    private var mDocumentRepository = DocumentRepository
    override fun pause() {
    }

    override fun getDocument(url: String) {
        mDocumentRepository.getDocument(url)?.run {
            mView.setDocument(this)
        } ?: mView.documentError()
    }

    override fun resume() {
    }
}