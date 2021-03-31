package com.fiqsky.easysafe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fiqsky.easysafe.webview.MapsPharmacyActivity
import kotlinx.android.synthetic.main.activity_pharmacy.*

class PharmacyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy)

        btn_start_pcy.setOnClickListener {
            val intent = Intent(this, MapsPharmacyActivity::class.java)
            startActivity(intent)
        }

        cv_pcy.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        cv_pcy2.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
    }
}