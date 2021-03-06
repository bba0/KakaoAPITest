package com.kakaoapitest.ui.detail

import com.kakaoapitest.data.model.Document
import com.kakaoapitest.data.source.document.DocumentRepository

class DetailPresenter(override var mView: DetailContract.View, var mDocumentRepository: DocumentRepository) : DetailContract.Presenter {
    private var mDocument: Document? = null
    override fun openDocument() {
        mDocument?.url?.run {
            mDocumentRepository.openDocument(this)
        }

    }
    override fun pause() {
    }

    override fun getDocument(url: String) {
        mDocumentRepository.getDocument(url)?.run {
            mDocument = this
            mView.setDocument(this)
        } ?: mView.documentError()
    }

    override fun resume() {
    }
}