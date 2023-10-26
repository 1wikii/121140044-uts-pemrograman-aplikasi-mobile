package com.example.uts_project.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_project.R
import com.example.uts_project.UserAdapter
import com.example.uts_project.model.DataItem
import com.example.uts_project.model.ResponseUser
import com.example.uts_project.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import java.util.Locale


class HomeFragment : Fragment() {

    private lateinit var adapter: UserAdapter
    private lateinit var searchView: SearchView

    private var query: CharSequence? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserAdapter(mutableListOf())

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_users)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        searchView = view.findViewById(R.id.search_action)

        recyclerView.setOnClickListener{
            val intent = Intent(activity, HomeDetailFragment::class.java)
            startActivity(intent)
        }



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                query = newText

                val client = ApiConfig.getApiService().getListUsers("1")

                val pattern = query.toString().lowercase(Locale.ROOT).trim()

                client.enqueue(object : Callback<ResponseUser> {
                    override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                        if (response.isSuccessful) {
                            val dataArray = response.body()?.data as List<DataItem>
                            for (data in dataArray) {

                                if(query.isNullOrEmpty()){
                                    adapter.addUser(data)
                                }else{

                                    if(data.firstName?.lowercase(Locale.ROOT)?.contains(pattern) == true ||
                                        data.lastName?.lowercase(Locale.ROOT)?.contains(pattern) == true
                                    ){
                                        adapter.clear()
                                        adapter.addUser(data)
                                    }
                                }

                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                        t.printStackTrace()
                    }
                })


                return false
            }
        })

        getUser(query)

    }

    private fun getUser(query: CharSequence?) {
        val client = ApiConfig.getApiService().getListUsers("1")

        val pattern = query.toString().lowercase(Locale.ROOT).trim()

        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()?.data as List<DataItem>
                    for (data in dataArray) {

                        if(query.isNullOrEmpty()){
                            adapter.addUser(data)
                        }else{

                            if(data.firstName?.lowercase(Locale.ROOT)?.contains(pattern) == true ||
                                data.lastName?.lowercase(Locale.ROOT)?.contains(pattern) == true
                            ){
                                adapter.clear()
                                adapter.addUser(data)
                            }
                        }

                    }
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }


}