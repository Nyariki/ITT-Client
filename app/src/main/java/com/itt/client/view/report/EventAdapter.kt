package com.itt.client.view.report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.itt.client.R
import com.itt.client.data.data.Event
import kotlinx.android.synthetic.main.layout_event_item.view.*


class EventAdapter() : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private var events: MutableList<Event> = mutableListOf()

    override fun onBindViewHolder(@NonNull holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_event_item, parent, false)
        return EventViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun setEvents(events: MutableList<Event>, clearPrevious : Boolean = true) {
        if(clearPrevious)
            this.events.clear()
        this.events.addAll(0, events)
        this.notifyDataSetChanged()
    }

    fun removeEvent(event: Event) {
        this.events.remove(event)
        this.notifyDataSetChanged()
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(event: Event) {

            itemView.tv.text = "${event.time} - ${event.message}"
        }
    }
}