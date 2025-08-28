package de.olivermakesco.fopcord.mc1218

import de.olivermakesco.fopcord.initialize
import de.olivermakesco.fopcord.relayToDiscord
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.PlayerChatMessage
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

object Events {
    fun init(configPath: String) {
        initialize(MinecraftServerRelay, configPath)
    }

    fun serverStarting(server: MinecraftServer) {
        relayToDiscord.sendSystemMessage("**Server Starting**")
    }

    fun serverStarted(server: MinecraftServer) {
        relayToDiscord.sendSystemMessage("**Server Started**")
        MinecraftServerRelay.server = server
    }

    fun serverStopping(server: MinecraftServer) {
        relayToDiscord.sendSystemMessage("**Server Stopping**")
        MinecraftServerRelay.server = null
    }

    fun serverStopped(server: MinecraftServer) {
        relayToDiscord.sendSystemMessage("**Server Stopped**")

        relayToDiscord.shutdown()
    }

    fun messageSent(message: PlayerChatMessage, player: ServerPlayer, bound: ChatType.Bound) {
        relayToDiscord.sendMessage(player.uuid, player.displayName!!.string, message.decoratedContent().string)
    }

    fun playerJoined(player: ServerPlayer) {
        relayToDiscord.sendSystemMessage("${player.displayName!!.string} joined the game.")
    }

    fun playerLeft(player: ServerPlayer) {
        relayToDiscord.sendSystemMessage("${player.displayName!!.string} left the game.")
    }
}
