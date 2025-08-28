package de.olivermakesco.fopcord.mc1218.fabric

import de.olivermakesco.fopcord.mc1218.Events
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents
import net.fabricmc.loader.api.FabricLoader

fun init() {
    Events.init(FabricLoader.getInstance().configDir.toString())

    ServerLifecycleEvents.SERVER_STARTING.register(Events::serverStarting)
    ServerLifecycleEvents.SERVER_STARTED.register(Events::serverStarted)
    ServerLifecycleEvents.SERVER_STOPPING.register(Events::serverStopping)
    ServerLifecycleEvents.SERVER_STOPPED.register(Events::serverStopped)

    ServerMessageEvents.CHAT_MESSAGE.register(Events::messageSent)

    ServerPlayerEvents.JOIN.register(Events::playerJoined)
    ServerPlayerEvents.LEAVE.register(Events::playerLeft)
}
