package com.amin.newalbumstask.di.builder

import androidx.lifecycle.ViewModelProvider
import com.amin.newalbumstask.core.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module(includes = [ViewModelModule::class])
abstract class ViewModelBuilder {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory)
            : ViewModelProvider.Factory
}