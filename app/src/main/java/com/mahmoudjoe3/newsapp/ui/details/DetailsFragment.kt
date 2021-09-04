package com.mahmoudjoe3.newsapp.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mahmoudjoe3.newsapp.R
import com.mahmoudjoe3.newsapp.databinding.FragmentDetailsBinding
import java.text.SimpleDateFormat

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)
        binding.apply {
            val article = args.articel
            Glide.with(this@DetailsFragment)
                .load(article.urlToImage)
                .error(R.drawable.ic_error)
                .into(imageView)

            source.text = article.source.name
            title.text = article.title

            val date: String = parseDate(
                article.publishedAt.replace("Z", ""),
                "MMM dd, yyyy"
            )
            val time: String = parseDate(
                article.publishedAt.replace("Z", ""),
                "h:mm a"
            )
            timeDate.text = "$time  $date"
            description.text = article.description
        }

    }
    fun parseDate(time: String, outputPattern: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormat = SimpleDateFormat(outputPattern)
        return outputFormat.format(inputFormat.parse(time)!!)
    }
}