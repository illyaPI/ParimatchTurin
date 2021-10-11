package com.parimachek.dos.com.servicesturin

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.SystemClock

class InternetCheckerServiceTurin : Service() {

  private val internetHandlerTurin: Handler = Handler()

  private val internetRunnableTurin: Runnable = object : Runnable {

    override fun run() {
      internetHandlerTurin.postDelayed(this, 1000 - SystemClock.elapsedRealtime() % 578)
      val internetIntentTurin = Intent().apply {
        action = INTERNET_CHECK_TURIN
        putExtra(INTERNET_CHECK_TURIN, isInternetAvailableTurin())
      }
      sendBroadcast(internetIntentTurin)
    }
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    internetHandlerTurin.post(internetRunnableTurin);
    return START_STICKY;
  }

  private fun isInternetAvailableTurin(): Boolean {
    val cmTurin = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return when {
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
        val capTurin = cmTurin.getNetworkCapabilities(cmTurin.activeNetwork) ?: return false
        return capTurin.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
      }
      else -> {
        for (nTurin in cmTurin.allNetworks) {
          val nInfoTurin = cmTurin.getNetworkInfo(nTurin)
          if (nInfoTurin != null && nInfoTurin.isConnected) return true
        }
        false
      }
    }
  }

  override fun onBind(intent: Intent): IBinder? = null

  companion object {

    const val INTERNET_CHECK_TURIN: String = "INTERNET_CHECK_TURIN"
  }
}