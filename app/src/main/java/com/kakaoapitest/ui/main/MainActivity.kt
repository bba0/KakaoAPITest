package com.kakaoapitest.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import com.kakaoapitest.ext.toast
import com.kakaoapitest.R
import com.kakaoapitest.data.model.Document
import com.kakaoapitest.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {
    companion object {
        const val EXTRA_DOCUMENT_URL = "documentUrl"
    }
    var reloadSize = 10
    override fun addDocument(documentList: List<Document>) {
        mAdapter.addData(documentList)
    }

    override fun setDocument(documentList: List<Document>) {
        mAdapter.clearData()
        mAdapter.addData(documentList)
    }

    private val mPresenter by lazy {
        MainPresenter(this)
    }
    private val mLinearLayoutManager = LinearLayoutManager(this)
    private val mAdapter = DocumentAdapter {
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra(EXTRA_DOCUMENT_URL, it.url)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search_recycler_view.adapter = mAdapter
        search_recycler_view.layoutManager = mLinearLayoutManager
        search_button.setOnClickListener {
            search_edit_text.text.toString().run {
                if (!TextUtils.isEmpty(trim())) {
                    mPresenter.search(this, false)
                } else {
                    toast(R.string.insert_query)
                }
            }
        }

        search_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (mLinearLayoutManager.findLastCompletelyVisibleItemPosition() == mAdapter.itemCount - reloadSize) {
                    mPresenter.moreLoad()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mPresenter.resume()
    }

    override fun onPause() {
        super.onPause()
        mPresenter.pause()
    }
}
