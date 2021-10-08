package com.parimachek.dos.com.models

import com.parimachek.dos.com.util.initializationError

data class BinomLink(
  val afStatus: AFStatus,

  var base: String?,
  var key: String?,

  val bundle: String,

  val sub2: String?,
  val sub3: String?,
  val sub4: String?,
  val sub5: String?,
  val sub6: String?,
  var sub7: String?,

  val sub10: String
) {

  fun toLink(): String {
    base ?: initializationError("base")
    key ?: initializationError("key")

    return when (afStatus) {
      AFStatus.ORGANIC -> {
        sub7 ?: initializationError("sub7")
        "$base?key=$key&bundle=$bundle&sub7=$sub7&sub10=$sub10"
      }
      AFStatus.NON_ORGANIC -> {
        val linkBuilderBower = StringBuilder("$base?key=$key&bundle=$bundle")
        linkBuilderBower.append(if (sub2 != null) "&sub2=$sub2" else "")
        linkBuilderBower.append(if (sub3 != null) "&sub3=$sub3" else "")
        linkBuilderBower.append(if (sub4 != null) "&sub4=$sub4" else "")
        linkBuilderBower.append(if (sub5 != null) "&sub5=$sub5" else "")
        linkBuilderBower.append(if (sub6 != null) "&sub6=$sub6" else "")
        linkBuilderBower.append(if (sub7 != null) "&sub7=$sub7" else "")
        linkBuilderBower.append("&sub10=$sub10")
        linkBuilderBower.toString()
      }
    }
  }
}
