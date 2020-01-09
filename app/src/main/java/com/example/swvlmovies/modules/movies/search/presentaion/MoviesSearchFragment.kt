package com.example.swvlmovies.modules.movies.search.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.swvlmovies.R
import com.example.swvlmovies.core.presentation.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MoviesSearchFragment : DaggerFragment() {
    @Inject
    lateinit var mViewModelFactory: ViewModelFactory
    private lateinit var mRootView: View
    private val mViewModel by lazy {
        ViewModelProviders.of(this, mViewModelFactory)
            .get(MoviesSearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadDataOnce()
        initObservation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.fragment_movies_search, container,false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun loadDataOnce() {

    }

    private fun initObservation() = with(mViewModel) {

    }

    private fun initViews() = with(mRootView) {

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
