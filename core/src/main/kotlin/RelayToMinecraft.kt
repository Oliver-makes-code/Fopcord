package de.olivermakesco.fopcord

interface RelayToMinecraft {
    fun sendMessageAsPlayer(color: Int, username: String, content: String)
}
