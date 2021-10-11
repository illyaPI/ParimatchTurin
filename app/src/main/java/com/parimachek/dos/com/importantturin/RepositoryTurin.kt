package com.parimachek.dos.com.importantturin

import com.parimachek.dos.com.importantturin.modelsturin.BinomLinkTurin

interface RepositoryTurin {

  var binomLinkTurin: BinomLinkTurin?
  var whiteBaseTurin: String
  var blackBaseTurin: String?
  var defaultKeyTurin: String

  var lastBinomLinkTurin: String?
}