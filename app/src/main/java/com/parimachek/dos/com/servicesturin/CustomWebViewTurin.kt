package com.parimachek.dos.com.servicesturin

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView

class CustomWebViewTurin(context: Context, attrs: AttributeSet) : WebView(context, attrs) {

  init {
    scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

    settings.apply {
      javaScriptEnabled = true
      domStorageEnabled = true
      cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
      defaultTextEncodingName = "utf-8"
      useWideViewPort = true
      loadWithOverviewMode = true
      mediaPlaybackRequiresUserGesture = false
      displayZoomControls = false
      builtInZoomControls = true
    }
  }
}