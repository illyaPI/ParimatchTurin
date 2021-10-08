package com.parimachek.dos.com.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
      val whiteBase = firebaseConfig.getString(WHITE_BASE)
      val blackBase = firebaseConfig.getString(BLACK_BASE).ifEmpty { null }
      val defaultKey = firebaseConfig.getString(DEFAULT_KEY)

      repository.whiteBase = whiteBase
      repository.blackBase = blackBase
      repository.defaultKey = defaultKey

      Log.i(TAG, "setupFirebase: whiteBase: $whiteBase")
      Log.i(TAG, "setupFirebase: blackBase: $blackBase")
      Log.i(TAG, "setupFirebase: defaultKey: $defaultKey")

      startActivity(Intent(this, WebViewActivity::class.java))
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