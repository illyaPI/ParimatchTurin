package com.parimachek.dos.com.activities

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.parimachek.dos.com.application.WebViewApplication
import com.parimachek.dos.com.databinding.ActivityLoadingBinding
import com.parimachek.dos.com.repository.Repository
import com.parimachek.dos.com.util.ViewBindingActivity
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoadingActivity : ViewBindingActivity<ActivityLoadingBinding>(ActivityLoadingBinding::inflate) {

  @Inject lateinit var repository: Repository

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    inject()
    lifecycleScope.launchWhenCreated {
      delay(SETUP_DELAY)
      setupFirebase()
    }
  }

  private fun setupFirebase() {
    val firebaseConfig = FirebaseRemoteConfig.getInstance()
    firebaseConfig.fetchAndActivate().addOnCompleteListener(this) {
    }
  }

  private fun inject(): Unit = (application as WebViewApplication).appComponent.inject(this)

  companion object {

    private const val TAG: String = "LoadingActivity"

    private const val WHITE_BASE: String = "doswhitebage"
    private const val BLACK_BASE: String = "dosblackpage"
    private const val DEFAULT_KEY: String = "dosdefaultkey"

    private const val SETUP_DELAY: Long = 5000L
  }
}