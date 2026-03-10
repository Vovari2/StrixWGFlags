package me.vovari2.strixwgflags;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.entity.EntityType;
import com.sk89q.worldedit.world.item.ItemType;
import com.sk89q.worldguard.config.WorldConfiguration;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Set;

public class Listeners implements Listener {

    @EventHandler
    public void denyDropItemToEntityDeath(EntityDeathEvent event){
        Entity entity = event.getEntity();
        WorldConfiguration worldConfiguration = getWorldConfiguration(entity.getWorld());
        if (!worldConfiguration.useRegions)
            return;

        Location location = entity.getLocation();
        Set<ItemType> itemTypes = Flags.getItemsInFlagOfDenyDropEntities(location);
        if (itemTypes == null)
            return;

        event.getDrops().removeIf(itemStack -> itemTypes.contains(BukkitAdapter.asItemType(itemStack.getType())));
    }

    @EventHandler
    public void denyDropItemsToBreakBlock(BlockDropItemEvent event){
        Block block = event.getBlock();
        WorldConfiguration worldConfiguration = getWorldConfiguration(block.getWorld());
        if (!worldConfiguration.useRegions)
            return;

        Set<ItemType> itemTypes = Flags.getItemsInFlagOfDenyDropBlocks(block.getLocation());
        if (itemTypes == null)
            return;

        event.getItems().removeIf(itemFromBlock -> itemTypes.contains(BukkitAdapter.asItemType(itemFromBlock.getItemStack().getType())));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void allowEntityToEntitySpawn(EntitySpawnEvent event){
        if (!event.isCancelled())
            return;

        Entity entity = event.getEntity();
        WorldConfiguration worldConfiguration = getWorldConfiguration(entity.getWorld());
        if (!worldConfiguration.useRegions)
            return;

        Set<EntityType> entityTypes = Flags.getEntitiesInFlagOfAllowSpawn(entity.getLocation());
        if (entityTypes == null)
            return;

        EntityType entityType = EntityType.REGISTRY.get(entity.getType().key().toString());
        if (entityType == null){
            Console.warn("Обнаружена сущность, которая не обнаружена в WorldGuard! (entity=%s)".formatted(entity.getType().key()));
            return;
        }

        if (!entityTypes.contains(entityType))
            return;

        event.setCancelled(false);
    }

    private WorldConfiguration getWorldConfiguration(World world){
        return StrixWGFlags.getWorldGuardPlatform().
                getGlobalStateManager().
                get(BukkitAdapter.adapt(world));
    }
}

