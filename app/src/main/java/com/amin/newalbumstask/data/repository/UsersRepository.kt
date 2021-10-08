package com.amin.newalbumstask.data.repository

import com.amin.newalbumstask.data.model.User
import com.amin.newalbumstask.data.network.ApiError
import io.reactivex.disposables.Disposable

interface UsersRepository {
    fun getUsers(success: (MutableList<User>?) -> Unit,
                 failure: (ApiError) -> Unit,
                 terminate: () -> Unit): Disposable
}