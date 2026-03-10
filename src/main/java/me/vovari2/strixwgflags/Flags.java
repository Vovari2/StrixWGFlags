package me.vovari2.strixwgflags;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.entity.EntityType;
import com.sk89q.worldedit.world.item.ItemType;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.RegistryFlag;
import com.sk89q.worldguard.protection.flags.SetFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.Location;

import java.util.Set;

public class Flags {
    private static SetFlag<ItemType> DENY_DROP_ENTITIES;
    private static SetFlag<ItemType> DENY_DROP_BLOCKS;
    private static SetFlag<EntityType> ALLOW_SPAWN;

    public static void loadFlags(){
        FlagRegistry flagRegistry = com.sk89q.worldguard.WorldGuard.getInstance().getFlagRegistry();

        SetFlag<ItemType> itemFlag;
        SetFlag<EntityType> entityFlag;

        itemFlag = new SetFlag<>(
                "deny-drop-entities",
                new RegistryFlag<>(null, ItemType.REGISTRY));
        flagRegistry.register(itemFlag);
        DENY_DROP_ENTITIES = itemFlag;

        itemFlag = new SetFlag<>(
                "deny-drop-blocks",
                new RegistryFlag<>(null, ItemType.REGISTRY));
        flagRegistry.register(itemFlag);
        DENY_DROP_BLOCKS = itemFlag;

        entityFlag = new SetFlag<>(
                "allow-spawn",
                new RegistryFlag<>(null, EntityType.REGISTRY));
        flagRegistry.register(entityFlag);
        ALLOW_SPAWN = entityFlag;
    }

    public static Set<ItemType> getItemsInFlagOfDenyDropEntities(Location location){
        return getApplicableRegionSet(location)
                .queryValue(null, DENY_DROP_ENTITIES);
    }
    public static Set<ItemType> getItemsInFlagOfDenyDropBlocks(Location location){
        return getApplicableRegionSet(location)
                .queryValue(null, DENY_DROP_BLOCKS);
    }
    public static Set<EntityType> getEntitiesInFlagOfAllowSpawn(Location location){
        return getApplicableRegionSet(location)
                .queryValue(null, ALLOW_SPAWN);
    }

    private static ApplicableRegionSet getApplicableRegionSet(Location location){
        return StrixWGFlags.getWorldGuardPlatform().
                getRegionContainer().
                createQuery().
                getApplicableRegions(BukkitAdapter.adapt(location));
    }
}
