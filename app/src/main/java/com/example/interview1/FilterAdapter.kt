package com.example.interview1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interview1.databinding.BottomSheetBinding
import com.example.interview1.databinding.FilterLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class FilterAdapter (private val list: List<FilterModel>,private val viewAllActivity: ViewAllActivity): RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    private lateinit var binding: FilterLayoutBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = FilterLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.filterTitle.text = list[position].title

        holder.binding.root.setOnClickListener {

            if(holder.binding.filterTitle.text == FilterValue.CLEAR){
                viewAllActivity.reset()
            }else{
                val bottomSheet = BottomSheetDialog(holder.itemView.context)

                val binding = BottomSheetBinding.inflate(LayoutInflater.from(holder.itemView.context))
                binding.title.text = list[position].title

                binding.recyclerView.apply {
                    this.layoutManager = LinearLayoutManager(holder.itemView.context)
                    this.adapter =  BottomSheetAdapter(list[position].title,list[position].option,bottomSheet)
                }

                bottomSheet.setContentView(binding.root)
                bottomSheet.setCanceledOnTouchOutside(false)
                bottomSheet.show()

                binding.cancelButton.setOnClickListener {
                    bottomSheet.dismiss()
                }
            }


        }

    }


    inner class ViewHolder(val binding: FilterLayoutBinding): RecyclerView.ViewHolder(binding.root){}
}