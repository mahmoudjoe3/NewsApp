package com.mahmoudjoe3.newsapp.ui.news_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mahmoudjoe3.newsapp.R
import com.mahmoudjoe3.newsapp.databinding.NewsItemLayoutBinding
import com.mahmoudjoe3.newsapp.pojo.Article
import java.text.SimpleDateFormat


class NewsAdapter( private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<NewsAdapter.VH>() {
    var articleList: List<Article> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(NewsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val article = articleList.get(position)
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class VH(private val binding: NewsItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener{
                if(adapterPosition!=RecyclerView.NO_POSITION)
                    onItemClickListener.onItemClick(articleList[adapterPosition])
            }
        }

        fun bind(article: Article) {
            binding.apply {
                Glide.with(itemView)
                    .load(article.urlToImage)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(itemImage)

                itemSourceName.text = article.source.name
                itemTitle.text = article.title

                val date: String = parseDate(
                    article.publishedAt.replace("Z", ""),
                    "MMM dd, yyyy"
                )
                val time: String = parseDate(
                    article.publishedAt.replace("Z", ""),
                    "h:mm a"
                )

                itemPublishDate.text = date
                itemPublishTime.text = time

            }
        }

        fun parseDate(time: String, outputPattern: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val outputFormat = SimpleDateFormat(outputPattern)
            return outputFormat.format(inputFormat.parse(time)!!)
        }
    }

    interface OnItemClickListener{
        fun onItemClick(article: Article)
    }

}