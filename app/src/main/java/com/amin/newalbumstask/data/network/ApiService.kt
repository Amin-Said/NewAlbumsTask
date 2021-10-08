package com.amin.newalbumstask.data.network

import com.amin.newalbumstask.data.model.AlbumsResponse
import com.amin.newalbumstask.data.model.PhotosResponse
import com.amin.newalbumstask.data.model.UsersResponse
import com.amin.newalbumstask.utils.Constants
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET(Constants.BASE_USERS_RETURN)
    fun getUsers(): Single<UsersResponse>

    @GET(Constants.BASE_ALBUMS_RETURN)
    fun getAlbums(@Query(Constants.USER_ID_PARAM) userID: String): Single<AlbumsResponse>

    @GET(Constants.BASE_PHOTOS_RETURN)
    fun getPhotos(@Query(Constants.ALBUM_ID_PARAM) albumID: String): Single<PhotosResponse>
}