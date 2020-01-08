package com.example.swvlmovies.modules.splash.data.source

import android.content.res.AssetManager
import com.example.swvlmovies.modules.splash.data.MoviesPreparationRepositoryImpl
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.InputStream

class AssetsDSTest{
    @Mock
    lateinit var assetsManager: AssetManager
    lateinit var assetsDS:AssetsDS
    private val movieStr = """{
    "movies": [
        {
            "title": "(500) Days of Summer",
            "year": 2009,
            "cast": [
                "Joseph Gordon-Levitt",
                "Zooey Deschanel"
            ],
            "genres": [
                "Romance",
                "Comedy"
            ],
            "rating": 1
        }]}
        """
    val assetsInputStream: InputStream by lazy { movieStr.byteInputStream() }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        assetsDS = spy(AssetsDS(assetManager=assetsManager,gson=Gson()))
        whenever(assetsManager.open(ArgumentMatchers.anyString())).thenReturn(assetsInputStream)
        assetsDS= AssetsDS(assetsManager, Gson())
    }
    @Test
    fun `when AssetsDs's loadMovies is called then assetsManager's open is called`() {
        assetsDS.loadMovies().test()
        verify(assetsManager).open(any())
    }
    @Test
    fun `given movieStr when AssetsDs's loadMovies is called then one movie is returned`() {
        assetsDS.loadMovies().test().assertValue{ it.size==1 }
    }
    @Test
    fun `given movieStr when AssetsDs's loadMovies is called then movie with the same data is returned`() {
        val movie = assetsDS.loadMovies().test().values()[0][0]
        assert(movie.rating==1)
        assert(movie.year==2009)
    }

}