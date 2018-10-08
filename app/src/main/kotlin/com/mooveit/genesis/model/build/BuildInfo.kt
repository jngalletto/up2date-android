package com.mooveit.genesis.model.build

import com.mooveit.genesis.BuildConfig

class BuildInfo {
  val isDebug = BuildConfig.DEBUG
  val isStaging = BuildConfig.FLAVOR == BuildType.STAGING.toString()
  val isProduction = BuildConfig.FLAVOR == BuildType.PRODUCTION.toString()
}
