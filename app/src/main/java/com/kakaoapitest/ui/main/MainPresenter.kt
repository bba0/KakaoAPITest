package com.kakaoapitest.ui.main

import android.util.Log
import com.kakaoapitest.network.Api

class MainPresenter(override var mVie: MainContract.View) : MainContract.Presenter {
    override fun search(query: String) {
        Api.searchBlog(query)
            .subscribe({
                Log.e("lol", it.toString())
            }, {
                Log.e("lol", it.toString())
            }, {
                Log.e("lol", "complete")
            })
    }

}