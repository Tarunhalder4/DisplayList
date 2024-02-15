package com.example.interview1

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interview1.databinding.AuthorGroupBinding
import com.example.interview1.model.AuthorAdaptor


class AuthorGroupAdapter(private val list: List<AuthorGroupModel>):RecyclerView.Adapter<AuthorGroupAdapter.ViewHolder>() {

    private lateinit var binding: AuthorGroupBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = AuthorGroupBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
  }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.groupId.text = list[position].groupTitle

        val data = list[position].list

        val adapter = AuthorAdaptor(data)
        val linearLayout = LinearLayoutManager(holder.binding.rec.context, LinearLayoutManager.HORIZONTAL, false)


        holder.binding.rec.apply {
            this.layoutManager = linearLayout
            this.adapter = adapter
        }

        binding.textView.setOnClickListener{
            val intent = Intent(holder.binding.root.context,ViewAllActivity::class.java)
            intent.putExtra("group_title", list[position].groupTitle)
            intent.putParcelableArrayListExtra("data", list[position].list)
            holder.binding.root.context.startActivity(intent)
        }

    }

   inner class ViewHolder(val binding: AuthorGroupBinding):RecyclerView.ViewHolder(binding.root){}
}



