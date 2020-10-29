package com.pardyl.chatbot.discord

import com.pardyl.chatbot.core.entities.UnicodeEmote
import com.pardyl.chatbot.core.events.OnMessageEvent
import com.pardyl.chatbot.core.events.OnMessageRemovedEvent
import com.pardyl.chatbot.core.events.OnReactionAddedEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.message.MessageDeleteEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

internal class DiscordEventHandler(private val bot: DiscordBotInstance) : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        // Do not call API at this point!
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.isWebhookMessage || event.author.isBot) {
            // Ignore bots and webhooks
            return
        }
        bot.process(OnMessageEvent(DiscordMessage(event.message)))
    }

    override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
        if (event.user!!.isBot) {
            // Ignore bots
            return
        }
        if (event.reaction.reactionEmote.isEmote) {
            bot.process(OnReactionAddedEvent(DiscordMessageReaction(DiscordMessage(event.channel.retrieveMessageById(event.messageId).complete()),
                DiscordReaction(event.reaction.reactionEmote.emote), event.reaction.count), DiscordUser(event.user!!)))
        } else {
            bot.process(OnReactionAddedEvent(DiscordMessageReaction(DiscordMessage(event.channel.retrieveMessageById(event.messageId).complete()),
                UnicodeEmote(event.reaction.reactionEmote.name), event.reaction.count), DiscordUser(event.user!!)))
        }
    }

    override fun onMessageDelete(event: MessageDeleteEvent) {
        bot.process(OnMessageRemovedEvent(DiscordChannel(event.channel), event.messageId))
    }
}
