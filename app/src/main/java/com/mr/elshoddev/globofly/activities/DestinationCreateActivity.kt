package com.mr.elshoddev.globofly.activities


import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.jsoup.Jsoup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mr.elshoddev.globofly.databinding.ActivityDestinyCreateBinding
import com.mr.elshoddev.globofly.models.Destination
import com.mr.elshoddev.globofly.services.DestinationService
import com.mr.elshoddev.globofly.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class DestinationCreateActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityDestinyCreateBinding.inflate(layoutInflater)
    }
    var imageUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val context = this

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        binding.imagePicker.setOnClickListener {
//            uploadImage()
//        }

        binding.btnAdd.setOnClickListener {
            if (binding.etCity.text!!.isEmpty()) {
                Toast.makeText(this, "Please create city name", Toast.LENGTH_SHORT).show()
            } else if (binding.etCountry.text.isEmpty()) {
                Toast.makeText(this, "Please create country name", Toast.LENGTH_SHORT).show()
            } else if (binding.etDescription.text.isEmpty()) {
                Toast.makeText(
                    this,
                    "Create short description about the city you are gonna goo",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                val newDestination = Destination()
                newDestination.city = binding.etCity.text.toString()
                newDestination.description = binding.etDescription.text.toString()
                newDestination.country = binding.etCountry.text.toString()

                newDestination.image = imageUrl
                Log.d("TAGTAG", "onCreate: $imageUrl")
                val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
                val requestCall = destinationService.addDestination(destination = newDestination)

                requestCall.enqueue(object : Callback<Destination> {
                    override fun onResponse(
                        call: Call<Destination>,
                        response: Response<Destination>
                    ) {
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

        binding.imagePicker.setOnClickListener {
            val searchText = binding.etCity.text.toString()
            if (searchText.isNotEmpty()) {
                val formattedText = searchText.replace(" ", "+")
                val searchUrl =
                    "https://www.google.com/search?q=$formattedText+city+high+quality+image&tbm=isch"
                SearchImagesTask().execute(searchUrl)
            }
        }

    }

    inner class SearchImagesTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String): String {
            val url = params[0]
            try {
                val doc = Jsoup.connect(url).get()
                val imageElement = doc.select("img[data-src]").first()
                val imageUrl = imageElement?.attr("data-src")
                return imageUrl ?: ""
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return ""
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            if (result.isNotEmpty()) {
                imageUrl = result

                Glide.with(this@DestinationCreateActivity).load(result).centerCrop()
                    .into(binding.imageCityNew)
            }
        }
    }

}


