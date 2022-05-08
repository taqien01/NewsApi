package id.reza.newsapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.reza.newsapi.databinding.ListCategoryBinding

class CategoryAdapter (val list: MutableList<String>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    lateinit var context: Context

    var mInterface: Interface? = null

    fun setInterface(mInterface: Interface?) {
        this.mInterface = mInterface
    }

    interface Interface {
        fun onClickCategory(cat: String)
    }

    class ViewHolder(val binding: ListCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var category = list[position]

        holder.binding.txtView.text = category

        holder.binding.crdNews.setOnClickListener {
            mInterface!!.onClickCategory(category)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}