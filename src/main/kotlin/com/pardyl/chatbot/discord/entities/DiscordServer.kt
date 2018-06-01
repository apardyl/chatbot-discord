package com.pardyl.chatbot.discord.entities

import com.pardyl.chatbot.core.BotInstance
import com.pardyl.chatbot.core.entities.*
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.managers.GuildController

class DiscordServer(private val guild: Guild) : Server() {
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
        val manager = GuildController(guild)
        manager.kick(user!!.id)
    }
}
