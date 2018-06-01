package com.pardyl.chatbot.discord.entities

import com.pardyl.chatbot.core.entities.Reaction
import net.dv8tion.jda.core.entities.Emote

class DiscordReaction(val discordReaction: Emote) : Reaction {
    override fun getName(): String {
        return discordReaction.name
    }

    override fun getId(): String {
        return discordReaction.id
    }
}
