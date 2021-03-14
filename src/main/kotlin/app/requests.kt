package app

import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.UnknownHostException
import java.text.ParseException

const val GET = "GET"
const val POST = "POST"
const val PUT = "PUT"
const val PATCH = "PATCH"
const val HEAD = "HEAD"
const val DELETE = "DELETE"

val availableMethods = listOf(GET, POST, PUT, PATCH, HEAD, DELETE)


fun httpCall(method: String, url: HttpUrl, contentType: MediaType?, body: String): Response? {
    return when (method) {
        GET -> proceedCall(GET, url)
        POST -> proceedCall(POST, url, contentType!!, body)
        PUT -> proceedCall(PUT, url, contentType!!, body)
        PATCH -> proceedCall(PATCH, url, contentType!!, body)
        HEAD -> proceedCall(HEAD, url)
        DELETE -> proceedCall(DELETE, url, contentType, body)
        else -> throw ParseException(method, -1)
    }
}

private fun proceedCall(method: String, url: HttpUrl, contentType: MediaType? = null, body: String? = null): Response? {
    return runCatching {
        val requestBuilder = Request.Builder()
            .url(url).cacheControl(CacheControl.FORCE_NETWORK)

        if (method == DELETE) requestBuilder.apply {
            if (body == null) delete()
            else delete(body.toRequestBody(contentType))
        } else {
            requestBuilder.method(method, body?.toRequestBody(contentType))
        }

        okhttp.newCall(requestBuilder.build()).execute()
    }.onSuccess {
        it.body?.close()
    }.getOrElse {
        if (it !is UnknownHostException) it.printStackTrace()
        null
    }
}
