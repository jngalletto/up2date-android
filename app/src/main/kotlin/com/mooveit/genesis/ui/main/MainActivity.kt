package com.mooveit.genesis.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mooveit.genesis.router
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mapright.android.helper.isPermissionGranted
import android.location.Geocoder
import com.mooveit.genesis.App
import com.mooveit.genesis.R
import com.mooveit.genesis.model.country.Country
import com.pixplicity.easyprefs.library.Prefs
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (Prefs.contains(getString(R.string.preferences_country_key))){
            proceedToPostListActivity()
        } else {
            requestLocationPermissionIfNeeded {
                setLocationListener()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val granted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        if (granted) {
            when (requestCode) {
                REQUEST_LOCATION_USAGE_CODE -> {
                    setLocationListener()
                }
                else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun getCurrentCountry(countryFound: String, countriesAvailable: List<String>) =
        countriesAvailable.firstOrNull { country -> country == countryFound }
            ?: getString(R.string.united_states)

    private fun checkCountryAndSave(countryFound: String) {
        val countriesAvailable = ArrayList(Arrays.asList<String>(*resources.getStringArray(R.array.countries)))
        getCurrentCountry(countryFound.toLowerCase(), countriesAvailable).let {
            Prefs.putString(getString(R.string.preferences_country_key), it)
        }
    }

    @SuppressLint("MissingPermission")
    private fun setLocationListener() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val gcd = Geocoder(this, Locale.getDefault())
                    val addresses = gcd.getFromLocation(location.latitude, location.longitude, 1)
                    if (addresses.size > 0) {
                        val countryName = addresses[0].countryCode
                        checkCountryAndSave(countryName)
                        proceedToPostListActivity()
                    } else {
                        proceedToPostListActivity()
                    }
                } ?: run {
                    proceedToPostListActivity()
                }
            }
    }

    private fun proceedToPostListActivity() {
        val countryKey = Prefs.getString(getString(R.string.preferences_country_key), getString(R.string.united_states))
        startActivity(router.routeToPostListActivity(Country(countryKey)))
        finish()
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun requestLocationPermissionIfNeeded(grantedBlock: () -> Unit) {
        if (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            grantedBlock()
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_USAGE_CODE)
        }
    }

    companion object {
        private const val REQUEST_LOCATION_USAGE_CODE = 1001
    }
}
