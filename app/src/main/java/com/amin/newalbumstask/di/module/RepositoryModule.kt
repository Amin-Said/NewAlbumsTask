package com.amin.newalbumstask.di.module

import com.amin.newalbumstask.data.network.ApiService
import com.amin.newalbumstask.data.repository.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUsersRepository(apiService: ApiService): UsersRepository {
        return UsersRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideAlbumsRepository(apiService: ApiService): AlbumsRepository {
        return AlbumsRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun providePhotosRepository(apiService: ApiService): PhotosRepository {
        return PhotosRepositoryImpl(apiService)
    }
}