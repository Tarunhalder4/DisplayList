package com.example.interview1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.interview1.databinding.ActivityMainBinding
import com.example.interview1.model.Collections
import com.example.interview1.model.Index
import com.example.interview1.model.Result
import com.example.interview1.model.SongsModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /////"id": 48,"title": "50 Blues Rhythms You MUST Know","educator": "Corey Congilio",

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel:ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        booKStatusObserve()
        viewModel.getSongsStatus()

    }


    private fun booKStatusObserve(){
        viewModel.songsStatus.observe(this){
            when(it.status){
                Status.LOADING ->{
                    binding.pro.visibility = View.VISIBLE
                    binding.massage.visibility =View.GONE
                }
                Status.SUCCESS ->{
                    binding.pro.visibility = View.GONE
                    binding.massage.visibility =View.VISIBLE

                    val adapter = AuthorGroupAdapter(getMainData(it))
                    binding.rec.layoutManager = LinearLayoutManager(this)
                    binding.rec.adapter = adapter

                }
                else ->{
                    binding.pro.visibility = View.GONE
                    binding.massage.visibility =View.VISIBLE
                    binding.massage.text  = it.error.toString()
                }
            }

        }
    }

    private fun getData(type:String,collections: Collections,result: Result):ArrayList<Index>{

        val result1 = ArrayList<Index>()
        collections.smart.forEach {
            if(it.id == type){
                it.courses.forEach { id ->
                    result.index.forEach {it->
                        if(it.id == id){
                            result1.add(it)
                        }
                    }

                }
            }
        }
        return result1
    }

    private fun getMainData(it:Data<SongsModel>):ArrayList<AuthorGroupModel>{
        val id = listOf("owned","recently_watched","favorites","wishlist","in_progress")

        val mainData = ArrayList<AuthorGroupModel>()
        id.forEach {id->
            val data = it.data?.result?.let { it1 ->
                it.data.result.collections.let { it2 ->
                    getData(id, it2,
                        it1
                    )
                }
            }
            mainData.add(AuthorGroupModel(id, data))
        }

        return mainData
    }
}