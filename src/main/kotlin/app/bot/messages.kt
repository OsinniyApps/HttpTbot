package app.bot

import app.bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.ReplyMarkup
import com.github.kotlintelegrambot.network.bimap
import okhttp3.Response

fun Response?.respondTo(message: Message) {
    message.reply(formatFullResponse(this), ParseMode.HTML, disableWebPagePreview = true)
}

fun Message.reply(
    text: String,
    parseMode: ParseMode? = null,
    disableWebPagePreview: Boolean? = null,
    disableNotification: Boolean? = null,
    replyMarkup: ReplyMarkup? = null
) = sendMessageInternal(
    sourceMessage = this,
    text = text,
    direct = true,
    parseMode = parseMode,
    disableWebPagePreview = disableWebPagePreview,
    disableNotification = disableNotification,
    replyMarkup = replyMarkup
)

fun Message.send(
    text: String,
    parseMode: ParseMode? = null,
    disableWebPagePreview: Boolean? = null,
    disableNotification: Boolean? = null,
    replyMarkup: ReplyMarkup? = null
) = sendMessageInternal(
    sourceMessage = this,
    text = text,
    direct = false,
    parseMode = parseMode,
    disableWebPagePreview = disableWebPagePreview,
    disableNotification = disableNotification,
    replyMarkup = replyMarkup
)

private fun sendMessageInternal(
    sourceMessage: Message,
    text: String,
    direct: Boolean,
    parseMode: ParseMode? = null,
    disableWebPagePreview: Boolean? = null,
    disableNotification: Boolean? = null,
    replyMarkup: ReplyMarkup? = null
): Message? {
    return bot.sendMessage(
        chatId = ChatId.fromId(sourceMessage.chat.id),
        text = text,
        parseMode = parseMode,
        disableWebPagePreview = disableWebPagePreview,
        disableNotification = disableNotification,
        replyMarkup = replyMarkup,
        replyToMessageId = if (direct) sourceMessage.messageId else null
    ).bimap(mapResponse = { response ->
        response?.result
    }, mapError = { err ->
        err.errorBody?.string()?.let {
            println(it)
        }
        err.exception?.let {
            it.printStackTrace()
        }
        null
    })
}
