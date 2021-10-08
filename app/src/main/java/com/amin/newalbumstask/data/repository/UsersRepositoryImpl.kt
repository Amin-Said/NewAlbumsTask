package com.amin.newalbumstask.data.repository

import android.util.Log
import com.amin.newalbumstask.data.model.User
import com.amin.newalbumstask.data.network.ApiError
import com.amin.newalbumstask.data.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

const val TAG = "RepositoryImpl"

class UsersRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UsersRepository {
    override fun getUsers(
        success: (MutableList<User>?) -> Unit,
        failure: (ApiError) -> Unit,
        terminate: () -> Unit
    ): Disposable {
        return apiService.getUsers().subscribeOn(Schedulers.io())
            .map { usersResponse ->
                usersResponse
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                success(result.toMutableList())
            }) { error ->
                Log.e(
                    TAG,"UsersRepositoryImpl :: "+
                    error.message.toString()
                )
            }
    }
}