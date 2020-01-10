package com.example.swvlmovies.modules.movies.features.details.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.example.swvlmovies.R
import com.example.swvlmovies.core.extention.*
import com.example.swvlmovies.core.presentation.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_movie_details.*
import timber.log.Timber
import javax.inject.Inject
import dagger.android.support.DaggerFragment

class MovieDetailsFragment : DaggerFragment() {
    @Inject
    lateinit var mViewModelFactory: ViewModelFactory
    private lateinit var mRootView: View
    private val movieYearArg by lazy {
        arguments!!.getInt(MOVIE_YEAR_KEY)
    }
    private val movieTitleArg by lazy {
        arguments!!.getString(MOVIE_TITLE_KEY)!!
    }
    private val mViewModel by lazy {
        ViewModelProviders.of(this, mViewModelFactory)
            .get(MovieDetailsViewModel::class.java)
    }
    private val adapter=MoviePhotosAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadMovieDetails()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.fragment_movie_details, container, false)
        return mRootView;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservation()
        txt_error onClick ::loadMovieDetails
    }

    private fun loadMovieDetails() = mViewModel.loadMovieDetails(movieTitleArg, movieYearArg)
    private fun initObservation() = with(mViewModel) {
        movieDetailsResource.observeResource(viewLifecycleOwner,
            doOnSuccess = ::showContent,
            doOnLoading = ::showLoading,
            doOnNetworkError = {showError(true)},
            doOnError = {showError(false)}
        )
    }
    private fun showContent(details: MovieDetailsUIModel)= with(details){
        progressBarPhotos.invisible()
        adapter.setData(photoUrls)
        recyclerViewPhotosList.visible()
        recyclerViewPhotosList.adapter=adapter
        txtTitle.text = title
        txtGenre.text = genres
        txtCast.text = cast
    }
    private fun showLoading() {
        txt_error.invisible()
        progressBarPhotos.visible()
    }

    private fun showError(isNetwork: Boolean) {
        if (isNetwork) txt_error.text = getString(R.string.movie_details_network_error_msg)
        else txt_error.text = getString(R.string.movie_details_error_msg)
        txt_error.visible()
    }



    companion object {
        private val MOVIE_TITLE_KEY: String = "MOVIE_TITLE_KEY"
        private val MOVIE_YEAR_KEY: String = "MOVIE_YEAR_KEY"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MovieDetailsFragment.
         */
        @JvmStatic
        fun newInstance(movieTitle: String, movieYear: Int) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_YEAR_KEY, movieYear)
                    putString(MOVIE_TITLE_KEY, movieTitle)
                }
            }
    }
}
