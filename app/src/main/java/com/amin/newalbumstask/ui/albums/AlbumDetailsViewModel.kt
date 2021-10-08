package com.amin.newalbumstask.ui.albums

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.amin.newalbumstask.core.BaseViewModel
import com.amin.newalbumstask.data.model.Photo
import com.amin.newalbumstask.data.network.ApiError
import com.amin.newalbumstask.data.repository.PhotosRepository
import javax.inject.Inject

const val TAG = "AlbumDetailsViewModel"

class AlbumDetailsViewModel @Inject constructor(
    private val photosRepository: PhotosRepository
) : BaseViewModel() {

    val photosData: MutableLiveData<MutableList<Photo>>
            by lazy { MutableLiveData<MutableList<Photo>>() }

    val error: MutableLiveData<ApiError> by lazy { MutableLiveData<ApiError>() }

    fun getPhotosData(albumID: String) {
        Log.d(TAG, "getPhotosData() called")
        photosRepository.getPhotos(albumID,
            { photos ->
                Log.d(TAG, "getPhotosData.success() called with size : ${photos?.size}")
                photosData.postValue(photos)

            }, {
                Log.d(TAG, "getPhotosData.error() called with: $it")
                error.value = it
            }, {
                Log.d(TAG, "getPhotosData.terminate() called")
            }
        ).also { compositeDisposable.add(it) }
    }

}