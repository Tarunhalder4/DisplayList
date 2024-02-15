package com.example.interview1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.interview1.databinding.BottomSheetListBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetAdapter (private val tag:String, private val list: List<String>,private val bottomSheet:BottomSheetDialog): RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {

    private lateinit var binding: BottomSheetListBinding
    companion object{
        val filterValue1: MutableLiveData<String> = MutableLiveData()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        binding = BottomSheetListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item.text = list[position]

        holder.binding.item.setOnClickListener {
            holder.binding.check.visibility = View.VISIBLE
            holder.binding.item.setBackgroundResource(R.color.select_filter_item_Back_ground)

            setValueInFilerList(tag,holder.binding.item.text.toString())
            filterValue1.postValue(holder.binding.item.text.toString())

            bottomSheet.dismiss()
        }

    }

    private fun setValueInFilerList(tag: String,value:String){
        when(tag){
            FilterValue.CURRICULM_ALL ->
                ViewAllActivity.curriculumFilter.add(value)
            FilterValue.SERIES_ALL ->
                ViewAllActivity.seriesFilter.add(value)
            FilterValue.STYLE_ALL ->
                ViewAllActivity.styleFilter.add(value)
            FilterValue.SKILL_ALL ->
                ViewAllActivity.skillFilter.add(value)
        }
    }


    inner class ViewHolder(val binding: BottomSheetListBinding): RecyclerView.ViewHolder(binding.root){}
}