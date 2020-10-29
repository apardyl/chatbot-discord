package com.pardyl.chatbot.discord

import com.pardyl.chatbot.core.BotInstance
import com.pardyl.chatbot.core.entities.*

internal class DiscordMessage(val discordMessage: net.dv8tion.jda.api.entities.Message) : Message {
    override fun getChannel(): Channel {
        return DiscordChannel(discordMessage.channel)
    }

    override fun getAuthor(): User? {
        return DiscordUser(discordMessage.author)
    }

    override fun getText(): String {
        return discordMessage.contentRaw
    }

    override fun getMentionedUsers(): List<User>? {
        return discordMessage.mentionedUsers.map { user -> DiscordUser(user) }
    }

    override fun getMentionedRoles(): List<Role>? {
        return discordMessage.mentionedRoles.map { role -> DiscordRole(role) }
    }

    override fun getMentionedChannels(): List<Channel> {
        return discordMessage.mentionedChannels.map { channel -> DiscordChannel(channel) }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DiscordMessage

        if (discordMessage != other.discordMessage) return false

        return true
    }

    override fun hashCode(): Int {
        return discordMessage.hashCode()
    }

    override fun addReaction(reaction: Emote?, bot: BotInstance?) {
        when (reaction) {
            is DiscordReaction -> discordMessage.addReaction(reaction.discordReaction).complete()
            is UnicodeEmote-> discordMessage.addReaction(reaction.name)
            else -> throw IllegalArgumentException("Not a discord message or not a discord reaction")
        }
    }

    override fun unpin(bot: BotInstance?) {
        discordMessage.unpin().complete()
    }

    override fun pin(bot: BotInstance?) {
        discordMessage.pin().complete()
    }

    override fun delete(bot: BotInstance?) {
        discordMessage.delete().complete()
    }
}
