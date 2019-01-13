package com.mooveit.genesis.ui.main

import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mooveit.genesis.router
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(router.routeToPostListActivity())
        finish()
    }
}
