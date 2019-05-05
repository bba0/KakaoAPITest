package com.kakaoapitest.ui.detail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kakaoapitest.R
import com.kakaoapitest.data.model.Document
import com.kakaoapitest.ext.toast
import com.kakaoapitest.ui.main.MainActivity
import com.kakaoapitest.ui.webview.WebViewActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailContract.View {
    companion object {
        const val EXTRA_TITLE = "title"
    }

    override fun documentError() {
        toast(R.string.empty_document)
        finish()
    }

    override fun setDocument(document: Document) {
        supportActionBar?.setDisplayShowTitleEnabled(true)
        document.run {
            supportActionBar?.title = label
            thumbnail_simple_drawee_view.setImageURI(imageUrl)
            name_text_view.text = name
            title_text_view.text = title
            content_text_view.text = content
            date_text_view.text = dateToDetail
            link_text_view.text = url
            link_button.setOnClickListener {
                mPresenter.openDocument()
                startActivity(Intent(this@DetailActivity, WebViewActivity::class.java).apply {
                    putExtra(MainActivity.EXTRA_DOCUMENT_URL, url)
                    putExtra(EXTRA_TITLE, title)
                })
            }
        }

    }

    private val mPresenter by lazy {
        DetailPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        intent.extras?.getString(MainActivity.EXTRA_DOCUMENT_URL)?.run {
            mPresenter.getDocument(this)
        } ?: finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}