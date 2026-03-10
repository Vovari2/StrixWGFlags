package me.vovari2.strixwgflags;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Console {
    static ComponentLogger LOGGER;

    public static void info(String message){
        LOGGER.info(MiniMessage.miniMessage().deserialize(message));
    }
    public static void warn(String message){
        LOGGER.warn(MiniMessage.miniMessage().deserialize(message));
    }
}
