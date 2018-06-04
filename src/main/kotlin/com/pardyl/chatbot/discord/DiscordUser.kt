package com.pardyl.chatbot.discord

import com.pardyl.chatbot.core.entities.Channel
import com.pardyl.chatbot.core.entities.User

internal class DiscordUser(val discordUser: net.dv8tion.jda.core.entities.User)
    : User {
    override fun getName(): String {
        return discordUser.name
    }

    override fun getId(): String {
        return discordUser.id
    }

    override fun getPrivateChannel(): Channel {
        return DiscordChannel(discordUser.openPrivateChannel().complete())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DiscordUser

        if (discordUser != other.discordUser) return false

        return true
    }

    override fun hashCode(): Int {
        return discordUser.hashCode()
    }

}
