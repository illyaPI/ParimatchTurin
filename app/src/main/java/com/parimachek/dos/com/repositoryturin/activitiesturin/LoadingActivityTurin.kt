package com.parimachek.dos.com.repositoryturin.activitiesturin

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.parimachek.dos.com.importantturin.applicationturin.WebViewApplicationTurin
import com.parimachek.dos.com.databinding.ActivityLoadingTurinBinding
import com.parimachek.dos.com.importantturin.RepositoryTurin
import com.parimachek.dos.com.repositoryturin.viewbindingturin.ViewBindingActivityTurin
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoadingActivityTurin : ViewBindingActivityTurin<ActivityLoadingTurinBinding>(ActivityLoadingTurinBinding::inflate) {

  @Inject lateinit var repositoryTurin: RepositoryTurin

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    injectTurin()
    if (repositoryTurin.lastBinomLinkTurin != null) {
      startActivity(Intent(this, WebViewActivityTurin::class.java))
      finish()
    }
    lifecycleScope.launchWhenCreated {
      delay(SETUP_DELAY_TURIN)
      setupFirebaseTurin()
    }
  }

  private fun setupFirebaseTurin() {
    val firebaseConfigTurin = FirebaseRemoteConfig.getInstance()
    firebaseConfigTurin.fetchAndActivate().addOnCompleteListener(this) {
      val whiteBaseTurin = firebaseConfigTurin.getString(WHITE_BASE_TURIN)
      val blackBaseTurin = firebaseConfigTurin.getString(BLACK_BASE_TURIN).ifEmpty { null }
      val defaultKeyTurin = firebaseConfigTurin.getString(DEFAULT_KEY_TURIN)

      repositoryTurin.whiteBaseTurin = whiteBaseTurin
      repositoryTurin.blackBaseTurin = blackBaseTurin
      repositoryTurin.defaultKeyTurin = defaultKeyTurin

      startActivity(Intent(this, WebViewActivityTurin::class.java))
      finish()
    }
  }

  private fun injectTurin(): Unit = (application as WebViewApplicationTurin).appComponentTurin.injectTurin(this)

  companion object {

    private const val TAG_TURIN: String = "LoadingActivity"

    private const val WHITE_BASE_TURIN: String = "doswhitebage"
    private const val BLACK_BASE_TURIN: String = "dosblackpage"
    private const val DEFAULT_KEY_TURIN: String = "dosdefaultkey"

    private const val SETUP_DELAY_TURIN: Long = 5000L
  }
}