package com.mr.elshoddev.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mr.elshoddev.newsapp.R
import com.mr.elshoddev.newsapp.databinding.FragmentArticleBinding
import com.mr.elshoddev.newsapp.ui.MainActivity
import com.mr.elshoddev.newsapp.ui.NewsViewModel


class ArticleFragment : Fragment(R.layout.fragment_article) {

    val b by lazy {
        FragmentArticleBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = (activity as MainActivity).viewModel
        val article = args.article
        val articleView = b.webView
        articleView.settings.javaScriptEnabled = true
        articleView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                Log.d("TAAG", "onReceivedError: $error")
                Log.d("TAAG", "onReceivedError: $request")
                super.onReceivedError(view, request, error)
            }
        }
        Log.d("TAAG", "onViewCreated: ${article.url}")

        articleView.loadUrl(article.url)



        return b.root
    }



}