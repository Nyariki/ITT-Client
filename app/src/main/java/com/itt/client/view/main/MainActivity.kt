package com.itt.client.view.main

import android.graphics.Color
import android.os.Bundle
import android.widget.AnalogClock
import androidx.appcompat.app.AppCompatActivity
import com.itt.client.R
import com.itt.client.data.remote.ObjectResponse
import com.itt.client.data.remote.Resource
import com.itt.client.defaultTimeFormat
import com.itt.client.showSnack
import com.itt.client.view.report.ReportActivity
import com.learning.kifaru.data.remote.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reports.setOnClickListener { startActivity(ReportActivity.newInstance(this)) }
        start.setOnClickListener { startProgram() }

        clock.stopTickTick()
    }

    private fun startProgram() {
        GlobalScope.launch(Dispatchers.Main) {
            mainActivityViewModel.startServer().collect { processStartProgramResponse(it) }
        }
    }

    private fun processStartProgramResponse(response: Resource<ObjectResponse<String>>) {
        when (response.status) {
            Status.LOADING -> {
                srl.isRefreshing = true
            }
            Status.SUCCESS -> {
                srl.isRefreshing = false
                showSnack(response.data?.message ?: "")
                response?.data?.`object`.let {
                    setClock(it)
                }
            }
            Status.ERROR -> {
                srl.isRefreshing = false
                showSnack("Error: ${response.message}")
            }
        }

    }

    /**
     * Program has started. Start Clock
     */
    private fun setClock(time: String?) {
        clock.setColor(Color.BLACK)
        val cal = Calendar.getInstance()
        time?.let{ cal.time = defaultTimeFormat.parse(time) }
        clock.calendar = cal
        clock.stopTickTick()
    }
}