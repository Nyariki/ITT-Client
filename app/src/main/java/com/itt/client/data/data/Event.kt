package com.itt.client.data.data

import org.parceler.Parcel
import java.io.Serializable
import java.util.*

/**
 * @author Mayore Robert
 */

@Parcel(Parcel.Serialization.BEAN)
class Event : Serializable {
    var id: Long? = null
    var type: Long? = null
    var color: String? = null
    var message: String? = null
    var time: String? = null
}