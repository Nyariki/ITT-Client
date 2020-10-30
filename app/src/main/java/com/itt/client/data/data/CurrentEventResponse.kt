package com.itt.client.data.data

/**
 * @author Mayore Robert
 */

data class CurrentEventResponse(
    var time: String,
    var events: MutableList<Event>?,
    var start_color: String?,
    var stop_color: String?,
    var report_color: String?
)