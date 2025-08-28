package de.olivermakesco.fopcord

import dev.kord.common.entity.Snowflake
import fish.cichlidmc.tinycodecs.Codec
import fish.cichlidmc.tinycodecs.codec.map.CompositeCodec
import java.io.File
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

data class Config(
    val token: String,
    val channelIdRaw: String,
    val threadIdRaw: Optional<String>,
    val webhookUrl: Optional<String>
) {
    val threadId = threadIdRaw.map({Snowflake(it)}).getOrNull()

    val effectiveChannelId = threadId ?: Snowflake(channelIdRaw)

    val webhookId: Snowflake?
    val webhookToken: String?

    init {
        if (webhookUrl.isPresent) {
            val split = webhookUrl.get().split("/")

            webhookId = Snowflake(split[5])
            webhookToken = split[6]
        } else {
            webhookId = null
            webhookToken = null
        }
    }

    companion object {
        val codec: Codec<Config> = CompositeCodec.of(
            Codec.STRING.fieldOf("token"), Config::token,
            Codec.STRING.fieldOf("channel_id"), Config::channelIdRaw,
            Codec.STRING.optional().fieldOf("thread_id"), Config::threadIdRaw,
            Codec.STRING.optional().fieldOf("webhook_url"), Config::webhookUrl,
            ::Config
        ).asCodec()

        val default: Config = Config(
            "TOKEN GOES HERE",
            "0",
            Optional.empty(),
            Optional.empty()
        )

        fun load(configPath: String): Config {
            return File(configPath).tryReadCodec(codec, default)
        }
    }
}
