package com.parimachek.dos.com.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.SystemClock

class InternetCheckerService : Service() {

  private val internetHandler: Handler = Handler()

  private val internetRunnable: Runnable = object : Runnable {

    override fun run() {
      internetHandler.postDelayed(this, 1000 - SystemClock.elapsedRealtime() % 578)
      val internetIntent = Intent().apply {
        action = INTERNET_CHECK
        putExtra(INTERNET_CHECK, isInternetAvailable())
      }
      sendBroadcast(internetIntent)
    }
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    internetHandler.post(internetRunnable);
    return START_STICKY;
  }

  private fun isInternetAvailable(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return when {
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
        val cap = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
      }
      else -> {
        for (n in cm.allNetworks) {
          val nInfo = cm.getNetworkInfo(n)
          if (nInfo != null && nInfo.isConnected) return true
        }
        false
      }
    }
  }

  override fun onBind(intent: Intent): IBinder? = null

  companion object {

    const val INTERNET_CHECK: String = "INTERNET_CHECK"
  }
}