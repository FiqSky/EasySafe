package com.fiqsky.easysafe

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_sos.*

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class SosActivity : AppCompatActivity() {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLastLocation: Location? = null
    private var latitude: Double? = null
    private var longitude: Double? = null
    private val internationalPhoneNumber = "6281414188150"

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sos)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mFusedLocationClient!!.lastLocation
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful && task.result != null) {
                        mLastLocation = task.result

                        latitude = mLastLocation!!.latitude
                        longitude = mLastLocation!!.longitude
                        Log.d("TAG", "getLastLocation: $latitude")
                        Log.d("TAG", "getLastLocation: $longitude")

                        val maps = "https://www.google.com/maps/dir//${latitude},${longitude}"
                        val message = "Tolong Saya!!!\nSaya Sekarang Ada di: $maps"

                        btn_start_sos.setOnClickListener {
                            try {
                                // Check if whatsapp is installed
                                this.packageManager?.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)

                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$internationalPhoneNumber?text=${message}"))
                                startActivity(intent)
                            } catch (e: PackageManager.NameNotFoundException) {
                                Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show()
                            }
                        }

                    } else {
//                    Toast.makeText(this, "Lokasi Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                        Log.w("TAG", "getLastLocation:exception", task.exception)
                    }
                }

        cv_sos.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        cv_sos2.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
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
}