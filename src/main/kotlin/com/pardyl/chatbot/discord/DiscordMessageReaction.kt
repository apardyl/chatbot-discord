package com.pardyl.chatbot.discord

import com.pardyl.chatbot.core.entities.Emote
import com.pardyl.chatbot.core.entities.Message
import com.pardyl.chatbot.core.entities.MessageReaction

internal class DiscordMessageReaction(private val message: DiscordMessage,
                                      private val emote: Emote,
                                      private val count: Int) : MessageReaction {
    override fun getMessage(): Message {
        return message
    }

    override fun getEmote(): Emote {
        return emote
    }

    override fun getCount(): Int {
        return count
    }
}
