package com.amin.newalbumstask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amin.newalbumstask.R
import com.amin.newalbumstask.core.MyApp
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyApp).androidInjector.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

}