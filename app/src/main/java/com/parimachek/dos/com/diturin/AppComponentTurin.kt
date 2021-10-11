package com.parimachek.dos.com.diturin

import com.parimachek.dos.com.repositoryturin.activitiesturin.LoadingActivityTurin
import com.parimachek.dos.com.repositoryturin.activitiesturin.WebViewActivityTurin
import com.parimachek.dos.com.importantturin.applicationturin.WebViewApplicationTurin
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModuleTurin::class])
interface AppComponentTurin {

  fun injectTurin(applicationTurin: WebViewApplicationTurin)
  fun injectTurin(activityTurin: LoadingActivityTurin)
  fun injectTurin(activityTurin: WebViewActivityTurin)
}