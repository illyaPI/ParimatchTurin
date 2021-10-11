package com.parimachek.dos.com.repositoryturin

import android.content.Context
import com.google.gson.Gson
import com.parimachek.dos.com.importantturin.RepositoryTurin
import com.parimachek.dos.com.importantturin.modelsturin.BinomLinkTurin
import com.parimachek.dos.com.servicesturin.utilturin.initializationErrorTurin

class WebViewRepositoryTurin(
  appContextTurin: Context,
  private val gsonTurin: Gson,
) : RepositoryTurin {

  private val prefsTurin = appContextTurin.getSharedPreferences(PREFS_TURIN, Context.MODE_PRIVATE)

  private fun getStringTurin(keyTurin: String): String? = prefsTurin.getString(keyTurin, null)

  private fun putStringTurin(keyTurin: String, valueTurin: String?): Unit = prefsTurin.edit().putString(keyTurin, valueTurin).apply()

  override var binomLinkTurin: BinomLinkTurin?
    get() {
      val binomLinkStringTurin = prefsTurin.getString(BINOM_LINK_TURIN, null) ?: return null
      return gsonTurin.fromJson(binomLinkStringTurin, BinomLinkTurin::class.java)
    }
    set(value) = prefsTurin.edit().putString(BINOM_LINK_TURIN, gsonTurin.toJson(value)).apply()

  override var whiteBaseTurin: String
    get() = getStringTurin(WHITE_BASE_TURIN) ?: initializationErrorTurin("whiteBase")
    set(value) = putStringTurin(WHITE_BASE_TURIN, value)

  override var blackBaseTurin: String?
    get() = getStringTurin(BLACK_BASE_TURIN)
    set(value) = putStringTurin(BLACK_BASE_TURIN, value)

  override var defaultKeyTurin: String
    get() = getStringTurin(DEFAULT_KEY_TURIN) ?: initializationErrorTurin("defaultKey")
    set(value) = putStringTurin(DEFAULT_KEY_TURIN, value)

  override var lastBinomLinkTurin: String?
    get() = getStringTurin(LAST_BINOM_LINK_TURIN)
    set(value) = putStringTurin(LAST_BINOM_LINK_TURIN, value)

  companion object {

    private const val PREFS_TURIN: String = "PREFS_TURIN"

    private const val BINOM_LINK_TURIN: String = "BINOM_LINK_TURIN"
    private const val WHITE_BASE_TURIN: String = "WHITE_BASE_TURIN"
    private const val BLACK_BASE_TURIN: String = "BLACK_BASE_TURIN"
    private const val DEFAULT_KEY_TURIN: String = "DEFAULT_KEY_TURIN"

    private const val LAST_BINOM_LINK_TURIN: String = "LAST_BINOM_LINK_TURIN"
  }
}