package com.mr.elshoddev.globofly.activities

import androidx.appcompat.app.AppCompatActivity
import com.mr.elshoddev.globofly.R


import android.content.Intent
import android.os.Bundle

import android.view.View
import com.mr.elshoddev.globofly.databinding.ActivityWelcomeBinding
import com.mr.elshoddev.globofly.services.MessageService
import com.mr.elshoddev.globofly.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WelcomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val service = ServiceBuilder.buildService(MessageService::class.java)
        val requestCall = service.getMessages("http://10.0.2.2:7000/messages")
        requestCall.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {
                    response.body()?.let {
                        binding.message.text = response.body()!!
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }
        })

        binding.message.text = "Black Friday! Get 50% cash back on saving your first spot."


    }

    fun getStarted(view: View) {
        val intent = Intent(this, DestinationListActivity::class.java)
        startActivity(intent)
        finish()
    }
}
