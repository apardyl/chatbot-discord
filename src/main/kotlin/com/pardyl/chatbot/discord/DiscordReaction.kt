package com.pardyl.chatbot.discord

import com.pardyl.chatbot.core.entities.Emote

internal class DiscordReaction(val discordReaction: net.dv8tion.jda.api.entities.Emote) : Emote {
    override fun getName(): String {
        return discordReaction.name
    }

    override fun getId(): String {
        return discordReaction.id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DiscordReaction

        if (discordReaction != other.discordReaction) return false

        return true
    }

    override fun hashCode(): Int {
        return discordReaction.hashCode()
    }
}
