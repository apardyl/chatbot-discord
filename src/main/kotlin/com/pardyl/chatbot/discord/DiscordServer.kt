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
            manager.kick(user.id)
        } else {
            throw IllegalArgumentException("Not a discord user")
        }
    }

    override fun getReactionForId(id: String?): Reaction {
        return DiscordReaction(guild.getEmoteById(id))
    }

    override fun getRoleForName(name: String?): Role {
        return DiscordRole(guild.getRolesByName(name, false).elementAtOrNull(0)!!)
    }

    override fun getChannelForName(name: String?): Channel {
        return DiscordChannel(guild.getTextChannelsByName(name, false).elementAtOrNull(0)!!)
    }

    override fun getUserForId(id: String?): User {
        return DiscordUser(guild.getMemberById(id).user)
    }

    override fun getReactionForName(name: String?): Reaction {
        return DiscordReaction(guild.getEmotesByName(name, false).elementAtOrNull(0)!!)
    }

    override fun getRoleForId(id: String?): Role {
        return DiscordRole(guild.getRoleById(id))
    }

    override fun getUserForName(name: String?): User {
        return DiscordUser(guild.getMembersByName(name, false).getOrNull(0)!!.user)
    }

    override fun getChannelForId(id: String?): Channel {
        return DiscordChannel(guild.getTextChannelById(id))
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
        if (user is DiscordUser) {
            val onlineStatus = listOf(OnlineStatus.ONLINE, OnlineStatus.DO_NOT_DISTURB,
                    OnlineStatus.IDLE, OnlineStatus.INVISIBLE)
            return onlineStatus.contains(guild.getMemberById(user.id).onlineStatus)
        } else {
            throw IllegalArgumentException("Not a discord user")
        }
    }

    override fun getRolesForUser(user: User?): List<Role> {
        if (user is DiscordUser) {
            return guild.getMemberById(user.id).roles.map { role -> DiscordRole(role) }
        } else {
            throw IllegalArgumentException("Not a discord user")
        }
    }

    override fun isMember(user: User?): Boolean {
        return when (user) {
            is DiscordUser -> guild.isMember(user.discordUser)
            else -> false
        }
    }

    override fun hasRole(user: User?, role: Role?): Boolean {
        return if (role !is DiscordRole) {
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
