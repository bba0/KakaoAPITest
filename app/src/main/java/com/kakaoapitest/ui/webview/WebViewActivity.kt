package com.kakaoapitest.ui.webview

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kakaoapitest.R
import com.kakaoapitest.ui.detail.DetailActivity
import com.kakaoapitest.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        setWebView()
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        intent.extras?.run {
            getString(MainActivity.EXTRA_DOCUMENT_URL)?.run {
                loadUrl(this)
            } ?: finish()
            getString(DetailActivity.EXTRA_TITLE)?.run {
                supportActionBar?.setDisplayShowTitleEnabled(true)
                supportActionBar?.title = this
            }
        }
    }

    private fun setWebView() {
        webview.run {
            settings.run {
                javaScriptEnabled = true
            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    loadUrl(url)
                    return true
                }
            }
        }

    }

    private fun loadUrl(url: String) {
        webview.loadUrl(url)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
        } else {
            super.onBackPressed()
        }
    }
}