package learn.mobile.rem_it.models

import android.content.Context

data class ActivityItem(
    val name: String,
    val description: String,
    val activityClass: Class<*>
)
