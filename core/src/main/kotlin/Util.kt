package de.olivermakesco.fopcord

import fish.cichlidmc.tinycodecs.Codec
import fish.cichlidmc.tinyjson.TinyJson
import java.io.File

fun File.tryCreate(contentProvider: () -> String) {
    println(path)

    if (exists())
        return

    createNewFile()

    writeText(contentProvider())
}

fun <T> File.tryReadCodec(codec: Codec<T>, default: T): T {
    tryCreate {
        codec.encode(default).orThrow.toString()
    }

    return codec.decode(TinyJson.parse(this)).orThrow
}
