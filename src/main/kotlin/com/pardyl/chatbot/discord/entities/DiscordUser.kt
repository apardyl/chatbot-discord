package com.pardyl.chatbot.discord.entities

import com.pardyl.chatbot.core.entities.Channel
import com.pardyl.chatbot.core.entities.User

class DiscordUser(val discordUser: net.dv8tion.jda.core.entities.User) : User {
    override fun getName(): String {
        return discordUser.name
    }

    override fun getId(): String {
        return discordUser.id
    }

    override fun getPrivateChannel(): Channel {
        return DiscordChannel(discordUser.openPrivateChannel().complete())
    }


}
