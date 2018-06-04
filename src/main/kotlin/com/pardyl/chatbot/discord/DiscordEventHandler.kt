package com.pardyl.chatbot.discord

import com.pardyl.chatbot.core.events.OnMessageEvent
import com.pardyl.chatbot.core.events.OnReactionAddedEvent
import net.dv8tion.jda.core.events.ReadyEvent
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

internal class DiscordEventHandler(private val bot: DiscordBotInstance) : ListenerAdapter() {
    override fun onReady(event: ReadyEvent?) {
        // Do not call API at this point!
    }

    override fun onMessageReceived(event: MessageReceivedEvent?) {
        if (event!!.isWebhookMessage || event.author.isBot) {
            // Ignore bots and webhooks
            return
        }
        bot.process(OnMessageEvent(DiscordMessage(event.message)))
    }

    override fun onMessageReactionAdd(event: MessageReactionAddEvent?) {
        if (event!!.user.isBot) {
            // Ignore bots
            return
        }
        if (event.reaction.reactionEmote.isEmote) {
            bot.process(OnReactionAddedEvent(DiscordMessage(event.channel.getMessageById(event.messageId).complete()),
                    DiscordReaction(event.reaction.reactionEmote.emote)))
        }
        // TODO: handle unicode reactions
    }
}
