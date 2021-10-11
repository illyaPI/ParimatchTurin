package com.parimachek.dos.com.diturin.webviewturin

import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClientTurin(
  private val intentUrlsTurin: Array<String>,
  private val prohibitedUrlsTurin: Array<String>,
) : WebViewClient() {

  override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
    val urlTurin = request.url.toString()
    return when {
      intentUrlsTurin.find { urlTurin.startsWith(it) } != null -> {
        view.context.startActivity(Intent(Intent.ACTION_VIEW, request.url))
        true
      }
      prohibitedUrlsTurin.find { urlTurin.contains(it) } != null -> {
        true
      }
      else -> false
    }
  }

  companion object {

    private const val TAG_TURIN: String = "CustomWebViewClient"
  }
}