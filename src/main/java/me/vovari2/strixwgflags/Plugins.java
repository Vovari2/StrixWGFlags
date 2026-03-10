package me.vovari2.strixwgflags;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public enum Plugins {
    WorldGuard;

    static void load(){
        PluginManager manager = StrixWGFlags.getInstance().getServer().getPluginManager();

        // WorldGuard
        Plugin plugin = manager.getPlugin(WorldGuard.name());
        if (plugin != null){
            WorldGuard.loadInside();
            Flags.loadFlags();
        }
    }
    static void enable(){
        if (WorldGuard.loaded)
            WorldGuard.enableInside();
    }

    private Plugin plugin;
    private boolean loaded = false;
    private boolean enabled = false;
    public boolean isEnabled(){
        return enabled;
    }
    void loadInside(){
        this.loaded = true;
        Console.info("Found %s plugin!".formatted(name()));
    }
    void enableInside(){
        this.enabled = true;
        Console.info("Connected %s plugin!".formatted(name()));
    }
}
