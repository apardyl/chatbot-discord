package com.pardyl.chatbot.discord

import com.pardyl.chatbot.core.entities.*
import net.dv8tion.jda.api.MessageBuilder

internal class DiscordMessageFactory : MessageFactory {
    private val msgBuilder: MessageBuilder = MessageBuilder()

    override fun appendText(text: String?): MessageFactory {
        msgBuilder.append(text)
        return this
    }

    override fun appendMentionUser(user: User?): MessageFactory {
        if (user is DiscordUser) {
            msgBuilder.append(user.discordUser)
        } else {
            msgBuilder.append(user!!.name)
        }
        return this
    }

    override fun appendMentionRole(role: Role?): MessageFactory {
        if (role is DiscordRole) {
            msgBuilder.append(role.discordRole)
        } else {
            msgBuilder.append(role!!.name)
        }
        return this
    }

    override fun appendEmote(reaction: Emote?): MessageFactory {
        if (reaction is DiscordReaction) {
            msgBuilder.append(reaction.discordReaction)
        } else {
            msgBuilder.append(reaction!!.name)
        }
        return this
    }

    override fun build(): Message {
        return DiscordMessage(msgBuilder.build())
    }
}
