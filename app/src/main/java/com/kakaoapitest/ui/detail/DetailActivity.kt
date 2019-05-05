package com.kakaoapitest.ui.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kakaoapitest.R
import com.kakaoapitest.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        intent.extras?.getString(MainActivity.EXTRA_DOCUMENT_URL)?.run {

        } ?: finish()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


}