package com.amin.newalbumstask.data.repository

import android.util.Log
import com.amin.newalbumstask.data.model.Photo
import com.amin.newalbumstask.data.network.ApiError
import com.amin.newalbumstask.data.network.ApiService
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class PhotosRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PhotosRepository {
    override fun getPhotos(
        albumID: String, success: (MutableList<Photo>?) -> Unit,
        failure: (ApiError) -> Unit,
        terminate: () -> Unit
    ): Disposable {

        return apiService.getPhotos(albumID).subscribeOn(Schedulers.io())
            .map { photosResponse ->
                photosResponse
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                success(result.toMutableList())
            }) { error ->
                Log.e(
                    TAG,"PhotosRepositoryImpl :: "+
                    error.message.toString()
                )
            }

    }




}