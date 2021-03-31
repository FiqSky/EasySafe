package com.fiqsky.easysafe.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fiqsky.easysafe.MainActivity
import com.fiqsky.easysafe.R
import com.fiqsky.easysafe.models.Features
import kotlinx.android.synthetic.main.list_item.view.*

@Suppress("DEPRECATION", "UNREACHABLE_CODE")
class FeaturesAdapter(private val arrayList: List<Features>,
                      private val adapterOnClick: (Features) -> Unit) : RecyclerView.Adapter<FeaturesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(features: Features) {
            itemView.apply {
                tv_list.text = features.name
                setOnClickListener {
                    adapterOnClick(features)
                }
            }
        }
    }
}