package com.luthfirrohman.newspedia.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.ColumnInfo
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.luthfirrohman.newspedia.R
import com.luthfirrohman.newspedia.core.data.source.local.entity.NewsEntity
import com.luthfirrohman.newspedia.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //actionbar
        val actionbar = supportActionBar

        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val dataNews = intent.getParcelableExtra<NewsEntity>(EXTRA_TITLE)

        //set actionbar title
        actionbar?.title = dataNews?.source

        Glide.with(this)
            .load(dataNews?.urlToImage)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(binding.imgDetailBackdrop)
        Glide.with(this)
            .load(dataNews?.urlToImage)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(binding.imgDetailPoster)
        binding.apply {
            tvAuthor.text = dataNews?.author
            tvDesc.text = dataNews?.description
            tvPublish.text = dataNews?.publishedAt
            tvSource.text = dataNews?.source
            tvTitle.text = dataNews?.title
            btnLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(dataNews?.url)
                startActivity(intent)
            }
        }
    }

    // Back Button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_TITLE = "extra_title"
    }
}