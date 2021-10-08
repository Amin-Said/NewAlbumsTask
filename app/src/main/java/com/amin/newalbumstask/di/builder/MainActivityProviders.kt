package com.amin.newalbumstask.di.builder

import com.amin.newalbumstask.ui.albums.AlbumDetailsFragment
import com.amin.newalbumstask.ui.albums.PhotoViewerFragment
import com.amin.newalbumstask.ui.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainActivityProviders {

    @ContributesAndroidInjector
    abstract fun provideProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun provideAlbumDetailsFragment(): AlbumDetailsFragment

    @ContributesAndroidInjector
    abstract fun providePhotoViewerFragment(): PhotoViewerFragment


}