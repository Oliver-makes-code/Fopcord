package de.olivermakesco.fopcord

import java.util.UUID

interface RelayToDiscord {
    fun sendSystemMessage(message: String)
    fun sendMessage(player: UUID, username: String, message: String)
    fun shutdown()
}
