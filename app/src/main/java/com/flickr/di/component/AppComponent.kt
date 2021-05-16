package com.flickr.di.component

import com.flickr.application.FlickrApp
import com.flickr.di.builder.ActivityBuilder
import com.flickr.di.module.AppModule
import com.flickr.di.module.NetworkModule
import com.flickr.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(
    modules = [
        //Dagger support
        AndroidInjectionModule::class,
        //App
        AppModule::class,
        //Activity
        ActivityBuilder::class,
        //ViewModel
        ViewModelModule::class,
        //Network
        NetworkModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<FlickrApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: FlickrApp): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: FlickrApp)
}