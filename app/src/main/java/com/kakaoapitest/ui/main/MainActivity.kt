package com.kakaoapitest.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.kakaoapitest.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {
    val mPresenter by lazy {
        MainPresenter(this)
    }
    var a = ArrayList<String>();
    val mLinearLayoutManager = LinearLayoutManager(this)
    val mAdapter = DocumentAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search_button.setOnClickListener {
            mPresenter.search(search_edit_text.text.toString())
        }
        for (i in 1..100) {
            a.add("abcdeee$i")
        }
        search_recycler_view.adapter = mAdapter
        search_recycler_view.layoutManager = mLinearLayoutManager
    }


}
