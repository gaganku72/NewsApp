package com.zeuscoder69.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class newsAdapter(private val listener: itemClicked): RecyclerView.Adapter<newsViewHolder>() {
    private val items: ArrayList<news> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.news_items,parent,false)
        val viewHolder = newsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: newsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imgUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun newsUpdate(newsUpdated: ArrayList<news>){
        items.clear()
        items.addAll(newsUpdated)

        notifyDataSetChanged()
    }
}

class newsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    val titleView: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)
}

interface itemClicked{
    fun onItemClicked(item: news)
}