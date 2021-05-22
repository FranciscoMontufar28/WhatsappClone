package com.francisco.whatsapptest.util

data class Event<out T>(private val content: T) {

    private var hasBeenHandles = false

    fun getContentIfNotHandled(): T? = if (hasBeenHandles) {
        null
    } else {
        hasBeenHandles = true
        content
    }
}