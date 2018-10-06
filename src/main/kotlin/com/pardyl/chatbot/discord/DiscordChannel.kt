package com.pardyl.chatbot.discord

import com.pardyl.chatbot.core.BotInstance
import com.pardyl.chatbot.core.entities.*
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.PrivateChannel
import net.dv8tion.jda.core.entities.TextChannel
import java.io.File
import java.io.InputStream
import java.lang.UnsupportedOperationException

internal class DiscordChannel(private val channel: MessageChannel) : Channel() {
    override fun getName(): String {
        return channel.name
    }

    override fun getId(): String {
        return channel.id
    }

    override fun getServer(): Server? {
        if (channel is TextChannel) {
            return DiscordServer(channel.guild)
        }
        // Fallback for private channels
        return object : Server() {
            override fun getRolesForUser(user: User?): List<Role> {
                return listOf()
            }

            override fun isUserOnline(user: User?): Boolean {
                return false
            }

            override fun getName(): String {
                return channel.name
            }

            override fun getId(): String {
                return channel.id
            }

            override fun banUser(user: User?, bot: BotInstance?) {
                throw UnsupportedOperationException()
            }

            override fun getUsersForRole(role: Role?): List<User> {
                return listOf()
            }

            override fun kickUser(user: User?, bot: BotInstance?) {
                throw UnsupportedOperationException()
            }

            override fun getUsers(): List<User> {
                if (channel is PrivateChannel) {
                    return listOf(DiscordUser(channel.jda.selfUser), DiscordUser(channel.user))
                }
                return listOf(DiscordUser(channel.jda.selfUser))
            }

            override fun getReactions(): List<Reaction> {
                return listOf()
            }

            override fun getChannels(): List<Channel> {
                return listOf(this@DiscordChannel)
            }

            override fun getRoles(): List<Role> {
                return listOf()
            }

        }
    }

    override fun getMembers(): List<User> {
        if (channel is TextChannel) {
            return channel.members.map { member -> DiscordUser(member.user) }
        }
        return listOf()
    }

    override fun sendMessage(message: Message?, bot: BotInstance?) {
        if (message is DiscordMessage) {
            channel.sendMessage(message.discordMessage).complete()
        } else {
            throw IllegalArgumentException("Not a discord message")
        }
    }

    override fun sendFile(file: File?, uploadName: String?, message: Message?, bot: BotInstance?) {
        if (message == null || message is DiscordMessage) {
            channel.sendFile(file, uploadName, (message as DiscordMessage?)?.discordMessage).complete()
        } else {
            throw IllegalArgumentException("Not a discord message")
        }
    }

    override fun sendFile(data: InputStream?, uploadName: String?, message: Message?, bot: BotInstance?) {
        if (message == null || message is DiscordMessage) {
            channel.sendFile(data, uploadName, (message as DiscordMessage?)?.discordMessage).complete()
        } else {
            throw IllegalArgumentException("Not a discord message")
        }
    }

    override fun sendIsTyping(bot: BotInstance?) {
        channel.sendTyping().complete()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DiscordChannel

        if (channel != other.channel) return false

        return true
    }

    override fun hashCode(): Int {
        return channel.hashCode()
    }
}
