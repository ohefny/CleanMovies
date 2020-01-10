package com.example.swvlmovies.modules.movies.features.details.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swvlmovies.R
import kotlinx.android.synthetic.main.item_movie_photo.view.*
import java.util.*

class MoviePhotosAdapter : RecyclerView.Adapter<MoviePhotosAdapter.PhotoVH>() {

    private var data: List<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoVH {
        return PhotoVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_photo, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: PhotoVH, position: Int) {
        holder.bind(data[position])
    }

    fun setData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
    }
    class PhotoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String) = with(itemView) {
            Glide.with(context)
                .load(item)
                .centerCrop()
                .into(imgMovie)
        }
    }
}