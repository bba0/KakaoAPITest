package com.kakaoapitest.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kakaoapitest.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {
    val mPresenter by lazy {
        MainPresenter(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search_button.setOnClickListener {
            mPresenter.search(search_edit_text.text.toString())
        }
    }
}
