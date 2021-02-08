package com.xariyx;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CuboidListeners implements Listener {


    Cuboid[] activeCuboids;

    CuboidListeners(Cuboid[] activeCuboids) {
        this.activeCuboids = activeCuboids;

    }


    @EventHandler
    private void onCuboidEntityGetDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        for (Cuboid cuboid : activeCuboids) {
            if (!cuboid.isInside(entity.getLocation())) {
                continue;
            }

            if (cuboid.getEntityGetDamage()) {
                continue;
            }

            if (!cuboid.getOwnerGroup().contains(entity.getUniqueId())) {
                event.setCancelled(true);
                break;
            }
        }
    }


    @EventHandler
    private void onCuboidEntityDealDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();


        for (Cuboid cuboid : activeCuboids) {
            if (!cuboid.isInside(entity.getLocation())) {
                continue;
            }

            if (cuboid.getEntityDealDamage()) {
                continue;
            }

            if (!cuboid.getOwnerGroup().contains(entity.getUniqueId())) {
                event.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    private void onCuboidBlockDestroyed(BlockBreakEvent event) {
        Block block = event.getBlock();

        for (Cuboid cuboid : activeCuboids) {
            if (!cuboid.isInside(block.getLocation())) {
                continue;
            }

            if (cuboid.getEntityDestroyBlocks()) {
                continue;
            }

            if (!cuboid.getOwnerGroup().contains(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
                break;
            }
        }
    }


    @EventHandler
    private void onCuboidBlockBurnt(BlockBurnEvent event) {
        Block block = event.getBlock();

        for (Cuboid cuboid : activeCuboids) {
            if (!cuboid.isInside(block.getLocation())) {
                continue;
            }

            if (cuboid.getEntityDestroyBlocks()) {
                continue;
            }

            event.setCancelled(true);
            break;
        }
    }


    @EventHandler
    private void onCuboidBlockExploded(BlockExplodeEvent event) {
        Block block = event.getBlock();

        for (Cuboid cuboid : activeCuboids) {
            if (!cuboid.isInside(block.getLocation())) {
                continue;
            }

            if (cuboid.getEntityDestroyBlocks()) {
                continue;
            }

            event.setCancelled(true);
            break;
        }
    }

    @EventHandler
    private void onCuboidBlockPlaced(BlockPlaceEvent event) {
        Block block = event.getBlock();

        for (Cuboid cuboid : activeCuboids) {
            if (!cuboid.isInside(block.getLocation())) {
                continue;
            }

            if (cuboid.getEntityDestroyBlocks()) {
                continue;
            }

            if (!cuboid.getOwnerGroup().contains(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
                break;
            }
        }
    }

}
