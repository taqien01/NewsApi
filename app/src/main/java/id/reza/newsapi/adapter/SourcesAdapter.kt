package id.reza.newsapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.reza.newsapi.databinding.ListCategoryBinding
import id.reza.newsapi.model.Sources

class SourcesAdapter(private val list: List<Sources>) : RecyclerView.Adapter<SourcesAdapter.ViewHolder>() {

    lateinit var context: Context

    var mInterface: Interface? = null

    fun setInterface(mInterface: Interface?) {
        this.mInterface = mInterface
    }

    interface Interface {
        fun onClickSources(cat: String)
    }

    class ViewHolder(val binding: ListCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var sources = list[position]

        holder.binding.txtView.text = sources.name

        holder.binding.crdNews.setOnClickListener {
            mInterface!!.onClickSources(sources.id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}