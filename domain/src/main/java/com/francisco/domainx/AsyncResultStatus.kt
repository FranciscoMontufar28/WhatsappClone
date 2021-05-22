package com.francisco.domainx

enum class AsyncResultStatus { LOADING, SUCCESS, ERROR }

data class AsyncStatus<out T>(
    val status: AsyncResultStatus,
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T): AsyncStatus<T> =
            AsyncStatus(
                status = AsyncResultStatus.SUCCESS,
                data = data
            )

        fun <T> error(data: T): AsyncStatus<T> =
            AsyncStatus(
                status = AsyncResultStatus.ERROR,
                data = data
            )
    }
}
