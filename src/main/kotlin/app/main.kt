package app

import app.bot.dispatchers.onHelp
import app.bot.dispatchers.onInlineQuery
import app.bot.dispatchers.onRequest
import app.bot.dispatchers.onStart
import app.server.setupKtor
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.inlineQuery
import com.github.kotlintelegrambot.dispatcher.telegramError
import com.github.kotlintelegrambot.entities.TelegramFile
import com.github.kotlintelegrambot.webhook
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import java.io.File

lateinit var config: Config
lateinit var botScope: CoroutineScope
lateinit var okhttp: OkHttpClient
lateinit var bot: Bot

fun main(args: Array<String>) {
    config = Config(args.firstOrNull() ?: error("You must specify properties file name in first command line arg"))
    okhttp = OkHttpClient.Builder().build()
    botScope = CoroutineScope(Dispatchers.IO)

    setupKtor()

    bot = bot {
        token = config.botToken

        webhook {
            url = config.webhookUrl
            certificate = TelegramFile.ByFile(File("cert.pem"))
        }

        dispatch {
            command("start") { onStart() }
            command("help") { onHelp() }
            command("get") { onRequest("get") }
            command("post") { onRequest("post") }
            command("put") { onRequest("put") }
            command("patch") { onRequest("patch") }
            command("head") { onRequest("head") }
            command("delete") { onRequest("delete") }

            inlineQuery { onInlineQuery() }

            telegramError {
                println(error.getErrorMessage())
            }
        }
    }
    bot.startWebhook()
}
