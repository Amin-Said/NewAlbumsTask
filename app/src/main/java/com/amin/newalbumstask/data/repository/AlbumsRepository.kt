package com.amin.newalbumstask.data.repository

import com.amin.newalbumstask.data.model.Album
import com.amin.newalbumstask.data.network.ApiError
import io.reactivex.disposables.Disposable

interface AlbumsRepository {
    fun getAlbums(userID:String, success: (MutableList<Album>?) -> Unit,
                  failure: (ApiError) -> Unit,
                  terminate: () -> Unit): Disposable
}