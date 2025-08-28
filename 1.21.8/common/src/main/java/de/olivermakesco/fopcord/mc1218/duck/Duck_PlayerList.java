package de.olivermakesco.fopcord.mc1218.duck;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Predicate;

public interface Duck_PlayerList {
    void fopcord$broadcastChatMessage(PlayerChatMessage message, Predicate<ServerPlayer> shouldFilterMessageTo, ServerPlayer sender, ChatType.Bound boundChatType);
}
