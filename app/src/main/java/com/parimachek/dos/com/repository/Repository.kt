package com.parimachek.dos.com.repository

import com.parimachek.dos.com.models.BinomLink

interface Repository {

  var binomLink: BinomLink?
  var whiteBase: String?
  var blackBase: String?
  var defaultKey: String?

  var lastBinomLink: String?
}