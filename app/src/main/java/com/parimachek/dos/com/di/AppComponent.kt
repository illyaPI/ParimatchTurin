package com.parimachek.dos.com.di

import com.parimachek.dos.com.activities.LoadingActivity
import com.parimachek.dos.com.activities.WebViewActivity
import com.parimachek.dos.com.application.WebViewApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface AppComponent {

  fun inject(application: WebViewApplication)
  fun inject(activity: LoadingActivity)
  fun inject(activity: WebViewActivity)
}