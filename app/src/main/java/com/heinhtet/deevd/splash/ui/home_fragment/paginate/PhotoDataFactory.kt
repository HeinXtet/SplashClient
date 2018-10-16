package com.heinhtet.deevd.splash.ui.home_fragment.paginate

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.heinhtet.deevd.splash.data.DataService
import com.heinhtet.deevd.splash.model.response.Movie
import com.heinhtet.deevd.splash.model.response.PhotoModel
import com.heinhtet.deevd.splash.ui.main.paginate.MovieDataSource
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Hein Htet on 10/14/18.
 */
class PhotoDataFactory(private val compositeDisposable: CompositeDisposable,
                       private val dataService: DataService)
    : DataSource.Factory<Long, PhotoModel>() {
    val dataSourceLiveData = MutableLiveData<PhotoDataSource>()

    override fun create(): DataSource<Long, PhotoModel> {
        val movieDataSource = PhotoDataSource(compositeDisposable, dataService)
        dataSourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }

}
