package app.bot.dispatchers

import app.*
import app.bot.*
import com.github.kotlintelegrambot.dispatcher.handlers.InlineQueryHandlerEnvironment
import com.github.kotlintelegrambot.entities.InlineQuery
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.inlinequeryresults.InlineQueryResult
import com.github.kotlintelegrambot.entities.inlinequeryresults.InputMessageContent
import kotlinx.coroutines.launch
import okhttp3.Response

fun InlineQueryHandlerEnvironment.onInlineQuery() = botScope.launch {
    val params = inlineQuery.query.split(" ", limit = 4)
    val (method, url, mediaType) =
        validateUserInput(params, inlineQuery = inlineQuery) ?: return@launch

    httpCall(method, url, mediaType, params.getOrElse(3) { "" }).respondTo(inlineQuery)
}

fun InlineQuery.answer(
    shortTitle: String = TEXT_INLINE_SHORT_RESPONSE,
    shortDescription: String? = null,
    longTitle: String = TEXT_INLINE_FULL_RESPONSE,
    longDescription: String? = null
) {
    val queryResults = mutableListOf<InlineQueryResult>()

    if (shortDescription != null) queryResults += InlineQueryResult.Article(
        id = "short",
        title = shortTitle,
        description = shortDescription.withoutHtml(),
        inputMessageContent = InputMessageContent.Text(
            messageText = shortDescription,
            parseMode = ParseMode.HTML,
            disableWebPagePreview = true
        )
    )
    if (longDescription != null) queryResults += InlineQueryResult.Article(
        id = "full",
        title = longTitle,
        description = longDescription.withoutHtml(),
        inputMessageContent = InputMessageContent.Text(
            messageText = longDescription,
            parseMode = ParseMode.HTML,
            disableWebPagePreview = true
        )
    )

    bot.answerInlineQuery(id, queryResults, cacheTime = 0)
}

fun Response?.respondTo(inlineQuery: InlineQuery) {
    if (this == null) inlineQuery.answer(shortTitle = TEXT_ERROR, shortDescription = ERROR_REQUEST)
    else {
        val shortResponse = formatShortResponse(this)
        val fullResponse = formatFullResponse(this, shortResponse)

        inlineQuery.answer(shortDescription = shortResponse, longDescription = fullResponse)
    }
}
