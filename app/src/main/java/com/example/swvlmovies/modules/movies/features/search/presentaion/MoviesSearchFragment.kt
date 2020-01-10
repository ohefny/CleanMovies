package com.example.swvlmovies.modules.movies.features.search.presentaion

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.swvlmovies.R
import com.example.swvlmovies.core.extention.*
import com.example.swvlmovies.core.presentation.ViewModelFactory
import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movies_search.view.*
import kotlinx.android.synthetic.main.view_movie_list.*
import kotlinx.android.synthetic.main.view_movie_list.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class MoviesSearchFragment : DaggerFragment() {
    @Inject
    lateinit var mViewModelFactory: ViewModelFactory
    private lateinit var mRootView: View
    private lateinit var adapter: MoviesAdapter
    private var movieSelectedListener:MovieSelectedListener by Delegates.notNull()
    private val mViewModel by lazy {
        ViewModelProviders.of(this, mViewModelFactory)
            .get(MoviesSearchViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        check(context is MovieSelectedListener) { "Activity should implement MovieSelectedListener " }
        movieSelectedListener= context
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.fragment_movies_search, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservation()
    }

    private fun initObservation() = with(mViewModel) {
        clickedMovie.observe(viewLifecycleOwner, onEmission = movieSelectedListener::onMovieSelected)
        categorizedGenreMovies.observeResource(viewLifecycleOwner,
            doOnSuccess = adapter::addData,
            doOnLoading = { toggleLoading(true) },
            doOnTerminate = { toggleLoading(false) },
            doOnError = {
                adapter.reset()
                showNotValidGenre()
            })
    }

    private fun showNotValidGenre() {
        textView_movieList_callToAction.text = getString(R.string.search_error_place_holder)
        textView_movieList_callToAction.visible()
    }

    private fun toggleLoading(showLoading: Boolean) {
        progressBar_movieList_loading.toggleVisibility(visible = showLoading)
        if (mViewModel.lastSearchMovies.isEmpty())
            textView_movieList_callToAction.toggleVisibility(visible = !showLoading)
        else
            textView_movieList_callToAction.toggleVisibility(false)
    }

    private fun initViews() = with(mRootView) {
        adapter = MoviesAdapter(mViewModel::onMovieClicked)
        button_search onClick { mViewModel.searchGenreMovies(editText_search.text.toString()) }
        recyclerView_movieList.adapter = adapter

    }
    interface MovieSelectedListener{
        fun onMovieSelected(movie: Movie)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MoviesSearchFragment.
         */
        @JvmStatic
        fun newInstance() =
            MoviesSearchFragment().apply {
                arguments = Bundle()
            }
    }
}
