package com.mooveit.genesis.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mooveit.genesis.router

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    startActivity(router.routeToPostListActivity())
    finish()
  }
}
