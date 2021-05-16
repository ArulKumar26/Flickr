package com.flickr.application

import com.flickr.di.component.DaggerAppComponent
import com.flickr.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class FlickrApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerAppComponent.builder()
            .application(this)
            .appModule(AppModule(this))
            .build()
        component.inject(this)
        return component
    }
}