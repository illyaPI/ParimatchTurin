package com.parimachek.dos.com.activities

import android.os.Bundle
import com.parimachek.dos.com.application.WebViewApplication
import com.parimachek.dos.com.databinding.ActivityWebViewBinding
import com.parimachek.dos.com.repository.Repository
import com.parimachek.dos.com.util.ViewBindingActivity
import javax.inject.Inject

class WebViewActivity : ViewBindingActivity<ActivityWebViewBinding>(ActivityWebViewBinding::inflate) {

  @Inject lateinit var repository: Repository

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    inject()
  }

  private fun inject(): Unit = (application as WebViewApplication).appComponent.inject(this)

  companion object {

    private const val TAG: String = "MainActivity"
  }
}