package com.mr.elshoddev.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.HorizontalScrollView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mr.elshoddev.globofly.R
import com.mr.elshoddev.globofly.databinding.ActivityDestinyListBinding
import com.mr.elshoddev.globofly.decoration.ItemDecoration
import com.mr.elshoddev.globofly.helpers.DestinationAdapter
import com.mr.elshoddev.globofly.helpers.SampleData
import com.mr.elshoddev.globofly.models.Destination
import com.mr.elshoddev.globofly.services.DestinationService
import com.mr.elshoddev.globofly.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationListActivity : AppCompatActivity(), DestinationAdapter.OnItemClickListener {

    private val binding by lazy {
        ActivityDestinyListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            binding.destinyRecyclerView.addItemDecoration(ItemDecoration(16, 12))
        }

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = title

        binding.fab.setOnClickListener {
            val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadDestinations()
    }

    private fun loadDestinations() {

        // To be replaced by retrofit code
        val destinationList = ServiceBuilder.buildService(DestinationService::class.java)
        val requestCall = destinationList.getDestinationList()

        requestCall.enqueue(object : Callback<List<Destination>> {
            override fun onResponse(
                call: Call<List<Destination>>,
                response: Response<List<Destination>>
            ) {
                if (response.isSuccessful) {
                    val destinationList = response.body()!!
                    Log.d("TAG", "onResponse: ${destinationList.toString()}")
                    binding.destinyRecyclerView.adapter =
                        DestinationAdapter(
                            this@DestinationListActivity,
                            destinationList,
                            this@DestinationListActivity
                        )
                }
            }

            override fun onFailure(call: Call<List<Destination>>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.cause}\n ${t.message}")
            }
        })
    }

    override fun onItemClick(id: Int, image: String) {

        val intent = Intent(this, DestinationDetailActivity::class.java)
        intent.putExtra(DestinationDetailActivity.ARG_ITEM_ID, id)
        intent.putExtra("image", image)
        startActivity(intent)

    }


}
