package ca.cmpt362.projects.weCareApp

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class FindClinicActivity: AppCompatActivity() {
    private lateinit var webView: WebView
    private val URL = "https://www.google.ca/maps"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_clinic)
        webView = findViewById(R.id.webpage)

        webView.apply {
            loadUrl(URL)
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true

        }
        val intent = intent
        val data: Uri? = intent.data
        if (data != null) {
            val url = intent.dataString
            webView.loadUrl(URL)
        } else{
            webView.loadUrl("https://www.google.ca/maps")
        }
    }
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        }else {
            super.onBackPressed()
        }
    }
}