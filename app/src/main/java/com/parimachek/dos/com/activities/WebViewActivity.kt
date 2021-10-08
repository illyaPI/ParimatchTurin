package com.parimachek.dos.com.activities

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.webkit.ValueCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.parimachek.dos.com.BuildConfig
import com.parimachek.dos.com.application.WebViewApplication
import com.parimachek.dos.com.databinding.ActivityWebViewBinding
import com.parimachek.dos.com.models.AFStatus
import com.parimachek.dos.com.repository.Repository
import com.parimachek.dos.com.services.InternetCheckerService
import com.parimachek.dos.com.util.ViewBindingActivity
import com.parimachek.dos.com.util.decodeFromBase64
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WebViewActivity : ViewBindingActivity<ActivityWebViewBinding>(ActivityWebViewBinding::inflate) {

  @Inject lateinit var repository: Repository
  private lateinit var builtBinomLink: String

  private lateinit var intentFilter: IntentFilter
  private var filePathCallback: ValueCallback<Array<Uri>>? = null
  private var uriView: Uri = Uri.EMPTY

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    inject()
    setupBinomLink()
    setupWebView()
    setupService()
  }

  private fun setupBinomLink() {
    val blackBase = repository.blackBase
    if (blackBase != null) {
      val binomLink = repository.binomLink
      val defaultKey = repository.defaultKey
      if (binomLink != null) {
        when (binomLink.afStatus) {
          AFStatus.ORGANIC -> {
            binomLink.base = blackBase
            binomLink.key = defaultKey
          }
          AFStatus.NON_ORGANIC -> {
            binomLink.base = blackBase
            val key = binomLink.key
            if (key == null || key.length != 20) {
              binomLink.key = defaultKey
              binomLink.sub7 = "Default"
            }
          }
        }
        repository.binomLink = binomLink
        builtBinomLink = binomLink.toLink()
      } else {
        val application = application as WebViewApplication
        val sub10 =
          "${application.appsFlyerId}||${application.googleAdvertisingId}||${BuildConfig.APPS_FLYER_KEY_TURIN.decodeFromBase64()}"
        builtBinomLink = "$blackBase?key=$defaultKey&bundle=${BuildConfig.APPLICATION_ID}&sub7=Organic&sub10=$sub10"
      }
    } else {
      builtBinomLink = repository.whiteBase
    }
    Log.i(TAG, "setupBinomLink: binomLink: ${repository.binomLink}")
    Log.i(TAG, "setupBinomLink: builtBinomLink: $builtBinomLink")
  }

  private fun setupWebView() {
  }

  override fun onBackPressed() {
    if (bnd.webView.canGoBack()) bnd.webView.goBack()
    else super.onBackPressed()
  }

  private fun setupService() {
    intentFilter = IntentFilter().apply {
      addAction(InternetCheckerService.INTERNET_CHECK)
    }
    startService(Intent(this, InternetCheckerService::class.java))
  }

  private val broadcastReceiver = object : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
      if (intent.action == InternetCheckerService.INTERNET_CHECK) {
        if (intent.getBooleanExtra(InternetCheckerService.INTERNET_CHECK, true)) {
          bnd.layoutWebView.visibility = View.VISIBLE
          bnd.layoutNoInternet.visibility = View.GONE
        } else {
          bnd.layoutWebView.visibility = View.GONE
          bnd.layoutNoInternet.visibility = View.VISIBLE
        }
      }
    }
  }

  override fun onPause() {
    super.onPause()
    unregisterReceiver(broadcastReceiver)
  }

  override fun onResume() {
    super.onResume()
    registerReceiver(broadcastReceiver, intentFilter)
  }

  private val startActivityForImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    if (filePathCallback == null) return@registerForActivityResult
    val uriResult = if (it.data == null || it.resultCode != Activity.RESULT_OK) null else it.data!!.data
    if (uriResult != null && filePathCallback != null) {
      filePathCallback!!.onReceiveValue(arrayOf(uriResult))
    } else if (filePathCallback != null) {
      filePathCallback!!.onReceiveValue(arrayOf(uriView))
    }
    filePathCallback = null
  }

  private fun createTempFile(): File {
    val date = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val fileDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile("TMP${date}", ".jpg", fileDir)

  }

  private fun inject(): Unit = (application as WebViewApplication).appComponent.inject(this)

  companion object {

    private const val TAG: String = "WebViewActivity"
  }
}