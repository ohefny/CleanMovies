package com.example.swvlmovies.modules.movies.search.presentaion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.swvlmovies.R
import com.example.swvlmovies.core.extention.onClick
import kotlinx.android.synthetic.main.listitem_movie.view.*
import kotlinx.android.synthetic.main.listitem_movie_header.view.*

class MoviesAdapter constructor(val onMovieClickedListener: (CategorizedMovieUI.MovieUI) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: MutableList<CategorizedMovieUI> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MOVIE_TYPE) MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_movie, parent, false)
        ) else YearViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_movie_header, parent, false)
        )
    }

    override fun getItemCount() = data.size
    override fun getItemViewType(position: Int): Int {
        return if (data[position] is CategorizedMovieUI.MovieUI) MOVIE_TYPE
        else YEAR_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder)
            holder.bind(data[position] as CategorizedMovieUI.MovieUI)
        else if (holder is YearViewHolder)
            holder.bind(data[position] as CategorizedMovieUI.YearUI)
    }

    fun addData(newData: List<CategorizedMovieUI>) {
        this.data.clear()
        this.data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: CategorizedMovieUI.MovieUI) = with(itemView) {
            tvMovieTitle.text = movie.title
            onClick{onMovieClickedListener(movie)}
        }
    }

    inner class YearViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(year: CategorizedMovieUI.YearUI) = with(itemView) {
            tvMoviesYear.text = "${year.value}"
        }
    }

    companion object {
        const val MOVIE_TYPE = 1
        const val YEAR_TYPE = 0
    }
}