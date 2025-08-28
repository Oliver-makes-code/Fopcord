package de.olivermakesco.fopcord

import de.olivermakesco.fopcord.bot.FopcordBot

val modid = "fopbot"
val configFilename = "fopbot.json"

private lateinit var bot: FopcordBot
val relayToDiscord: RelayToDiscord
    get() = bot
lateinit var relayToMinecraft: RelayToMinecraft private set

fun initialize(
    relay: RelayToMinecraft,
    configPath: String
) {
    relayToMinecraft = relay
    bot = FopcordBot(Config.load("$configPath/$configFilename"))
}
