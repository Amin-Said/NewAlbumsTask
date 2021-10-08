package com.amin.newalbumstask.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.amin.newalbumstask.core.BaseViewModel
import com.amin.newalbumstask.data.model.Album
import com.amin.newalbumstask.data.model.User
import com.amin.newalbumstask.data.network.ApiError
import com.amin.newalbumstask.data.repository.AlbumsRepository
import com.amin.newalbumstask.data.repository.UsersRepository
import javax.inject.Inject

const val TAG = "ProfileViewModel"

class ProfileViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val albumsRepository: AlbumsRepository
) : BaseViewModel() {

    val albumsData: MutableLiveData<MutableList<Album>>
            by lazy { MutableLiveData<MutableList<Album>>() }

    val currentUser: MutableLiveData<User>
            by lazy { MutableLiveData<User>() }

    val error: MutableLiveData<ApiError> by lazy { MutableLiveData<ApiError>() }

    init {
        Log.d(TAG, "init block")
        getUsersData()
    }


    private fun getAlbumsData(userID:String) {
        Log.d(TAG, "getAlbumsData() called")
        albumsRepository.getAlbums(userID,
            { albums ->
                Log.d(TAG, "getAlbumsData.success() called with size : ${albums?.size}")
                albumsData.postValue(albums)

            }, {
                Log.d(TAG, "getAlbumsData.error() called with: $it")
                error.value = it
            }, {
                Log.d(TAG, "getAlbumsData.terminate() called")
            }
        ).also { compositeDisposable.add(it) }


    }

    private fun getUsersData() {
        Log.d(TAG, "getUsersData() called")
        usersRepository.getUsers(
            { users ->
                Log.d(TAG, "getUsersData.success() called with size : ${users?.size}")
                //  usersData.postValue(users)
                val randomUser = users?.random()
                currentUser.postValue(randomUser)
                getAlbumsData(randomUser?.id.toString())

            }, {
                Log.d(TAG, "getUsersData.error() called with: $it")
                error.value = it
            }, {
                Log.d(TAG, "getUsersData.terminate() called")
            }
        ).also { compositeDisposable.add(it) }


    }

}
