package app.bot.dispatchers

import app.*
import app.bot.*
import app.server.*
import com.github.kotlintelegrambot.dispatcher.handlers.CommandHandlerEnvironment
import com.github.kotlintelegrambot.entities.ParseMode
import kotlinx.coroutines.launch

fun CommandHandlerEnvironment.onStart() = botScope.launch {
    message.send(TEXT_START, ParseMode.MARKDOWN)
}

fun CommandHandlerEnvironment.onHelp() = botScope.launch {
    message.send(TEXT_HELP, ParseMode.MARKDOWN)
}

fun CommandHandlerEnvironment.onRequest(command: String) = botScope.launch {
    val (method, url, mediaType) = validateUserInput(
        listOf(command, args.firstOrNull(), args.getOrNull(1)), message = message
    ) ?: return@launch

    httpCall(method, url, mediaType, args.getOrElse(2) { "" }).respondTo(message)
}
