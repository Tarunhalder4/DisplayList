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
   // private var myList: ArrayList<Index>? = null
  //  private var myList: MutableLiveData<ArrayList<Index>> = MutableLiveData()
    private var adapter:AuthorAdaptor = AuthorAdaptor(ArrayList<Index>())
    private var myList1:ArrayList<Index> = ArrayList()
//    private var searchList:ArrayList<Index> = ArrayList()
//    private var search = false
//
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

       clearFilter()

        myList1 = intent.getParcelableArrayListExtra(Constant.DATA)!!
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


        adapter = AuthorAdaptor(myList1)
        binding.Rec.adapter = adapter

        mainSearch(myList1)


        fillFilterData()



    }

    private fun mainSearch(list:ArrayList<Index>){
        filterObserver(list)

        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val data = ArrayList<Index>()
                myList1.forEach { t1->
                    if(s.toString() in t1.title.toLowerCase().toString() || s.toString() in t1.educator.toLowerCase().toString()){
                        data.add(t1)

                    }
                    adapter.updateData(data)

                }
              //  myList1 = data
              //  myList.postValue(data)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

    }

    private fun filterObserver(myList:ArrayList<Index>){
        BottomSheetAdapter.filterValue1.observe(this){ filterValue ->
             filter(myList1, curriculumFilter, seriesFilter, skillFilter, styleFilter, educatorFilter)?.let {
                // myList1 = ArrayList<Index>(it)
                 val  newMyList1 = ArrayList<Index>(it)
                 adapter.updateData(newMyList1)
            }

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
        myList1.forEach {it->
            curriculumFilterData.addAll(it.curriculum_tags)
            seriesFilterData.addAll(it.series_tags)
            skillFilterData.addAll(it.skill_tags)
            styleFilterData.addAll(it.style_tags)
            educatorFilterData.add(it.educator)
        }

    }

    fun reset(){
        clearFilter()
        myList1 = intent.getParcelableArrayListExtra(Constant.DATA)!!
        adapter.updateData(myList1)
      //  myList?.postValue(myList1)
    }

    private fun clearFilter(){
        curriculumFilter.clear()
        seriesFilter.clear()
        skillFilter.clear()
        styleFilter.clear()
        educatorFilter.clear()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}