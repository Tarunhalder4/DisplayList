package com.example.interview1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.interview1.databinding.ActivityViewAllBinding
import com.example.interview1.model.AuthorAdaptor
import com.example.interview1.model.Index

class ViewAllActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewAllBinding
    private var myList: ArrayList<Index>? = null
    private var adapter:AuthorAdaptor = AuthorAdaptor(ArrayList<Index>())

    companion object{
        var curriculumFilter = ArrayList<String>()
        var seriesFilter = ArrayList<String>()
        var skillFilter = ArrayList<String>()
        var styleFilter = ArrayList<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = intent.getStringExtra(Constant.GROUP_TITLE)

        val filterModel = listOf(
            FilterModel(Constant.CLEAR,FilterValue.emptyList),
            FilterModel(Constant.SKILL_ALL,FilterValue.skill_tags),
            FilterModel(Constant.CURRICULM_ALL,FilterValue.curriculum_tags),
            FilterModel(Constant.SERIES_ALL,FilterValue.series_tags),
            FilterModel(Constant.STYLE_ALL,FilterValue.style_tags))


        binding.filterRec.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.filterRec.adapter = FilterAdapter(filterModel,this)

        binding.Rec.layoutManager = GridLayoutManager(this,2)
        myList = intent.getParcelableArrayListExtra(Constant.DATA)
        adapter = AuthorAdaptor(myList)
        binding.Rec.adapter = adapter


        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val data = ArrayList<Index>()
                myList?.forEach {
                    if(s.toString() in it.title.toLowerCase().toString() || s.toString() in it.educator.toLowerCase().toString()){
                        data.add(it)
                        adapter.updateData(data)
                    }else{
                        adapter.updateData(data)
                    }
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        filterObserver()
    }

    private fun filterObserver(){
        BottomSheetAdapter.filterValue1.observe(this){ filterValue ->
            adapter.updateData(filter(myList, curriculumFilter, seriesFilter, skillFilter, styleFilter))

        }
    }

    private fun filter(newList:ArrayList<Index>?,
                       curriculumFilter:List<String>,
                       seriesFilter:List<String>,
                       skillFilter:List<String>,
                       styleFilter:List<String>):List<Index>? {
        val list =   newList?.filter { index ->
            index.curriculum_tags.containsAll(curriculumFilter) &&
                    index.series_tags.containsAll(seriesFilter) &&
                    index.skill_tags.containsAll(skillFilter) &&
                    index.style_tags.containsAll(styleFilter)
        }
        return list
    }

    fun reset(){
        curriculumFilter.clear()
        seriesFilter.clear()
        skillFilter.clear()
        styleFilter.clear()
        adapter.updateData(myList)
    }
}