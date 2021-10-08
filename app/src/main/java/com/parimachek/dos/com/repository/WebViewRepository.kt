package com.parimachek.dos.com.repository

import android.content.Context
import com.google.gson.Gson
import com.parimachek.dos.com.models.BinomLink

class WebViewRepository(
  appContext: Context,
  private val gson: Gson,
) : Repository {

  private val prefs = appContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

  private fun getString(key: String): String? = prefs.getString(key, null)

  private fun putString(key: String, value: String?): Unit = prefs.edit().putString(key, value).apply()

  override var binomLink: BinomLink?
    get() {
      val binomLinkString = prefs.getString(BINOM_LINK, null) ?: return null
      return gson.fromJson(binomLinkString, BinomLink::class.java)
    }
    set(value) = prefs.edit().putString(BINOM_LINK, gson.toJson(value)).apply()

  override var whiteBase: String?
    get() = getString(WHITE_BASE)
    set(value) = putString(WHITE_BASE, value)

  override var blackBase: String?
    get() = getString(BLACK_BASE)
    set(value) = putString(BLACK_BASE, value)

  override var defaultKey: String?
    get() = getString(DEFAULT_KEY)
    set(value) = putString(DEFAULT_KEY, value)

  override var lastBinomLink: String?
    get() = getString(LAST_BINOM_LINK)
    set(value) = putString(LAST_BINOM_LINK, value)

  companion object {
    private const val PREFS: String = "PREFS"

    private const val BINOM_LINK: String = "BINOM_LINK"
    private const val WHITE_BASE: String = "WHITE_BASE"
    private const val BLACK_BASE: String = "BLACK_BASE"
    private const val DEFAULT_KEY: String = "DEFAULT_KEY"

    private const val LAST_BINOM_LINK: String = "LAST_BINOM_LINK"
  }
}