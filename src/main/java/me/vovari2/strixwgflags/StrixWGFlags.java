package me.vovari2.strixwgflags;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class StrixWGFlags extends JavaPlugin {
    private static StrixWGFlags INSTANCE;

    private String PLUGIN_NAME;
    private String VERSION;
    private String AUTHOR;

    private WorldGuardPlatform PLATFORM;

    public void onLoad() {
        INSTANCE = this;
        Console.LOGGER = getComponentLogger();

        PLUGIN_NAME = INSTANCE.getName();
        VERSION = INSTANCE.getPluginMeta().getVersion();
        AUTHOR = INSTANCE.getPluginMeta().getAuthors().getFirst();

        Plugins.load();
    }
    public void onEnable() {
        long time = System.currentTimeMillis();

        Plugins.enable();
        PLATFORM = WorldGuard.getInstance().getPlatform();

        registerListeners();
        Console.info("<green>Plugin %s %s enabled! (%d ms)".formatted(PLUGIN_NAME, VERSION, System.currentTimeMillis() - time));
    }

    public void onDisable() {
        unregisterListeners();
        Console.info("<red>Plugin %s %s disabled!".formatted(PLUGIN_NAME, VERSION));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new Listeners(), this);
    }
    private void unregisterListeners() {
        HandlerList.unregisterAll(this);
    }

    public static StrixWGFlags getInstance() {
        return INSTANCE;
    }
    public static WorldGuardPlatform getWorldGuardPlatform(){
        return INSTANCE.PLATFORM;
    }
}
