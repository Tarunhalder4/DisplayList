package com.example.interview1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
        var educatorFilter = ArrayList<String>()
    }

    private var curriculumFilterData = mutableSetOf<String>()
    private var seriesFilterData = mutableSetOf<String>()
    private var skillFilterData = mutableSetOf<String>()
    private var styleFilterData = mutableSetOf<String>()
    private var educatorFilterData =mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = intent.getStringExtra(Constant.GROUP_TITLE)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val filterModel = listOf(
            FilterModel(Constant.CLEAR,FilterValue.emptyList),
            FilterModel(Constant.SKILL_ALL,skillFilterData),
            FilterModel(Constant.CURRICULM_ALL,curriculumFilterData),
            FilterModel(Constant.SERIES_ALL,seriesFilterData),
            FilterModel(Constant.STYLE_ALL,styleFilterData),
            FilterModel(Constant.EDUCATOR,educatorFilterData)
        )



        binding.filterRec.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.filterRec.adapter = FilterAdapter(filterModel,this)

        binding.Rec.layoutManager = GridLayoutManager(this,2)
        myList = intent.getParcelableArrayListExtra(Constant.DATA)
        adapter = AuthorAdaptor(myList)
        binding.Rec.adapter = adapter

        fillFilterData()


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
            adapter.updateData(filter(myList, curriculumFilter, seriesFilter, skillFilter, styleFilter, educatorFilter))
        }
    }

    private fun filter(newList:ArrayList<Index>?,
                       curriculumFilter:List<String>,
                       seriesFilter:List<String>,
                       skillFilter:List<String>,
                       styleFilter:List<String>,
                       educator:List<String>):List<Index>? {

        var list:List<Index>? = ArrayList<Index>()
        if(educator.size > 1)
            return list

        if(educator.size == 1){
            list =   newList?.filter { index ->
                index.curriculum_tags.containsAll(curriculumFilter) &&
                        index.series_tags.containsAll(seriesFilter) &&
                        index.skill_tags.containsAll(skillFilter) &&
                        index.style_tags.containsAll(styleFilter)&&
                        index.educator == educator[0]
            }
        }else{
            list =   newList?.filter { index ->
                index.curriculum_tags.containsAll(curriculumFilter) &&
                        index.series_tags.containsAll(seriesFilter) &&
                        index.skill_tags.containsAll(skillFilter) &&
                        index.style_tags.containsAll(styleFilter)
            }
        }
        return list
    }

    private fun fillFilterData(){
        myList?.forEach {it->
            curriculumFilterData.addAll(it.curriculum_tags)
            seriesFilterData.addAll(it.series_tags)
            skillFilterData.addAll(it.skill_tags)
            styleFilterData.addAll(it.style_tags)
            educatorFilterData.add(it.educator)
        }

        Log.d("tarun", "fillFilterData: ${educatorFilterData.size}")
    }

    fun reset(){
        curriculumFilter.clear()
        seriesFilter.clear()
        skillFilter.clear()
        styleFilter.clear()
        educatorFilter.clear()
        adapter.updateData(myList)
    }
}