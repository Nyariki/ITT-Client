package com.itt.client.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itt.client.R
import com.itt.client.view.report.ReportActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener { startActivity(ReportActivity.newInstance(this)) }
    }
}