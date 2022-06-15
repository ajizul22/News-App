package com.reserach.newsapp.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.reserach.newsapp.data.model.NewsResponse
import com.reserach.newsapp.databinding.ItemNewsBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val list = ArrayList<NewsResponse.Articles>()
    private var onItemClickCallback: NewsAdapter.OnItemClickCallback? = null

    fun setList(news: ArrayList<NewsResponse.Articles>) {
        list.clear()
        list.addAll(news)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: NewsResponse.Articles)
    }

    fun setOnItemClickCallback(onItemClickCallback: NewsAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class NewsViewHolder(val bind: ItemNewsBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(data: NewsResponse.Articles) {
            bind.apply {
                Glide.with(itemView)
                    .load(data?.urlToImage)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivNews)

                tvTitle.text = data?.title

                bind.root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size


}