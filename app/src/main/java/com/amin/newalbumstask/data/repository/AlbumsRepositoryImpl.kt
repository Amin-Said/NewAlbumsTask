package com.amin.newalbumstask.data.repository

import android.util.Log
import com.amin.newalbumstask.data.model.Album
import com.amin.newalbumstask.data.network.ApiError
import com.amin.newalbumstask.data.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AlbumsRepositoryImpl  @Inject constructor(
    private val apiService: ApiService
) : AlbumsRepository {
    override fun getAlbums(
        userID: String,
        success: (MutableList<Album>?) -> Unit,
        failure: (ApiError) -> Unit,
        terminate: () -> Unit
    ): Disposable {
        return apiService.getAlbums(userID).subscribeOn(Schedulers.io())
            .map { albumsResponse ->
                albumsResponse
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                success(result.toMutableList())
            }) { error ->
                Log.e(
                    TAG,"AlbumsRepositoryImpl :: "+
                    error.message.toString()
                )
            }
    }
}