package app.bot

import okhttp3.Headers
import okhttp3.Response

private val HTML_REGEX = "<.*?>".toRegex()

fun formatShortResponse(response: Response?) = response?.let {
    """
        <b>${it.request.method}</b> ${it.request.url}
        <b>Response:</b> HTTP ${it.code} ${httpStatuses[it.code] ?: ""}
    """.trimIndent()
} ?: ERROR_REQUEST

fun formatFullResponse(response: Response?, shortResponse: String? = null /* optimization */): String {
    response ?: return ERROR_REQUEST
    return (shortResponse ?: formatShortResponse(response)) +
            "\n<b>Protocol:</b> ${response.protocol}" +
            "\n<b>ContentType:</b> ${response.body?.contentType() ?: "<i>NO CONTENT</i>"}" +
            "\n\n<b>Response headers:</b>\n${response.headers.toPrettyString()}"
                .also { response.body?.close() }
}

fun String.withoutHtml() = replace(HTML_REGEX, "")

private fun Headers.toPrettyString() = buildString {
    for ((name, values) in toMultimap()) {
        append("<u>", name, "</u>: ", values.joinToString(";"), "\n")
    }
}
