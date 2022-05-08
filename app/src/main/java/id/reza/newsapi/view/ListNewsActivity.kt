package id.reza.newsapi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.reza.newsapi.adapter.CategoryAdapter
import id.reza.newsapi.adapter.NewsAdapter
import id.reza.newsapi.adapter.SourcesAdapter
import id.reza.newsapi.databinding.ActivityListNewsBinding
import id.reza.newsapi.databinding.ListNewsBinding
import id.reza.newsapi.model.Articles
import id.reza.newsapi.utils.OnLoadMoreListener
import id.reza.newsapi.utils.RecyclerViewLoadMoreScroll
import id.reza.newsapi.viewmodel.ListNewsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ListNewsActivity : AppCompatActivity(), NewsAdapter.Interface {

    private lateinit var binding: ActivityListNewsBinding

    var pageInt = 1

    val vm : ListNewsViewModel by viewModel()

    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    private lateinit var adapter: NewsAdapter
    private lateinit var listNews: MutableList<Articles>

    private var pilihan = "Business"
    private var tipeStr = "CATEGORY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListNewsBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        initView()
        initObserver()
        initListener()
    }

    private fun initListener() {
        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as GridLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
//                if (tipeStr.equals("CATEGORY")){
//                    vm.getListNewsCategory(pageInt, pilihan)
//
//                }else{
//                    vm.getListNewsSources(pageInt, pilihan)
//
//                }
                if (tipeStr.equals("CATEGORY")){
                    vm.getListNewsCategory(pageInt, pilihan)

                }else if (tipeStr.equals("SOURCES")){
                    vm.getListNewsSources(pageInt, pilihan)

                }else{
                    vm.getListNewsSearch(pageInt, pilihan)
                    binding.txtPencarian.text = "Pencarian : $pilihan"
                    binding.txtPencarian.visibility = View.VISIBLE
                }
//                vm.getData(pageStr)
            }
        })

        binding.rvList.addOnScrollListener(scrollListener)
    }

    private fun initView() {
        binding.progressBar.visibility = View.VISIBLE
        binding.txtPencarian.visibility = View.GONE

        tipeStr = this.intent.getStringExtra("TIPE")!!
        pilihan = this.intent.getStringExtra("PILIHAN")!!

//        business entertainment general health science sports technology.
        mLayoutManager = GridLayoutManager(this, 1)
        binding.rvList.layoutManager = mLayoutManager

        if (tipeStr.equals("CATEGORY")){
            vm.getListNewsCategory(pageInt, pilihan)

        }else if (tipeStr.equals("SOURCES")){
            vm.getListNewsSources(pageInt, pilihan)

        }else{
            vm.getListNewsSearch(pageInt, pilihan)
            binding.txtPencarian.text = "Pencarian : $pilihan"
            binding.txtPencarian.visibility = View.VISIBLE
        }

        listNews = ArrayList()

        adapter = NewsAdapter(listNews)

        binding.rvList.adapter = adapter

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
            Toast.makeText(this, ""+it, Toast.LENGTH_LONG).show()
        }

        vm.list.observe(this){
            pageInt++
            setAdapterNews(it.list!!)
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

    override fun onClickDetail(item: Articles, position: Int) {
        val i = Intent(this, DetailActivity::class.java)
        i.putExtra("URL", item.url)
        startActivity(i)
//        finish()
    }
}