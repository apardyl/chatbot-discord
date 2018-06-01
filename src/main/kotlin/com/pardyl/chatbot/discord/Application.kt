package com.pardyl.chatbot.discord

import com.pardyl.chatbot.core.BotConfiguration
import java.io.FileInputStream
import java.net.URL
import java.net.URLClassLoader
import java.util.*

class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val properties = Properties()
            val propFile = FileInputStream(args.elementAtOrElse(0, { "bot.properties" }))
            properties.load(propFile)
            propFile.close()

            val url = URL(properties["CONFIG"] as String)
            val loader = URLClassLoader(arrayOf(url))
            val confClass = Class.forName(properties["CONFIG_CLASS"] as String, true, loader)
            val config = confClass.getConstructor().newInstance() as BotConfiguration
            val bot = DiscordBotInstance(config, properties["TOKEN"] as String, properties)
            bot.run()
        }
    }
}
