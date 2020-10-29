package com.pardyl.chatbot.discord

import com.pardyl.chatbot.core.entities.Channel
import com.pardyl.chatbot.core.entities.Message
import com.pardyl.chatbot.core.entities.Role
import com.pardyl.chatbot.core.entities.User

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

}
