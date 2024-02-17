package com.example.interview1

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interview1.databinding.BottomSheetBinding
import com.example.interview1.databinding.FilterLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class FilterAdapter (private val list: List<FilterModel>,public val viewAllActivity: ViewAllActivity): RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    private lateinit var binding: FilterLayoutBinding
    private var filterAdapter: FilterAdapter = this

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
            Log.d("tarun", "onBindViewHolder: ")

            if(holder.binding.filterTitle.text == Constant.CLEAR){
                if(viewAllActivity.findFilterItemCount() > 0){
                    viewAllActivity.reset()
                    holder.binding.filterTitle.setBackgroundColor(holder.binding.root.context.getColor(R.color.filter_Back_ground))
                }else{
                    holder.binding.filterTitle.setBackgroundColor(holder.binding.root.context.getColor(R.color.select_filter_item_Back_ground))
                }

            }else{
                val bottomSheet = BottomSheetDialog(holder.itemView.context)

                val binding = BottomSheetBinding.inflate(LayoutInflater.from(holder.itemView.context))
                binding.title.text = list[position].title

                binding.recyclerView.apply {
                    this.layoutManager = LinearLayoutManager(holder.itemView.context)
                  //  this.adapter =  BottomSheetAdapter(list[position].title,list[position].option,bottomSheet)
                    this.adapter =  BottomSheetAdapter(list[position].title,list.get(position).option,bottomSheet,holder.binding, filterAdapter )
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