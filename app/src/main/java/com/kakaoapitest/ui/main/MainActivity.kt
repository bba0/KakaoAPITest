package com.kakaoapitest.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.ArrayAdapter
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
    var mAutoCompleteAdapter: ArrayAdapter<String>? = null
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
            search_auto_complete_text_view.text.toString().run {
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
        search_auto_complete_text_view.run {
            mAutoCompleteAdapter =  ArrayAdapter(context, android.R.layout.simple_list_item_1, ArrayList<String>())
            setAdapter(mAutoCompleteAdapter)
            threshold = 1
            setOnTouchListener { _, _ ->
                search_auto_complete_text_view.showDropDown()
                return@setOnTouchListener false
            }
            setOnItemClickListener { parent, _, position, _ ->
                mPresenter.search(parent.getItemAtPosition(position) as String, false)
            }
        }
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
