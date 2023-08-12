package com.mr.elshoddev.globofly.helpers

import android.app.ActivityOptions
import android.app.ListActivity
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.mr.elshoddev.globofly.R

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.provider.MediaStore.Images.Media.getBitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.mr.elshoddev.globofly.models.Destination
import com.mr.elshoddev.globofly.activities.DestinationDetailActivity
import com.mr.elshoddev.globofly.activities.DestinationListActivity
import java.net.URL

class DestinationAdapter(
    private val context: Context,
    private val destinationList: List<Destination>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<DestinationAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(id: Int, image: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val destination = destinationList.get(position)
        holder.txvDestination.text = destination.city.toString()

        Glide
            .with(context)
            .load(destination.image.toString())
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(holder.image);

        holder.itemView.setOnClickListener { v ->
            onItemClickListener.onItemClick(
                destination.id,
                destination.image
            )
        }
    }


    override fun getItemCount(): Int {
        return destinationList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txvDestination: TextView = itemView.findViewById(R.id.txv_destination)
        val image: ImageView = itemView.findViewById(R.id.city_image)


    }

}
