package com.fiqsky.easysafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fiqsky.easysafe.adapter.FeaturesAdapter
import com.fiqsky.easysafe.models.FeaturesData
import com.fiqsky.easysafe.webview.ArticleActivity
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    private val listData = FeaturesData
    private lateinit var featuresAdapter: FeaturesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        showRecyclerView()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    private fun showRecyclerView() {
        featuresAdapter = FeaturesAdapter(listData.listFeatures) { featuresAdapter ->
            when (featuresAdapter.id) {
                1 -> startActivity(Intent(this@ListActivity, MainActivity::class.java))
                2 -> startActivity(Intent(this@ListActivity, PharmacyActivity::class.java))
                3 -> startActivity(Intent(this@ListActivity, HealthArticleActivity::class.java))
                4 -> startActivity(Intent(this@ListActivity, SosActivity::class.java))
                5 -> Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Maaf", Toast.LENGTH_SHORT).show()
            }
        }

        rv_list.apply {
            layoutManager = LinearLayoutManager(this@ListActivity)
            adapter = featuresAdapter
        }
    }
}