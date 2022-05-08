package id.reza.newsapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.reza.newsapi.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()
    }

    private fun initView() {
        var urlWeb = intent.getStringExtra("URL")

        binding.webView.loadUrl("$urlWeb")
    }

}