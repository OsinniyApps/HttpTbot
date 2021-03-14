package app.bot

import app.PATCH
import app.POST
import app.PUT
import app.availableMethods
import app.bot.dispatchers.answer
import com.github.kotlintelegrambot.entities.InlineQuery
import com.github.kotlintelegrambot.entities.Message
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull

/**
 * @param input user request divided by space.
 * Presumably should contain method and url. Content type and body are optional.
 * @param message user message to answer for if input is not valid
 * @param inlineQuery inline query to answer for if input is not valid
 *
 * @return triple of method, url and media type.
 * _Null_ if input is not valid.
 * _Not null media type_ in post put and path requests.
 * */
fun validateUserInput(
    input: List<String?>,
    message: Message? = null,
    inlineQuery: InlineQuery? = null
): Triple<String, HttpUrl, MediaType?>? {
//    validate method
    val method = input.firstOrNull()?.toUpperCase()
    if (method == null || method !in availableMethods) {
        message?.reply(ERROR_INVALID_METHOD)
        inlineQuery?.answer(shortTitle = TEXT_ERROR, shortDescription = ERROR_INVALID_METHOD)
        return null
    }

//    check if url present
    val url = input.getOrNull(1)
    if (url == null) {
        message?.reply(ERROR_NO_URL)
        inlineQuery?.answer(shortTitle = TEXT_ERROR, shortDescription = ERROR_NO_URL)
        return null
    }

//    validate url
    val parsedUrl = url.toHttpUrlOrNull()
    if (parsedUrl == null) {
        message?.reply(ERROR_URL_NOT_VALID)
        inlineQuery?.answer(shortTitle = TEXT_ERROR, shortDescription = ERROR_URL_NOT_VALID)
        return null
    }

//    validate media type
    val contentType = input.getOrNull(2)?.toMediaTypeOrNull()
    when (method) {
        POST, PUT, PATCH -> {
            if (contentType == null) {
                message?.reply(ERROR_INVALID_CONTENT_TYPE)
                inlineQuery?.answer(shortTitle = TEXT_ERROR, shortDescription = ERROR_INVALID_CONTENT_TYPE)
                return null
            }
        }
    }

    return Triple(method, parsedUrl, contentType)
}
