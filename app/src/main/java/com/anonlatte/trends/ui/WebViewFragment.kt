package com.anonlatte.trends.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.anonlatte.trends.R
import kotlinx.android.synthetic.main.fragment_webview.view.*

class WebViewFragment : Fragment() {
    private val link: String? by lazy {
        arguments?.getString("link", "https://github.com/")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_webview, container, false)
        view.webView.webViewClient = CustomWebViewClient(view.webViewProgressBar)
        view.webView.loadUrl(link)
        return view
    }

    class CustomWebViewClient(private var progressBar: ProgressBar) : WebViewClient() {
        init {
            progressBar.visibility = View.VISIBLE
        }

        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }
    }
}