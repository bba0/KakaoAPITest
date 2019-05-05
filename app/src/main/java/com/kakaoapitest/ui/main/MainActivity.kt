package com.kakaoapitest.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.ext.toast
import com.kakaoapitest.R
import com.data.model.Document
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {
    override fun addDocument(documentList: List<Document>) {

    }

    override fun setDocument(documentList: List<Document>) {
        mAdapter.clearData()
        mAdapter.addData(documentList)
    }

    val mPresenter by lazy {
        MainPresenter(this)
    }
    private val mLinearLayoutManager = LinearLayoutManager(this)
    private val mAdapter = DocumentAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search_button.setOnClickListener {
            mPresenter.search(search_edit_text.text.toString())
        }
        search_recycler_view.adapter = mAdapter
        search_recycler_view.layoutManager = mLinearLayoutManager
        search_button.setOnClickListener {
            search_edit_text.text.toString().run {
                if (!TextUtils.isEmpty(trim())) {
                    mPresenter.search(this)
                } else {
                    toast(R.string.insert_query)
                }
            }
        }
    }
}
