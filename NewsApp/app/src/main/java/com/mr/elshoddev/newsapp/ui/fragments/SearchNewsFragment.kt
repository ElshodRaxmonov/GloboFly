package com.mr.elshoddev.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mr.elshoddev.newsapp.R
import com.mr.elshoddev.newsapp.adapters.NewsAdapter
import com.mr.elshoddev.newsapp.databinding.FragmentSearchNewsBinding
import com.mr.elshoddev.newsapp.decoration.NewsItemDecoration
import com.mr.elshoddev.newsapp.ui.MainActivity
import com.mr.elshoddev.newsapp.ui.NewsViewModel
import com.mr.elshoddev.newsapp.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    val b by lazy {
        FragmentSearchNewsBinding.inflate(layoutInflater)
    }
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        var job: Job? = null
        b.etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                it?.let {
                    if (it.toString().isNotEmpty()) {

                        viewModel.searchNews(it.toString())

                    }
                }
            }
        }
        newsAdapter.setOnItemClickListener {
            it?.let {
                val bundle = Bundle().apply {
                    putSerializable("article", it)
                }
                findNavController().navigate(
                    R.id.action_searchNewsFragment_to_articleFragment,
                    bundle
                )
            }
        }

        viewModel.searchedNews.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let { newsResponse ->

                        newsAdapter.differ.submitList(newsResponse.articles)

                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    it.message?.let { message ->
                        Log.d("TAG", "onCreate: $message")
                    }
                }

                is Resource.Loading -> {
                    Log.d("TAAG", "onCreateView: loading")
                    showProgressBar()
                }
            }
        })
        return b.root
    }
    private fun hideProgressBar() {
        b.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        b.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {


        newsAdapter = NewsAdapter()
        b.rvSearchNews.apply {
            addItemDecoration(NewsItemDecoration())
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}



