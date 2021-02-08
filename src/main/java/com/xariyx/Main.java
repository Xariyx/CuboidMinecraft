package com.xariyx;

import com.xariyx.Commands.CommandCuboid;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Set;


public final class Main extends JavaPlugin {


    private File cuboidsFile;
    private YamlConfiguration cuboidsConfig;

    @Override
    public void onEnable() {
        loadCommands();
        loadListeners();

        cuboidsFile = new File(getDataFolder(), "cuboids.yml");
        try {
            createCustomConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        cuboidsConfig = YamlConfiguration.loadConfiguration(cuboidsFile);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    private Cuboid[] getActiveCuboids(FileConfiguration cuboidsFile) {


        Set<String> keys = cuboidsFile.getKeys(false);
        String[] uuids = (String[]) keys.toArray();

        Cuboid[] activeCuboids = new Cuboid[keys.size()];
        for (int i = 0; i < activeCuboids.length; i++) {

            activeCuboids[i] = new Cuboid(cuboidsFile, uuids[i]);
        }

        return activeCuboids;
    }


    private void loadCommands() {
        PluginCommand commandCuboid = this.getCommand("cuboid");
        if (commandCuboid != null) {
            commandCuboid.setExecutor(new CommandCuboid(this));
        }


    }

    private void loadListeners() {
        getServer().getPluginManager().registerEvents(new CuboidListeners(getActiveCuboids(cuboidsConfig)), this);
    }


    public void createCustomConfig() throws IOException {
        if (!cuboidsFile.exists()) {
            boolean bool = cuboidsFile.createNewFile();
        }

    }

    public FileConfiguration getCuboidsConfig() {
        return this.cuboidsConfig;
    }

}
