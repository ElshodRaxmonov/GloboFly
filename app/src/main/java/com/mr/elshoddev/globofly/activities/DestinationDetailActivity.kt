package com.mr.elshoddev.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mr.elshoddev.globofly.R
import com.mr.elshoddev.globofly.databinding.ActivityDestinyDetailBinding
import com.mr.elshoddev.globofly.helpers.SampleData
import com.mr.elshoddev.globofly.models.Destination
import com.mr.elshoddev.globofly.services.DestinationService
import com.mr.elshoddev.globofly.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationDetailActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityDestinyDetailBinding.inflate(layoutInflater)
    }
    var imageUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.detailToolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        imageUrl = intent.getStringExtra("image").toString()
        Glide
            .with(this)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.wallpaper)
            .into(binding.imageCity);

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id = intent.getIntExtra(ARG_ITEM_ID, 0)

            loadDetails(id)

            initUpdateButton(id)

            initDeleteButton(id)
        }
    }

    private fun loadDetails(id: Int) {

        // To be replaced by retrofit code
//        val destination = SampleData.getDestinationById(id)
//
//        destination?.let {
//            binding.etCity.setText(destination.city)
//            binding.etDescription.setText(destination.description)
//            binding.etCountry.setText(destination.country)
//            binding.collapsingToolbar.title = destination.city
//        }

        val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
        val requestCall = destinationService.getDestination(id)

        requestCall.enqueue(object : Callback<Destination> {
            override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                if (response.isSuccessful) {
                    val destination = response.body()!!
                    destination?.let {
                        binding.etCity.setText(destination.city)
                        binding.etDescription.setText(destination.description)
                        binding.etCountry.setText(destination.country)
                        binding.collapsingToolbar.title = destination.city
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<Destination>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun initUpdateButton(id: Int) {

        binding.btnUpdate.setOnClickListener {

            val city = binding.etCity.text.toString()
            val description = binding.etDescription.text.toString()
            val country = binding.etCountry.text.toString()


            val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
            val requestCall =
                destinationService.updateDestination(id, city, country, description, imageUrl)
            // To be replaced by retrofit code

            requestCall.enqueue(object : Callback<Destination> {
                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful) {
                        finish()
                    }
                }

                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

        }
    }

    private fun initDeleteButton(id: Int) {

        binding.btnDelete.setOnClickListener {

            // To be replaced by retrofit code

            val requestCall =
                ServiceBuilder.buildService(DestinationService::class.java).deleteDestination(id)
            requestCall.enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    finish()
                }
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                }
            }
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, DestinationListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}
