package com.example.interview1.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.interview1.R
import com.example.interview1.databinding.AuthorBinding


class AuthorAdaptor(private var list: ArrayList<Index>?):RecyclerView.Adapter<AuthorAdaptor.ViewHolder>(){
    private lateinit var binding: AuthorBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = AuthorBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val id = list?.get(position)?.id.toString()
        val imageUrl = "https://d2xkd1fof6iiv9.cloudfront.net/images/courses/$id/169_820.jpg"
        Glide.with(holder.itemView.context).load(imageUrl).centerCrop()
            .placeholder(R.drawable.load_image_400)
            .into(holder.binding.imageView)
        holder.binding.title1.text = list?.get(position)?.title ?: ""
        holder.binding.subTitle.text = list?.get(position)?.educator ?: ""
        if(list?.get(position)?.owned ==1){
            holder.binding.tag.visibility = View.VISIBLE
            holder.binding.tagTitle.visibility = View.VISIBLE
        }else{
            holder.binding.tag.visibility = View.GONE
            holder.binding.tagTitle.visibility = View.GONE
        }
    }

    fun updateData(newData: List<Index>?) {
        list = newData as ArrayList<Index>?
        notifyDataSetChanged()
    }

   inner class ViewHolder(val binding: AuthorBinding):RecyclerView.ViewHolder(binding.root){}
}