package com.pardyl.chatbot.discord

import com.pardyl.chatbot.core.BotConfiguration
import com.pardyl.chatbot.core.BotInstance
import com.pardyl.chatbot.core.events.OnReadyEvent
import com.pardyl.chatbot.core.entities.MessageFactory
import com.pardyl.chatbot.core.entities.Server
import com.pardyl.chatbot.discord.entities.DiscordMessageFactory
import com.pardyl.chatbot.discord.entities.DiscordServer
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder

class DiscordBotInstance(configuration: BotConfiguration, val token: String) : BotInstance(configuration) {
    private var api: JDA? = null

    override fun run() {
        val builder = JDABuilder(AccountType.BOT)
        builder.setToken(token)
        builder.addEventListener(DiscordEventHandler(this))
        api = builder.buildBlocking()
        process(OnReadyEvent())
    }

    override fun getServers(): List<Server> {
        return api!!.guilds.map { guild -> DiscordServer(guild) }
    }

    override fun getMessageFactory(): MessageFactory {
        return DiscordMessageFactory()
    }
}
