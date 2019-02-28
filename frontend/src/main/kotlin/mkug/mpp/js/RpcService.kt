package mkug.mpp.js

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.xhr.XMLHttpRequest
import kotlin.js.Promise

private const val HTML_CODE_SUCCESS: Short = 200
private const val XHR_READY_CODE: Short = 4

internal object RpcService {
    fun <T> call(method: String, data: Any? = null, callback: (Promise<T>.() -> Unit)? = null) {
        GlobalScope.launch {
            val xhr = createXhr(method, callback)
            if (data == null) {
                xhr.open("GET", "http://localhost:8080/rpc/$method", true)
                xhr.send()
            } else {
                xhr.open("POST", "http://localhost:8080/rpc/$method", true)
                xhr.setRequestHeader("Content-Type", "application/json")
                xhr.send(JSON.stringify(data))
            }
        }
    }
}

fun <T> createXhr(method: String, callback: (Promise<T>.() -> Unit)? = null): XMLHttpRequest {
    return XMLHttpRequest()
        .apply {
            callback?.also { cb ->
                onload = {
                    if (readyState == XHR_READY_CODE) {
                        when (status) {
                            HTML_CODE_SUCCESS -> {
                                cb.success(this)
                            }
                            else -> {
                                cb.reject(method, this)
                            }
                        }
                    }
                }
                onerror = {
                    cb.reject(method, this)
                }
            }
        }
}

fun <T> (Promise<T>.() -> Unit).reject(method: String, xhr: XMLHttpRequest) {
    Promise.reject(RpcException(xhr.status, method, xhr.statusText)).this()
}

fun <T> (Promise<T>.() -> Unit).success(xhr: XMLHttpRequest) {
    Promise.resolve<T>(JSON.parse(xhr.responseText)).this()
}

class RpcException(val status: Short, val method: String, errorText: String) : Exception(message = errorText)