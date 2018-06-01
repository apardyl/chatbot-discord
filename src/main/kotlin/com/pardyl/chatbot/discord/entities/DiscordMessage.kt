package com.pardyl.chatbot.discord.entities

import com.pardyl.chatbot.core.entities.Channel
import com.pardyl.chatbot.core.entities.Message
import com.pardyl.chatbot.core.entities.Role
import com.pardyl.chatbot.core.entities.User

class DiscordMessage(val discordMessage: net.dv8tion.jda.core.entities.Message) : Message {
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
}
