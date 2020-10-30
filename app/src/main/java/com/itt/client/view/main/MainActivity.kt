package com.itt.client.view.main

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.itt.client.*
import com.itt.client.data.data.CurrentEventResponse
import com.itt.client.data.remote.ObjectResponse
import com.itt.client.data.remote.Resource
import com.itt.client.view.report.EventAdapter
import com.itt.client.view.report.ReportActivity
import com.itt.client.data.remote.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel by viewModel<MainActivityViewModel>()

    private val adapter: EventAdapter = EventAdapter()

    //runs every 10 seconds
    private val timer = object : CountDownTimer(60000, 10000) {
        override fun onTick(millisUntilFinished: Long) {
            onCountdownTick()
        }

        override fun onFinish() {
            onCountdownFinished()
        }
    }

    //program data
    var isRunning = false
    var programTime: String? = null
    var startColor: String? = null
    var stopColor: String? = null
    var reportColor: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reports.setOnClickListener { startActivity(ReportActivity.newInstance(this)) }
        start.setOnClickListener { startProgram() }

        clock.stopTickTick()
        setUpAdapterViews()
    }


    private fun setUpAdapterViews() {
        //invoke loading status
        rv.layoutManager = LinearLayoutManager(this)
        rv.addItemDecorationWithoutLastDivider()
        adapter.setEvents(ArrayList())
        rv.adapter = adapter
    }


    private fun startProgram() {
        GlobalScope.launch(Dispatchers.Main) {
            mainActivityViewModel.startServer().collect { processStartProgramResponse(it) }
        }
    }

    private fun fetchCurrentEvents() {
        GlobalScope.launch(Dispatchers.Main) {
            val cal = Calendar.getInstance()
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            mainActivityViewModel.fetchCurrentEvents(
                currentEventTimeFormat.format(Calendar.getInstance().time),
                startColor,
                stopColor,
                reportColor
            ).collect { processCurrentEventsResponse(it) }
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
                    adapter.setEvents(mutableListOf())
                }
            }
            Status.ERROR -> {
                srl.isRefreshing = false
                showSnack("Error: ${response.message}")
            }
        }

    }

    private fun processCurrentEventsResponse(response: Resource<ObjectResponse<CurrentEventResponse>>) {
        when (response.status) {
            Status.LOADING -> {
                //do nothing
            }
            Status.SUCCESS -> {
                showToast(getString(R.string.message_current_tasks_fetched))
                response?.data?.`object`.let {
                    updateColors(it?.start_color, it?.stop_color, it?.report_color)
                    updateClock(it?.time)
                    it?.events?.let { adapter.setEvents(it, true) }
                }
            }
            Status.ERROR -> {
                showSnack("Error: ${response.message}")
                timer.cancel()
            }
        }
    }


    /**
     * Program has started. Start Clock
     */
    private fun setClock(time: String?) {
        isRunning = true
        timer.start()
        programTime = time
        clock.setColor(Color.BLACK  )
        val cal = Calendar.getInstance()
        time?.let { cal.time = defaultTimeFormat.parse(time) }
        clock.calendar = cal
    }

    /**
     * Program has started. Start Clock
     */
    private fun updateClock(time: String?) {
        programTime = time
        val cal = Calendar.getInstance()
        time?.let { cal.time = currentEventTimeFormat.parse(time) }
        clock.calendar = cal
    }

    /**
     * Update the UI colors
     */
    private fun updateColors(start_color: String?, stop_color: String?, report_color: String?) {
        start_color?.let { wall.setBackgroundColor(Color.parseColor(it)) }
        stop_color?.let { clock.faceColor = Color.parseColor(it) }
        report_color?.let { clock.labelColor = Color.parseColor(it) }

    }

    /**
     * Will fetch current events
     * and updated colors every interval (10 seconds)
     */
    private fun onCountdownTick() {
        fetchCurrentEvents()
    }

    private fun onCountdownFinished() {
        timer.start()
    }

    override fun onResume() {
        super.onResume()
        if (isRunning)
            timer.start()
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }
}