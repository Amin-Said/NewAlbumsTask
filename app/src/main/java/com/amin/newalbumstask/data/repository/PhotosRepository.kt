package com.amin.newalbumstask.data.repository

import com.amin.newalbumstask.data.model.Photo
import com.amin.newalbumstask.data.network.ApiError
import io.reactivex.disposables.Disposable

interface  PhotosRepository {
    fun getPhotos(albumID:String, success: (MutableList<Photo>?) -> Unit,
                  failure: (ApiError) -> Unit,
                  terminate: () -> Unit): Disposable

}