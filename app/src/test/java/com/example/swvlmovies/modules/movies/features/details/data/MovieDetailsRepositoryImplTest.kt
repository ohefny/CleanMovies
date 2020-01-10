package com.example.swvlmovies.modules.movies.features.details.data

import com.example.swvlmovies.modules.common.data.local.MoviesDAO
import com.example.swvlmovies.modules.common.data.local.models.MovieDTO
import com.example.swvlmovies.modules.movies.features.details.data.source.local.PhotosLocalDS
import com.example.swvlmovies.modules.movies.features.details.data.source.remote.MoviePhotosRemoteDS
import com.example.swvlmovies.modules.movies.features.details.domain.entity.MovieIDParam
import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.internal.verification.Times

class MovieDetailsRepositoryImplTest {
    @Mock
    lateinit var moviesDAO: MoviesDAO
    @Mock
    lateinit var photosLocal: PhotosLocalDS
    @Mock
    lateinit var movieRemote: MoviePhotosRemoteDS
    @InjectMocks
    lateinit var repository: MovieDetailsRepositoryImpl
    private val movie = Movie(cast = emptyList(), genres = emptyList(), rating = 2, title = "title", year = 2020)
    private val moviedto = MovieDTO(cast = emptyList(), genres = emptyList(), rating = 2, title = "title", year = 2020)

    private val genres:List<String> = mutableListOf("action","Action","war","drama","biography")
    private val movieID = MovieIDParam(title = "title", year = 2020)
    private val photosList = listOf("photo1","photo2","photo3","photo4")
    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }
    @Test
    fun `when repository_getMovieFromID called then MoviesDAO_getMovieByNameAndYear is called with the same values`() {
        whenever(moviesDAO.getMovieByNameAndYear(any(), any())).thenReturn(Single.just(moviedto))
        repository.getMovieFromID(movieID).test()
        verify(moviesDAO).getMovieByNameAndYear(movieID.year,movieID.title)

    }
    @Test
    fun `when repository_getMovieFromID called then returned DTO transformed to entity with the same values`() {
        whenever(moviesDAO.getMovieByNameAndYear(any(), any())).thenReturn(Single.just(moviedto))
        repository.getMovieFromID(movieID).test().assertValue {
            movie.title==it.title && movie.year==it.year
        }
    }
    @Test
    fun `given no cache photos when repository_getMoviePhotos called then MoviePhotosRemoteDS_getPhotos is called`() {
        whenever(photosLocal.getMoviePhotos(any())).thenReturn(Flowable.empty<String>())
        whenever(movieRemote.getMoviePhotos(any())).thenReturn(Flowable.fromIterable(photosList))
        repository.getMoviePhotos(movie).test()
        verify(movieRemote).getMoviePhotos(any())
    }
    @Test
    fun `given cached photos when repository_getMoviePhotos called then MoviePhotosRemoteDS_getPhotos is not called`() {
        whenever(photosLocal.getMoviePhotos(any())).thenReturn(Flowable.fromIterable(photosList))
        repository.getMoviePhotos(movie).test()
        verify(movieRemote, Times(0)).getMoviePhotos(any())
    }
    @Test
    fun `given no cache photos when repository_getMoviePhotos called then emitted remote photos is cached `() {
        whenever(photosLocal.getMoviePhotos(any())).thenReturn(Flowable.empty<String>())
        whenever(movieRemote.getMoviePhotos(any())).thenReturn(Flowable.fromIterable(photosList))
        repository.getMoviePhotos(movie).test()
        verify(photosLocal).insertMoviePhotos(any(), any())
    }
}