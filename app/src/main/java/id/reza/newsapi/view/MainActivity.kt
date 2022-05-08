package id.reza.newsapi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.reza.newsapi.R
import id.reza.newsapi.adapter.CategoryAdapter
import id.reza.newsapi.adapter.NewsAdapter
import id.reza.newsapi.adapter.SourcesAdapter
import id.reza.newsapi.databinding.ActivityMainBinding
import id.reza.newsapi.model.Articles
import id.reza.newsapi.model.NewsSources
import id.reza.newsapi.model.Sources
import id.reza.newsapi.utils.OnLoadMoreListener
import id.reza.newsapi.utils.RecyclerViewLoadMoreScroll
import id.reza.newsapi.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), NewsAdapter.Interface, SourcesAdapter.Interface, CategoryAdapter.Interface {

    lateinit var binding: ActivityMainBinding

    val vm : MainViewModel by viewModel()

    var pageInt = 1

    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    private lateinit var listCategory: MutableList<String>
    private lateinit var adapterSource: SourcesAdapter
    private lateinit var adapterCategory: CategoryAdapter
    private lateinit var adapter: NewsAdapter

    private lateinit var listNews: MutableList<Articles>
    private lateinit var listSource: MutableList<Sources>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()
        initObserver()
        initListener()
    }

    private fun initView() {
        binding.progressBar.visibility = View.VISIBLE

//        business entertainment general health science sports technology.
        listCategory = ArrayList()

        listCategory.add("business")
        listCategory.add("entertainment")
        listCategory.add("general")
        listCategory.add("health")
        listCategory.add("science")
        listCategory.add("sports")
        listCategory.add("technology")

        mLayoutManager = GridLayoutManager(this, 2)
        binding.rvListCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvListSources.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvList.layoutManager = mLayoutManager

        vm.getSource()
        vm.getListNews(pageInt)

        listNews = ArrayList()
        listSource = ArrayList()

        adapter = NewsAdapter(listNews)
        adapterCategory = CategoryAdapter(listCategory)
        adapterSource = SourcesAdapter(listSource)

        binding.rvList.adapter = adapter
        binding.rvListCategory.adapter = adapterCategory
        binding.rvListSources.adapter = adapterSource

        adapterCategory.notifyDataSetChanged()
        adapterCategory.setInterface(this)

    }

    private fun initListener() {
        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as GridLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                vm.getListNews(pageInt)
//                vm.getData(pageStr)
            }
        })

        binding.btnSearch.setOnClickListener {
            if (binding.edtSearch.text.toString().equals("") || binding.edtSearch.text.toString().equals(" ")){
                //do nothing
            }else{
                val i = Intent(this, ListNewsActivity::class.java)
                i.putExtra("TIPE", "PENCARIAN")
                i.putExtra("PILIHAN", binding.edtSearch.text.toString())
                startActivity(i)
            }
        }

        binding.rvList.addOnScrollListener(scrollListener)
    }

    private fun initObserver() {
        vm.loadingEvent.observe(this) {
            if (it){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

        vm.errorEvent.observe(this){
            Toast.makeText(this, it+"", Toast.LENGTH_LONG).show()
        }

        vm.list.observe(this){
            pageInt++
            setAdapterNews(it.list!!)
        }

        vm.listSources.observe(this){
            setAdapterSource(it.list!!)
        }
    }

    private fun setAdapterSource(list: List<Sources>){
        if (list.isNullOrEmpty()){
            binding.rvListSources.visibility = View.GONE
            binding.rlTroubleSources.visibility = View.VISIBLE
        }else{
            binding.rvListSources.visibility = View.VISIBLE
            binding.rlTroubleSources.visibility = View.GONE

            listSource.addAll(list)

            adapterSource.notifyDataSetChanged()
            adapterSource.setInterface(this)

        }
    }

    private fun setAdapterNews(list: List<Articles>){
        if (list.isNullOrEmpty()){
            binding.rvList.visibility = View.GONE
            binding.rlTrouble.visibility = View.VISIBLE
        }else{
            binding.rvList.visibility = View.VISIBLE
            binding.rlTrouble.visibility = View.GONE

            listNews.addAll(list)

            adapter.notifyDataSetChanged()
            adapter.setInterface(this)

            scrollListener.setLoaded()

        }
    }

    override fun onClickCategory(cat: String) {
        val i = Intent(this, ListNewsActivity::class.java)
        i.putExtra("TIPE", "CATEGORY")
        i.putExtra("PILIHAN", cat)
        startActivity(i)
//        finish()
    }

    override fun onClickDetail(item: Articles, position: Int) {
        val i = Intent(this, DetailActivity::class.java)
        i.putExtra("URL", item.url)
        startActivity(i)
//        finish()
    }

    override fun onClickSources(cat: String) {
        val i = Intent(this, ListNewsActivity::class.java)
        i.putExtra("TIPE", "SOURCES")
        i.putExtra("PILIHAN", cat)
        startActivity(i)
//        finish()
    }

}