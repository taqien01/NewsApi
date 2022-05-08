package id.reza.newsapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.reza.newsapi.databinding.ListNewsBinding
import id.reza.newsapi.model.Articles

class NewsAdapter(val list: List<Articles>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>(){

    lateinit var context: Context

    var mInterface: Interface? = null

    fun setInterface(mInterface: Interface?) {
        this.mInterface = mInterface
    }

    interface Interface {
        fun onClickDetail(item: Articles, position: Int)
    }

    class ViewHolder (val binding: ListNewsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        context = parent.context

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val berita = list[position]

        holder.binding.txtTitle.text = berita.title
        holder.binding.txtDescription.text = berita.description

        Glide.with(context).load(berita.urlToImage).into(holder.binding.imageView)

        holder.binding.crdNews.setOnClickListener {
            mInterface!!.onClickDetail(berita, position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}