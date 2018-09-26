package com.pardyl.chatbot.discord

import com.pardyl.chatbot.core.BotInstance
import com.pardyl.chatbot.core.entities.*
import net.dv8tion.jda.core.OnlineStatus
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.managers.GuildController

internal class DiscordServer(private val guild: Guild) : Server() {
    override fun getName(): String {
        return guild.name
    }

    override fun getId(): String {
        return guild.id
    }

    override fun getChannels(): List<Channel> {
        return guild.textChannels.map { txt -> DiscordChannel(txt) }
    }

    override fun getUsers(): List<User> {
        return guild.members.map { member -> DiscordUser(member.user) }
    }

    override fun getRoles(): List<Role> {
        return guild.roles.map { role -> DiscordRole(role) }
    }

    override fun getReactions(): List<Reaction> {
        return guild.emotes.map { emote -> DiscordReaction(emote) }
    }

    override fun kickUser(user: User?, bot: BotInstance?) {
        if (user is DiscordUser) {
            val manager = GuildController(guild)
            manager.kick(user.id).complete()
        } else {
            throw IllegalArgumentException("Not a discord user")
        }
    }

    override fun getReactionForId(id: String?): Reaction? {
        return guild.getEmoteById(id)?.let { DiscordReaction(it) }
    }

    override fun getRoleForName(name: String?): Role? {
        return guild.getRolesByName(name, false).elementAtOrNull(0)?.let { DiscordRole(it) }
    }

    override fun getChannelForName(name: String?): Channel? {
        return guild.getTextChannelsByName(name, false).elementAtOrNull(0)?.let { DiscordChannel(it) }
    }

    override fun getUserForId(id: String?): User? {
        return guild.getMemberById(id).user?.let { DiscordUser(it) }
    }

    override fun getReactionForName(name: String?): Reaction? {
        return guild.getEmotesByName(name, false).elementAtOrNull(0)?.let { DiscordReaction(it) }
    }

    override fun getRoleForId(id: String?): Role? {
        return guild.getRoleById(id)?.let { DiscordRole(it) }
    }

    override fun getUserForName(name: String?): User? {
        return guild.getMembersByName(name, false).getOrNull(0)?.user?.let { DiscordUser(it) }
    }

    override fun getChannelForId(id: String?): Channel? {
        return guild.getTextChannelById(id)?.let { DiscordChannel(it) }
    }

    override fun banUser(user: User?, bot: BotInstance?) {
        if (user is DiscordUser) {
            val manager = GuildController(guild)
            manager.ban(user.id, 0)
        } else {
            throw IllegalArgumentException("Not a discord user")
        }
    }

    override fun getUsersForRole(role: Role?): List<User> {
        return if (role is DiscordRole) {
            guild.getMembersWithRoles(role.discordRole).map { member -> DiscordUser(member.user) }
        } else listOf()
    }

    override fun isUserOnline(user: User?): Boolean {
        return when (user) {
            null -> false
            is DiscordUser -> {
                val onlineStatus = listOf(OnlineStatus.ONLINE, OnlineStatus.DO_NOT_DISTURB,
                        OnlineStatus.IDLE, OnlineStatus.INVISIBLE)
                onlineStatus.contains(guild.getMemberById(user.id).onlineStatus)
            }
            else -> throw IllegalArgumentException("Not a discord user")
        }
    }

    override fun getRolesForUser(user: User?): List<Role> {
        return when (user) {
            null -> listOf()
            is DiscordUser -> guild.getMemberById(user.id).roles.map { role -> DiscordRole(role) }
            else -> throw IllegalArgumentException("Not a discord user")
        }
    }

    override fun isMember(user: User?): Boolean {
        return when (user) {
            is DiscordUser -> guild.isMember(user.discordUser)
            else -> false
        }
    }

    override fun hasRole(user: User?, role: Role?): Boolean {
        return if (user == null || role == null) {
            false
        } else if (role !is DiscordRole) {
            throw IllegalArgumentException("Not a discord role")
        } else {
            when (user) {
                is DiscordUser -> guild.getMemberById(user.id)
                        .roles.contains(role.discordRole)
                else -> false
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DiscordServer

        if (guild != other.guild) return false

        return true
    }

    override fun hashCode(): Int {
        return guild.hashCode()
    }
}
