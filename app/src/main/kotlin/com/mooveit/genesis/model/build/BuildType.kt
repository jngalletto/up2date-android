package com.mooveit.genesis.model.build

enum class BuildType {
  STAGING,
  PRODUCTION,
  ;

  override fun toString() = when (this) {
    STAGING -> "staging"
    PRODUCTION -> "production"
  }
}
