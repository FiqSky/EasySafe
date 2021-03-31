package com.fiqsky.easysafe.webview

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.webkit.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fiqsky.easysafe.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_maps_hospital.*

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class MapsHospitalActivity : AppCompatActivity() {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLastLocation: Location? = null
    private var latitude: Double? = null
    private var longitude: Double? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_hospital)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mFusedLocationClient!!.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result

                    latitude = mLastLocation!!.latitude
                    longitude = mLastLocation!!.longitude
                    Log.d("TAG", "getLastLocation: $latitude")
                    Log.d("TAG", "getLastLocation: $longitude")

                } else {
                    Toast.makeText(this, "Lokasi Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                    Log.w("TAG", "getLastLocation:exception", task.exception)
                }
            }

        wv_hospital.loadUrl("https://www.google.com/maps/search/rumah+sakit+terdekat+di+surabaya/${longitude},${latitude}")
        val webSettings: WebSettings = wv_hospital.settings
        webSettings.javaScriptEnabled = true

        wv_hospital.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url.toString())
                Log.d("URL", "shouldOverrideUrlLoading: $url")
                return true
            }
        }

        wv_hospital.webChromeClient = object : WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(
                origin: String,
                callback: GeolocationPermissions.Callback
            ) {
                callback.invoke(origin, true, false)
            }
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !==
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) ===
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // Clear all the Application Cache, Web SQL Database and the HTML5 Web Storage
        WebStorage.getInstance().deleteAllData()

        // Clear all the cookies
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()

        wv_hospital.clearCache(true)
        wv_hospital.clearFormData()
        wv_hospital.clearHistory()
        wv_hospital.clearSslPreferences()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}