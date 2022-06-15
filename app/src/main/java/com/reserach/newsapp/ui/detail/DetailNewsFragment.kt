package com.reserach.newsapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.reserach.newsapp.R
import com.reserach.newsapp.databinding.DetailNewsFragmentBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailNewsFragment: Fragment() {
    companion object {
        const val ARG_TITLE = "title"
        const val ARG_AUTHOR = "author"
        const val ARG_CONTENT = "content"
        const val ARG_DATE = "date"
        const val ARG_IMAGE = "image"


        fun newInstance(title: String?, author: String?, image: String?, date: String?, content: String?): DetailNewsFragment {
            val fragment = DetailNewsFragment()

            val bundle = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_AUTHOR, author)
                putString(ARG_CONTENT, content)
                putString(ARG_DATE, date)
                putString(ARG_IMAGE, image)
            }

            fragment.arguments = bundle

            return fragment
        }
    }

    private lateinit var bind: DetailNewsFragmentBinding

    private var title: String? = null
    private var author: String? = null
    private var image: String? = null
    private var content: String? = null
    private var date: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.detail_news_fragment, container, false)

        title = arguments?.getString(ARG_TITLE)
        image = arguments?.getString(ARG_IMAGE)
        date = arguments?.getString(ARG_DATE)
        content = arguments?.getString(ARG_CONTENT)
        author = arguments?.getString(ARG_AUTHOR)

        initComponent()

        return bind.root
    }

    private fun initComponent() {
        bind.apply {
            Glide.with(context!!)
                .load(image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(bind.ivNews)

            if (author != null) {
                tvAuthor.text = author
            } else {
                tvAuthor.text = "-"
            }

            if (content != null) {
                tvContent.text = content
            } else {
                tvContent.text = "-"
            }

            if (title != null) {
                tvTitle.text = title
            } else {
                tvTitle.text = "-"
            }

            if (date != null) {
                tvDate.text = formatDate(date!!)
            } else {
                tvDate.text = "-"
            }
        }
    }

    fun formatDate(inputDate: String): String? {
        val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)

        val date: Date = inputFormat.parse(inputDate)
        val outputText: String = outputFormat.format(date)

        return outputText
    }


}