package com.itt.client.view.report

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.itt.client.R
import com.itt.client.addItemDecorationWithoutLastDivider
import com.itt.client.data.data.Event
import com.itt.client.data.remote.ListResponse
import com.itt.client.data.remote.Resource
import com.itt.client.showSnack
import com.itt.client.data.remote.Status
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class ReportActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val adapter: EventAdapter = EventAdapter()

    private val bookActivityViewModel by viewModel<ReportActivityViewModel>()

    companion object {
        fun newInstance(context: Context) = Intent(context, ReportActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        srl.setOnRefreshListener(this)
        setUpAdapterViews()
        fetchEvents()
    }

    private fun fetchEvents() {
        GlobalScope.launch(Dispatchers.Main) {
            bookActivityViewModel.fetchReports().collect { processFetchEventsResponse(it) }
        }
    }

    private fun processFetchEventsResponse(response: Resource<ListResponse<Event>>) {
        when (response.status) {
            Status.LOADING -> {
                srl.isRefreshing = true
            }
            Status.SUCCESS -> {
                srl.isRefreshing = false

                if (response.data?.status == true) {
                    response.data.list?.let {
                        adapter.setEvents(it)
                    }

                }
                if (response.data?.status != true) showSnack(response.data?.message ?: "")
            }
            Status.ERROR -> {
                srl.isRefreshing = false
                showSnack("Error: ${response.message}")
            }
        }
    }


    private fun setUpAdapterViews() {
        //invoke loading status
        rv.layoutManager = LinearLayoutManager(this)
        rv.addItemDecorationWithoutLastDivider()
        adapter.setEvents(ArrayList())
        rv.adapter = adapter
    }

    override fun onRefresh() {
        fetchEvents()
    }


}