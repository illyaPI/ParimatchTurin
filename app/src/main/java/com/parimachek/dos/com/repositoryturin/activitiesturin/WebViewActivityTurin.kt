package com.parimachek.dos.com.repositoryturin.activitiesturin

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.parimachek.dos.com.BuildConfig
import com.parimachek.dos.com.R
import com.parimachek.dos.com.importantturin.applicationturin.WebViewApplicationTurin
import com.parimachek.dos.com.databinding.ActivityWebViewTurinBinding
import com.parimachek.dos.com.importantturin.modelsturin.AFStatusTurin
import com.parimachek.dos.com.importantturin.RepositoryTurin
import com.parimachek.dos.com.servicesturin.InternetCheckerServiceTurin
import com.parimachek.dos.com.repositoryturin.viewbindingturin.ViewBindingActivityTurin
import com.parimachek.dos.com.servicesturin.utilturin.checkForPermissionsTurin
import com.parimachek.dos.com.servicesturin.utilturin.decodeFromBase64Turin
import com.parimachek.dos.com.diturin.webviewturin.CustomWebViewClientTurin
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WebViewActivityTurin : ViewBindingActivityTurin<ActivityWebViewTurinBinding>(ActivityWebViewTurinBinding::inflate) {

  @Inject lateinit var repositoryTurin: RepositoryTurin
  private lateinit var builtBinomLinkTurin: String

  private lateinit var intentFilterTurin: IntentFilter
  private var filePathCallbackTurin: ValueCallback<Array<Uri>>? = null
  private var uriViewTurin: Uri = Uri.EMPTY

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    injectTurin()
    setupBinomLinkTurin()
    setupWebViewTurin()
    setupServiceTurin()
  }

  private fun setupBinomLinkTurin() {
    val blackBaseTurin = repositoryTurin.blackBaseTurin
    if (blackBaseTurin != null) {
      val binomLinkTurin = repositoryTurin.binomLinkTurin
      val defaultKeyTurin = repositoryTurin.defaultKeyTurin
      if (binomLinkTurin != null) {
        when (binomLinkTurin.afStatusTurin) {
          AFStatusTurin.ORGANIC_TURIN -> {
            binomLinkTurin.baseTurin = blackBaseTurin
            binomLinkTurin.keyTurin = defaultKeyTurin
          }
          AFStatusTurin.NON_ORGANIC_TURIN -> {
            binomLinkTurin.baseTurin = blackBaseTurin
            val keyTurin = binomLinkTurin.keyTurin
            if (keyTurin == null || keyTurin.length != 20) {
              binomLinkTurin.keyTurin = defaultKeyTurin
              binomLinkTurin.sub7Turin = "Default"
            }
          }
        }
        repositoryTurin.binomLinkTurin = binomLinkTurin
        builtBinomLinkTurin = binomLinkTurin.toLinkTurin()
      } else {
        val applicationTurin = application as WebViewApplicationTurin
        val sub10Turin =
          "${applicationTurin.appsFlyerIdTurin}||${applicationTurin.googleAdvertisingIdTurin}||${BuildConfig.APPS_FLYER_KEY_TURIN.decodeFromBase64Turin()}"
        builtBinomLinkTurin = "$blackBaseTurin?key=$defaultKeyTurin&bundle=${BuildConfig.APPLICATION_ID}&sub7=Organic&sub10=$sub10Turin"
      }
    } else {
      builtBinomLinkTurin = repositoryTurin.whiteBaseTurin
    }
  }

  private fun setupWebViewTurin() {
    val cookiesManTurin: CookieManager = CookieManager.getInstance()
    CookieManager.setAcceptFileSchemeCookies(true)
    cookiesManTurin.setAcceptThirdPartyCookies(bndTurin.webViewTurin, true)

    bndTurin.webViewTurin.webViewClient = CustomWebViewClientTurin(
      resources.getStringArray(R.array.intent_urls),
      resources.getStringArray(R.array.prohibited_urls),
    )

    bndTurin.webViewTurin.webChromeClient = object : WebChromeClient() {

      override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?,
      ): Boolean {
        checkForPermissionsTurin()
        this@WebViewActivityTurin.filePathCallbackTurin = filePathCallback
        val capIntentTurin = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (capIntentTurin.resolveActivity(packageManager) != null) {
          val providedFileTurin = createTempFileTurin()
          uriViewTurin = FileProvider.getUriForFile(
            this@WebViewActivityTurin,
            "${application.packageName}.provider",
            providedFileTurin
          )
          capIntentTurin.apply {
            putExtra(MediaStore.EXTRA_OUTPUT, uriViewTurin)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
          }
          val actionIntentTurin = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
          }
          val intentChooserTurin = Intent(Intent.ACTION_CHOOSER).apply {
            putExtra(Intent.EXTRA_INTENT, capIntentTurin)
            putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(actionIntentTurin))
          }
          startActivityForImageTurin.launch(intentChooserTurin)
          return true
        }
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
      }
    }

    bndTurin.webViewTurin.loadUrl(repositoryTurin.lastBinomLinkTurin ?: builtBinomLinkTurin)

    bndTurin.layoutWebViewTurin.setOnRefreshListener {
      bndTurin.webViewTurin.loadUrl(builtBinomLinkTurin)
      bndTurin.layoutWebViewTurin.isRefreshing = false
    }
  }

  override fun onBackPressed() {
    if (bndTurin.webViewTurin.canGoBack()) bndTurin.webViewTurin.goBack()
    else super.onBackPressed()
  }

  private fun setupServiceTurin() {
    intentFilterTurin = IntentFilter().apply {
      addAction(InternetCheckerServiceTurin.INTERNET_CHECK_TURIN)
    }
    startService(Intent(this, InternetCheckerServiceTurin::class.java))
  }

  private val broadcastReceiverTurin = object : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
      if (intent.action == InternetCheckerServiceTurin.INTERNET_CHECK_TURIN) {
        if (intent.getBooleanExtra(InternetCheckerServiceTurin.INTERNET_CHECK_TURIN, true)) {
          bndTurin.layoutWebViewTurin.visibility = View.VISIBLE
          bndTurin.layoutNoInternetTurin.visibility = View.GONE
        } else {
          bndTurin.layoutWebViewTurin.visibility = View.GONE
          bndTurin.layoutNoInternetTurin.visibility = View.VISIBLE
        }
      }
    }
  }

  override fun onPause() {
    super.onPause()
    unregisterReceiver(broadcastReceiverTurin)
  }

  override fun onStop() {
    super.onStop()
    repositoryTurin.lastBinomLinkTurin = bndTurin.webViewTurin.url
  }

  override fun onResume() {
    super.onResume()
    registerReceiver(broadcastReceiverTurin, intentFilterTurin)
  }

  private val startActivityForImageTurin = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    if (filePathCallbackTurin == null) return@registerForActivityResult
    val uriResultTurin = if (it.data == null || it.resultCode != Activity.RESULT_OK) null else it.data!!.data
    filePathCallbackTurin!!.onReceiveValue(arrayOf(uriResultTurin ?: uriViewTurin))
    filePathCallbackTurin = null
  }

  private fun createTempFileTurin(): File {
    val dateTurin = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val fileDirTurin = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile("TMP${dateTurin}_TURIN", ".jpg", fileDirTurin)
  }

  private fun injectTurin(): Unit = (application as WebViewApplicationTurin).appComponentTurin.injectTurin(this)

  companion object {

    private const val TAG_TURIN: String = "WebViewActivity"
  }
}