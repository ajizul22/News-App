package com.reserach.newsapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.reserach.newsapp.R
import com.reserach.newsapp.api.Api
import com.reserach.newsapp.api.RetrofitClient
import com.reserach.newsapp.data.model.NewsResponse
import com.reserach.newsapp.databinding.HomeFragmentBinding
import com.reserach.newsapp.ui.MainActivity
import com.reserach.newsapp.util.Constant
import com.reserach.newsapp.util.adapter.NewsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

class HomeFragment: Fragment() {

    private lateinit var bind:HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var service: Api

    private lateinit var coroutineScope: CoroutineScope

    private lateinit var adapter: NewsAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = RetrofitClient.getApiClient()!!.create(Api::class.java)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)

        adapter = NewsAdapter()
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewModel.setService(service)

        initRequestData()
        initComponent()
        initData()

        return bind.root
    }

    private fun initRequestData() {
        viewModel.callNewsApi("id")
        showLoading(true)
    }

    private fun initComponent() {
        bind.apply {
            rcvNews.layoutManager = layoutManager
            rcvNews.setHasFixedSize(true)
            rcvNews.adapter = adapter

            adapter.setOnItemClickCallback(object: NewsAdapter.OnItemClickCallback {
                override fun onItemClicked(data: NewsResponse.Articles) {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.putExtra("KEY_OPEN_LAYOUT", Constant.KEY_OPEN_LAYOUT_DETAIL_NEWS)
                    intent.putExtra("TITLE", data.title)
                    intent.putExtra("IMAGE", data.urlToImage)
                    intent.putExtra("AUTHOR", data.author)
                    intent.putExtra("CONTENT", data.content)
                    intent.putExtra("DATE", data.publishedAt)

                    startActivity(intent)

                }
            })
        }
    }

    private fun initData() {
        viewModel.listNews.observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it as ArrayList<NewsResponse.Articles>)
            }
        })

        viewModel.isSuccess.observe(viewLifecycleOwner, {
            if (it) {
                showLoading(false)
            }
        })
    }



    private fun showLoading(state: Boolean) {
        if (state) {
            bind.progressBar.visibility = View.VISIBLE
            bind.rcvNews.visibility = View.GONE
        } else {
            bind.rcvNews.visibility = View.VISIBLE
            bind.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

}