package com.pardyl.chatbot.discord.entities

import com.pardyl.chatbot.core.entities.Role

class DiscordRole(val discordRole: net.dv8tion.jda.core.entities.Role) : Role {
    override fun getName(): String {
        return discordRole.name
    }

    override fun getId(): String {
        return discordRole.id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DiscordRole

        if (discordRole != other.discordRole) return false

        return true
    }

    override fun hashCode(): Int {
        return discordRole.hashCode()
    }
}
