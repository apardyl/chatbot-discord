package com.pardyl.chatbot.discord

import com.pardyl.chatbot.core.BotConfiguration
import com.pardyl.chatbot.core.BotInstance
import com.pardyl.chatbot.core.events.OnReadyEvent
import com.pardyl.chatbot.core.entities.MessageFactory
import com.pardyl.chatbot.core.entities.Server
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import java.io.InputStream
import java.util.*
import kotlin.system.exitProcess

internal class DiscordBotInstance(configuration: BotConfiguration,
                                  private val token: String,
                                  private val properties: Properties)
    : BotInstance(configuration) {
    private var api: JDA? = null

    override fun run() {
        val builder = JDABuilder.createDefault(token)
        builder.addEventListeners(DiscordEventHandler(this))
        api = builder.build().awaitReady()
        process(OnReadyEvent())
    }

    override fun shutdown() {
        api!!.shutdown()
        exitProcess(0)
    }

    override fun getServers(): List<Server> {
        return api!!.guilds.map { guild -> DiscordServer(guild) }
    }

    override fun getServerForName(name: String): Server {
        return DiscordServer(api!!.getGuildsByName(name, true).elementAtOrNull(0)!!)
    }

    override fun getServerForId(id: String): Server {
        return DiscordServer(api!!.getGuildById(id)!!)
    }

    override fun getMessageFactory(): MessageFactory {
        return DiscordMessageFactory()
    }

    override fun runAdminTask(taskName: String?): InputStream {
        val command = (properties["TASK-$taskName"] ?: throw UnsupportedOperationException(taskName)) as String
        val proc = Runtime.getRuntime().exec(command)
        return proc.inputStream
    }
}
