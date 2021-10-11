package com.parimachek.dos.com.importantturin.applicationturin

import android.app.Application
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import com.parimachek.dos.com.BuildConfig
import com.parimachek.dos.com.diturin.AppComponentTurin
import com.parimachek.dos.com.diturin.DaggerAppComponentTurin
import com.parimachek.dos.com.diturin.RepositoryModuleTurin
import com.parimachek.dos.com.importantturin.modelsturin.AFStatusTurin
import com.parimachek.dos.com.importantturin.modelsturin.BinomLinkTurin
import com.parimachek.dos.com.importantturin.RepositoryTurin
import com.parimachek.dos.com.servicesturin.utilturin.decodeFromBase64Turin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class WebViewApplicationTurin : Application() {

  lateinit var appComponentTurin: AppComponentTurin
  @Inject lateinit var repositoryTurin: RepositoryTurin
  lateinit var googleAdvertisingIdTurin: String
  lateinit var appsFlyerIdTurin: String

  override fun onCreate() {
    super.onCreate()

    setupDITurin()
    injectTurin()
    setupGoogleAdvertisingIdTurin()
    setupOneSignalTurin()
    setupAppsFlyerTurin()
  }

  private fun setupGoogleAdvertisingIdTurin() {
    MobileAds.initialize(applicationContext)
    GlobalScope.launch(Dispatchers.IO) {
      googleAdvertisingIdTurin = AdvertisingIdClient.getAdvertisingIdInfo(applicationContext).id
    }
  }

  private fun setupOneSignalTurin() {
    OneSignal.initWithContext(applicationContext)
    OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
    OneSignal.setAppId(BuildConfig.ONE_SIGNAL_KEY_TURIN.decodeFromBase64Turin())
  }

  private fun setupAppsFlyerTurin() {
    val appsFlyerConversionListenerTurin = object : AppsFlyerConversionListener {

      override fun onConversionDataFail(p0: String?) {}
      override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
      override fun onAttributionFailure(p0: String?) {}

      override fun onConversionDataSuccess(dataMapTurin: MutableMap<String, Any>?) {
        val sub10Turin = "$appsFlyerIdTurin||$googleAdvertisingIdTurin||${BuildConfig.APPS_FLYER_KEY_TURIN.decodeFromBase64Turin()}"
        val binomLinkTurin = when (val afStatusTurin = dataMapTurin?.get(AF_STATUS_TURIN)) {
          ORGANIC_TURIN -> {
            BinomLinkTurin(
              afStatusTurin = AFStatusTurin.ORGANIC_TURIN, baseTurin = null, keyTurin = null,
              bundleTurin = BuildConfig.APPLICATION_ID,
              sub2Turin = null, sub3Turin = null, sub4Turin = null, sub5Turin = null, sub6Turin = null,
              sub7Turin = ORGANIC_TURIN, sub10Turin = sub10Turin
            )
          }
          NON_ORGANIC_TURIN -> {
            val campaignTurin = dataMapTurin[CAMPAIGN_TURIN]?.toString()?.split("||")?.map { it.substringAfter(':') }
            BinomLinkTurin(
              afStatusTurin = AFStatusTurin.NON_ORGANIC_TURIN, baseTurin = null, keyTurin = campaignTurin?.getOrNull(1),
              bundleTurin = BuildConfig.APPLICATION_ID,
              sub2Turin = campaignTurin?.getOrNull(2), sub3Turin = campaignTurin?.getOrNull(3),
              sub4Turin = dataMapTurin[AD_GROUP_TURIN]?.toString(), sub5Turin = dataMapTurin[AD_SET_TURIN]?.toString(),
              sub6Turin = dataMapTurin[AF_CHANNEL_TURIN]?.toString(), sub7Turin = dataMapTurin[MEDIA_SOURCE_TURIN]?.toString(),
              sub10Turin = sub10Turin
            )
          }
          else -> {
            null
          }
        }
        repositoryTurin.binomLinkTurin = binomLinkTurin
      }
    }
    AppsFlyerLib.getInstance().apply {
      init(BuildConfig.APPS_FLYER_KEY_TURIN.decodeFromBase64Turin(), appsFlyerConversionListenerTurin, applicationContext)
      start(applicationContext)
      appsFlyerIdTurin = getAppsFlyerUID(applicationContext)
    }
  }

  private fun setupDITurin() {
    appComponentTurin = DaggerAppComponentTurin.builder()
      .repositoryModuleTurin(RepositoryModuleTurin(applicationContext))
      .build()
  }

  private fun injectTurin(): Unit = appComponentTurin.injectTurin(this)

  companion object {

    private const val TAG_TURIN: String = "WebViewApplication"

    //Keys for appsFlyerConversionListener
    private const val AF_STATUS_TURIN: String = "af_status"
    private const val ORGANIC_TURIN: String = "Organic"
    private const val NON_ORGANIC_TURIN: String = "Non-organic"
    private const val CAMPAIGN_TURIN: String = "campaign"
    private const val AD_GROUP_TURIN: String = "adgroup"
    private const val AD_SET_TURIN: String = "adset"
    private const val AF_CHANNEL_TURIN: String = "af_channel"
    private const val MEDIA_SOURCE_TURIN: String = "media_source"
  }
}