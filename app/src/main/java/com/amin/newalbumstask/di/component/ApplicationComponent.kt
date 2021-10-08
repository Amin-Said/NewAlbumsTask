package com.amin.newalbumstask.di.component

import android.app.Application
import com.amin.newalbumstask.core.MyApp
import com.amin.newalbumstask.di.builder.ActivityBuilder
import com.amin.newalbumstask.di.module.ContextModule
import com.amin.newalbumstask.di.module.NetworkModule
import com.amin.newalbumstask.di.module.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        RepositoryModule::class,
        ActivityBuilder::class,
        ContextModule::class,
        NetworkModule::class]
)
interface ApplicationComponent : AndroidInjector<MyApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}