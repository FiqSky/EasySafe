package com.fiqsky.easysafe.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.fiqsky.easysafe.R
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        wv_article.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url.toString())
                Log.d("URL", "shouldOverrideUrlLoading: $url")
                return true
            }
        }

        wv_article.loadUrl("https://www.halodoc.com/artikel")
        val webSettings: WebSettings = wv_article.settings
        webSettings.javaScriptEnabled = true
    }

    override fun onPause() {
        super.onPause()
        // Clear all the Application Cache, Web SQL Database and the HTML5 Web Storage
        WebStorage.getInstance().deleteAllData()

        // Clear all the cookies
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()

        wv_article.clearCache(true)
        wv_article.clearFormData()
        wv_article.clearHistory()
        wv_article.clearSslPreferences()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}