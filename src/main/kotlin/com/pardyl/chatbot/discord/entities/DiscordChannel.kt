package com.pardyl.chatbot.discord.entities

import com.pardyl.chatbot.core.BotInstance
import com.pardyl.chatbot.core.entities.*
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.TextChannel

class DiscordChannel(private val channel: MessageChannel) : Channel() {
    override fun getName(): String {
        return channel.name
    }

    override fun getId(): String {
        return channel.id
    }

    override fun getServer(): Server? {
        if (channel is TextChannel) {
            return DiscordServer(channel.guild)
        }
        return null
    }

    override fun getMembers(): List<User>? {
        if (channel is TextChannel) {
            return channel.members.map { member -> DiscordUser(member.user) }
        }
        return null
    }

    override fun sendMessage(message: Message?, bot: BotInstance?) {
        if (message is DiscordMessage) {
            channel.sendMessage(message.discordMessage).complete()
        } else {
            throw IllegalArgumentException("Not a discord message")
        }
    }

    override fun addReaction(message: Message?, reaction: Reaction?, bot: BotInstance?) {
        if (reaction is DiscordReaction && message is DiscordMessage) {
            message.discordMessage.addReaction(reaction.discordReaction).complete()
        } else {
            throw IllegalArgumentException("Not a discord message or not a discord reaction")
        }
    }
}