package com.amin.newalbumstask.di.builder

import androidx.lifecycle.ViewModel
import com.amin.newalbumstask.di.qualifier.ViewModelKey
import com.amin.newalbumstask.ui.albums.AlbumDetailsViewModel
import com.amin.newalbumstask.ui.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlbumDetailsViewModel::class)
    abstract fun bindAlbumDetailsViewModel(albumDetailsViewModel: AlbumDetailsViewModel): ViewModel

}