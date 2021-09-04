package com.mahmoudjoe3.newsapp.ui.news_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mahmoudjoe3.newsapp.R
import com.mahmoudjoe3.newsapp.databinding.FragmentNewsListBinding
import com.mahmoudjoe3.newsapp.pojo.Article
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment(R.layout.fragment_news_list), NewsAdapter.OnItemClickListener {
    private val viewModel by viewModels<NewsListViewModel>()
    private var binding: FragmentNewsListBinding? = null
    private val adapter: NewsAdapter = NewsAdapter(this)
    private var mainArticleList: ArrayList<Article> = ArrayList()
    private var tempArticleList: ArrayList<Article> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsListBinding.bind(view)
        binding!!.progressBar.visibility = View.VISIBLE

        binding!!.apply {
            mainRecycle.setHasFixedSize(true)
            mainRecycle.adapter = adapter
            retryBtn.setOnClickListener {
                binding!!.progressBar.visibility = View.VISIBLE
                viewModel.refreshArticles()
                observeData()
            }
        }

        observeData()
        setHasOptionsMenu(true)
    }

    //item click listener  navigate to details fragment
    override fun onItemClick(article: Article) {
        val action = NewsListFragmentDirections.actionNewsListFragmentToDetailsFragment(article)
        findNavController().navigate(action)
    }

    private fun observeData() {
        Log.d("TAG", "onViewCreated: ")
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            if (!articles.isNullOrEmpty()) {
                Log.d("TAG", "onViewCreated: data fetched successfully")
                mainArticleList.clear()
                tempArticleList.clear()
                mainArticleList.addAll(articles)
                tempArticleList.addAll(articles)
                adapter.articleList = tempArticleList
                hideViews()
            } else if (articles == null) {
                Log.d("TAG", "onViewCreated: Network error")
                hideViews()
                binding!!.noInterNetView.visibility = View.VISIBLE
                binding!!.retryBtn.visibility = View.VISIBLE
            } else {
                Log.d("TAG", "onViewCreated: No Data")
                hideViews()
                binding!!.emptyView.visibility = View.VISIBLE
            }
        }
    }

    fun hideViews() {
        binding!!.emptyView.visibility = View.GONE
        binding!!.noInterNetView.visibility = View.GONE
        binding!!.progressBar.visibility = View.GONE
        binding!!.retryBtn.visibility = View.GONE

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {

                if (!newText.isNullOrEmpty()) {
                    tempArticleList.clear()
                    mainArticleList.forEach {
                        if (it.title.contains(newText.trim(), true)
                            || it.source.name.contains(newText.trim(), true)
                        )
                            tempArticleList.add(it)
                    }
                    if (tempArticleList.isEmpty())
                        binding!!.emptyView.visibility = View.VISIBLE
                    else
                        hideViews()

                    binding!!.mainRecycle.adapter!!.notifyDataSetChanged()
                } else {
                    tempArticleList.clear()
                    tempArticleList.addAll(mainArticleList)
                    binding!!.mainRecycle.adapter!!.notifyDataSetChanged()
                    hideViews()
                }

                return false
            }

        })
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}