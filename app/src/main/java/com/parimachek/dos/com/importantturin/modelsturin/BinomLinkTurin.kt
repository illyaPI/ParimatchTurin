package com.parimachek.dos.com.importantturin.modelsturin

import com.parimachek.dos.com.servicesturin.utilturin.initializationErrorTurin

data class BinomLinkTurin(
  val afStatusTurin: AFStatusTurin,

  var baseTurin: String?,
  var keyTurin: String?,

  val bundleTurin: String,

  val sub2Turin: String?,
  val sub3Turin: String?,
  val sub4Turin: String?,
  val sub5Turin: String?,
  val sub6Turin: String?,
  var sub7Turin: String?,

  val sub10Turin: String
) {

  fun toLinkTurin(): String {
    baseTurin ?: initializationErrorTurin("base")
    keyTurin ?: initializationErrorTurin("key")

    return when (afStatusTurin) {
      AFStatusTurin.ORGANIC_TURIN -> {
        sub7Turin ?: initializationErrorTurin("sub7")
        "$baseTurin?key=$keyTurin&bundle=$bundleTurin&sub7=$sub7Turin&sub10=$sub10Turin"
      }
      AFStatusTurin.NON_ORGANIC_TURIN -> {
        val linkBuilderTurin = StringBuilder("$baseTurin?key=$keyTurin&bundle=$bundleTurin")
        linkBuilderTurin.append(if (sub2Turin != null) "&sub2=$sub2Turin" else "")
        linkBuilderTurin.append(if (sub3Turin != null) "&sub3=$sub3Turin" else "")
        linkBuilderTurin.append(if (sub4Turin != null) "&sub4=$sub4Turin" else "")
        linkBuilderTurin.append(if (sub5Turin != null) "&sub5=$sub5Turin" else "")
        linkBuilderTurin.append(if (sub6Turin != null) "&sub6=$sub6Turin" else "")
        linkBuilderTurin.append(if (sub7Turin != null) "&sub7=$sub7Turin" else "")
        linkBuilderTurin.append("&sub10=$sub10Turin")
        linkBuilderTurin.toString()
      }
    }
  }
}
