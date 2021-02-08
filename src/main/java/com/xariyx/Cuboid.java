package com.xariyx;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class Cuboid {

    private String name;
    private Location start;
    private Location end;
    private Boolean entityGetDamage;
    private Boolean entityDealDamage;
    private Boolean entityDestroyBlocks;
    private Boolean entityInteract;
    private ArrayList<UUID> ownerGroup;


    public Cuboid(String name, Location start, Location end, FileConfiguration cuboidConfigFile, Player owner) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.ownerGroup = new ArrayList<>(1);
        ownerGroup.add(owner.getUniqueId());

        entityDestroyBlocks = cuboidConfigFile.getBoolean("basicCuboid.entityDestroyBlocks");
        entityDealDamage = cuboidConfigFile.getBoolean("basicCuboid.entityDealDamage");
        entityGetDamage = cuboidConfigFile.getBoolean("basicCuboid.entityGetDamage");
        entityInteract = cuboidConfigFile.getBoolean("basicCuboid.entityInteract");
    }


    public Cuboid(FileConfiguration config, String name) {
        getDataFromConfig(config, name);
    }


    public void saveDataToConfig(FileConfiguration config) {
        config.set(getName() + ".start", getStart());
        config.set(getName() + ".end", getEnd());
        config.set(getName() + ".entityDestroyBlocks", getEntityDestroyBlocks());
        config.set(getName() + ".entityDealDamage", getEntityDealDamage());
        config.set(getName() + ".entityGetDamage", getEntityGetDamage());
        config.set(getName() + ".entityInteract", getEntityInteract());
        config.set(getName() + ".ownerGroup", getOwnerGroup());
    }


    public void getDataFromConfig(FileConfiguration config, String name) {
        setStart((Location) config.get(name + ".start", getStart()));
        setEnd((Location) config.get(name + ".end", getEnd()));
        setEntityDestroyBlocks(config.getBoolean(name + ".entityDestroyBlocks", getEntityDestroyBlocks()));
        setEntityDealDamage(config.getBoolean(name + ".entityDealDamage", getEntityDealDamage()));
        setEntityGetDamage(config.getBoolean(name + ".entityGetDamage", getEntityGetDamage()));
        setEntityInteract(config.getBoolean(name + ".entityInteract", getEntityInteract()));

        List<?> temp = config.getList(name + ".ownerGroup", getOwnerGroup());
        ArrayList<UUID> temp2 = new ArrayList<>();
        if (temp != null) {
            for(Object id:temp)
                temp2.add((UUID) id);
        }
        setOwnerGroup(temp2);
    }


    public boolean isInside(Location location) {

        Location start = getStart();
        Location end = getEnd();

        boolean x = location.getX() > Math.min(start.getX(), end.getX())
                &&  location.getX() < Math.max(start.getX(), end.getX());

        boolean y = location.getY() > Math.min(start.getY(), end.getY())
                &&  location.getY() < Math.max(start.getY(), end.getY());

        boolean z = location.getZ() > Math.min(start.getZ(), end.getZ())
                &&  location.getZ() < Math.max(start.getZ(), end.getZ());

        return x && y && z;
    }



    public ArrayList<UUID> getOwnerGroup() {
        return ownerGroup;
    }

    public void setOwnerGroup(Collection<UUID> collection) {
        this.ownerGroup.clear();
        this.ownerGroup.addAll(collection);
    }

    public void addOwner(UUID playersUUID){
        ownerGroup.add(playersUUID);

    }

    public String getName() {
        return name;
    }


    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getEnd() {
        return end;
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    public Boolean getEntityGetDamage() {
        return entityGetDamage;
    }

    public void setEntityGetDamage(Boolean entityGetDamage) {
        this.entityGetDamage = entityGetDamage;
    }

    public Boolean getEntityDealDamage() {
        return entityDealDamage;
    }

    public void setEntityDealDamage(Boolean entityDealDamage) {
        this.entityDealDamage = entityDealDamage;
    }

    public Boolean getEntityDestroyBlocks() {
        return entityDestroyBlocks;
    }

    public void setEntityDestroyBlocks(Boolean entityDestrotBlocks) {
        this.entityDestroyBlocks = entityDestrotBlocks;
    }

    public Boolean getEntityInteract() {
        return entityInteract;
    }

    public void setEntityInteract(Boolean entityInteract) {
        this.entityInteract = entityInteract;
    }
}
