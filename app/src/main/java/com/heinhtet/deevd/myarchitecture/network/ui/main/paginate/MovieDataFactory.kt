package com.heinhtet.deevd.myarchitecture.network.ui.main.paginate

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.heinhtet.deevd.myarchitecture.network.data.DataService
import com.heinhtet.deevd.myarchitecture.network.model.response.Movie
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Hein Htet on 8/22/18.
 */
class MovieDataFactory(private val compositeDisposable: CompositeDisposable,
                       private val dataService: DataService)
    : DataSource.Factory<Long, Movie>() {
    val dataSourceLiveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Long, Movie> {
        val movieDataSource = MovieDataSource(dataService,compositeDisposable)
        dataSourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }

}
