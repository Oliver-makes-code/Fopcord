package de.olivermakesco.fopcord.mc1218

import de.olivermakesco.fopcord.RelayToMinecraft
import de.olivermakesco.fopcord.mc1218.duck.Duck_PlayerList
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.OutgoingChatMessage
import net.minecraft.network.chat.PlayerChatMessage
import net.minecraft.server.MinecraftServer

object MinecraftServerRelay : RelayToMinecraft {
    var server: MinecraftServer? = null

    override fun sendMessageAsPlayer(color: Int, username: String, content: String) {
        server ?: return

        (server!!.playerList as Duck_PlayerList).`fopcord$broadcastChatMessage`(PlayerChatMessage
            .system(content),
            {false},
            null,
            ChatType.bind(ChatType.CHAT, server!!.registryAccess(), Component.literal(username).withColor(color))
        )
    }
}
