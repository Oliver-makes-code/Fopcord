package de.olivermakesco.fopcord.mc1218.fabric.mixin;

import de.olivermakesco.fopcord.mc1218.duck.Duck_PlayerList;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Predicate;

@Mixin(PlayerList.class)
public abstract class Mixin_PlayerList implements Duck_PlayerList {
    @Shadow protected abstract void broadcastChatMessage(PlayerChatMessage playerChatMessage, Predicate<ServerPlayer> predicate, @Nullable ServerPlayer serverPlayer, ChatType.Bound bound);

    @Override
    public void fopcord$broadcastChatMessage(PlayerChatMessage message, Predicate<ServerPlayer> shouldFilterMessageTo, ServerPlayer sender, ChatType.Bound boundChatType) {
        broadcastChatMessage(message, shouldFilterMessageTo, sender, boundChatType);
    }
}
