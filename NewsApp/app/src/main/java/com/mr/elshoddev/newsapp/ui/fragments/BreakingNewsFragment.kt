package com.mr.elshoddev.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mr.elshoddev.newsapp.R
import com.mr.elshoddev.newsapp.adapters.NewsAdapter
import com.mr.elshoddev.newsapp.databinding.FragmentBreakingNewsBinding
import com.mr.elshoddev.newsapp.decoration.NewsItemDecoration
import com.mr.elshoddev.newsapp.ui.MainActivity
import com.mr.elshoddev.newsapp.ui.NewsViewModel
import com.mr.elshoddev.newsapp.util.Resource

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    val b by lazy {
        FragmentBreakingNewsBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        newsAdapter.setOnItemClickListener {
            it?.let {
                Log.d("TAAG", "onCreateView: Kirdi")
                val bundle = Bundle().apply {
                    putSerializable("article", it)
                }
                findNavController().navigate(
                    R.id.action_breakingNewsFragment_to_articleFragment,
                    bundle
                )

            }
        }
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {
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
        b.rvBreakingNews.apply {
            addItemDecoration(NewsItemDecoration())
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }


}