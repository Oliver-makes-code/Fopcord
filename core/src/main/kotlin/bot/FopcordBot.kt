package de.olivermakesco.fopcord.bot

import de.olivermakesco.fopcord.Config
import de.olivermakesco.fopcord.RelayToDiscord
import de.olivermakesco.fopcord.relayToMinecraft
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.execute
import dev.kord.core.entity.Webhook
import dev.kord.core.entity.channel.MessageChannel
import dev.kord.core.entity.channel.TextChannel
import dev.kord.core.entity.effectiveName
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

@OptIn(DelicateCoroutinesApi::class)
class FopcordBot(val config: Config) : RelayToDiscord {
    private val bot: Kord = runBlocking { Kord(config.token) }
    private val channel: MessageChannelBehavior by lazy { runBlocking {
        bot.getChannel(config.effectiveChannelId) as MessageChannelBehavior
    } }
    private val webhook: Webhook? by lazy { runBlocking {
        if (config.webhookId == null)
            null
        else
            bot.getWebhook(config.webhookId)
    } }

    init {
        bot.on<MessageCreateEvent> {
            if (message.author?.isBot != false || message.channelId != config.effectiveChannelId)
                return@on

            val member = message.getAuthorAsMember()

            val color = member.roles.firstOrNull { it.color.rgb != 0 }?.color?.rgb ?: 0xFFFFFF

            relayToMinecraft.sendMessageAsPlayer(color, member.effectiveName, message.content)
        }

        GlobalScope.launch {
            bot.login {
                @OptIn(PrivilegedIntent::class)
                intents += Intent.MessageContent
            }
        }
    }

    override fun sendSystemMessage(message: String) {
        GlobalScope.launch {
            channel.createMessage(message)
        }
    }

    fun avatarUrl(uuid: UUID): String {
        return "https://vzge.me/head/512/$uuid.png?no=shadow,cape,helmet,overlay&y=70"
    }

    override fun sendMessage(player: UUID, username: String, message: String) {
        GlobalScope.launch {
            if (webhook != null) {
                webhook!!.execute(config.webhookToken!!, config.threadId) {
                    this.username = username
                    this.content = message
                    this.avatarUrl = avatarUrl(player)
                }
            } else {
                channel.createMessage("<$username> $message")
            }
        }
    }

    override fun shutdown() {
        runBlocking {
            bot.shutdown()
        }
    }
}
