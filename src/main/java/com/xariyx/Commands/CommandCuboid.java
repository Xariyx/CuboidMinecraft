package com.xariyx.Commands;

import com.xariyx.Cuboid;
import com.xariyx.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Set;

public class CommandCuboid implements CommandExecutor {

    FileConfiguration pluginConfig;
    FileConfiguration cuboidConfig;

    public CommandCuboid(Main main) {
        this.pluginConfig = main.getConfig();
        this.cuboidConfig = main.getCuboidsConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        Set<String> keys = cuboidConfig.getKeys(false);
        World world = player.getWorld();

        //new, edit, delete, list, info

        if (args[0].equalsIgnoreCase("new")) {


            String cuboidName = args[1];

            if (keys.contains(cuboidName)) {
                String message = pluginConfig.getString("messages.nameInUse");
                if (message != null) {
                    ChatColor.translateAlternateColorCodes('&', message);
                    player.sendMessage(message);
                    return true;
                }
                return false;
            }


            double startX = Double.parseDouble(args[2]);
            double startY = Double.parseDouble(args[3]);
            double startZ = Double.parseDouble(args[4]);
            double endX = Double.parseDouble(args[5]);
            double endY = Double.parseDouble(args[6]);
            double endZ = Double.parseDouble(args[7]);

            Location start = new Location(world, startX, startY, startZ);
            Location end = new Location(world, endX, endY, endZ);
            Cuboid cuboid = new Cuboid(cuboidName, start, end, pluginConfig, player);

            cuboid.saveDataToConfig(cuboidConfig);
            return true;


        } else if (args[0].equalsIgnoreCase("edit")) {

            String cuboidName = args[1];
            if (!keys.contains(cuboidName)) {
                String message = pluginConfig.getString("messages.cuboidNotExists");
                if (message != null) {
                    message = ChatColor.translateAlternateColorCodes('&', message);
                    player.sendMessage(message);
                    return true;
                }
                return false;
            }

            String message = pluginConfig.getString("messages.editSuccessful");
            if (message != null) {
                message = ChatColor.translateAlternateColorCodes('&', message);
            }

            switch (args[2].toLowerCase()) {

                case "start": {
                    double startX = Double.parseDouble(args[3]);
                    double startY = Double.parseDouble(args[4]);
                    double startZ = Double.parseDouble(args[5]);
                    Location start = new Location(world, startX, startY, startZ);
                    cuboidConfig.set(cuboidName + ".start", start);
                    assert message != null;
                    player.sendMessage(message);
                    return true;
                }

                case "end": {
                    double endX = Double.parseDouble(args[3]);
                    double endY = Double.parseDouble(args[4]);
                    double endZ = Double.parseDouble(args[5]);
                    Location end = new Location(world, endX, endY, endZ);
                    cuboidConfig.set(cuboidName + ".end", end);
                    assert message != null;
                    player.sendMessage(message);
                    return true;
                }

                case "entitydestroyblocks": {

                    if (!checkIfBoolean(args[3])) {
                        return false;
                    }

                    cuboidConfig.set(cuboidName + ".entityDestroyBlocks", args[3]);
                    assert message != null;
                    player.sendMessage(message);
                    return true;
                }


                case "entitydealdamage": {

                    if (!checkIfBoolean(args[3])) {
                        return false;
                    }

                    cuboidConfig.set(cuboidName + ".entityDealDamage", args[3]);
                    assert message != null;
                    player.sendMessage(message);
                    return true;
                }


                case "entitygetdamage": {

                    if (!checkIfBoolean(args[3])) {
                        return false;
                    }

                    cuboidConfig.set(cuboidName + ".entityGetDamage", args[3]);
                    assert message != null;
                    player.sendMessage(message);
                    return true;
                }


                case "entityinteract": {

                    if (!checkIfBoolean(args[3])) {
                        return false;
                    }

                    cuboidConfig.set(cuboidName + ".entityInteract", args[3]);
                    assert message != null;
                    player.sendMessage(message);
                    return true;
                }

                case "addowner": {

                    for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {

                        if (args[3].equalsIgnoreCase(offlinePlayer.getName())) {

                            Cuboid cub = new Cuboid(cuboidConfig, cuboidName);
                            cub.addOwner(offlinePlayer.getUniqueId());
                            cub.saveDataToConfig(cuboidConfig);
                            assert message != null;
                            player.sendMessage(message);
                            return true;
                        }
                    }
                    return false;
                }


            }

        } else if (args[0].equalsIgnoreCase("delete")) {

            String cuboidName = args[1];
            if (!keys.contains(cuboidName)) {
                String message = pluginConfig.getString("messages.cuboidNotExists");
                if (message != null) {
                    message = ChatColor.translateAlternateColorCodes('&', message);
                    player.sendMessage(message);
                    return true;
                }
                return false;
            }


            cuboidConfig.set(cuboidName, null);

            String message = pluginConfig.getString("messages.deleteComplete");
            if (message != null) {
                message = ChatColor.translateAlternateColorCodes('&', message);
                player.sendMessage(message);
                return true;
            }
            return false;


        } else if (args[0].equalsIgnoreCase("list")) {

            String message = pluginConfig.getString("messages.list");
            if (message != null) {
                message = ChatColor.translateAlternateColorCodes('&', message);

                for (String key : keys) {
                    message = message.replace("%cuboidname%", key);
                    player.sendMessage(message);
                    return true;
                }
            }
            return false;


        } else if (args[0].equalsIgnoreCase("info")) {

            String cuboidName = args[1];
            if (!keys.contains(cuboidName)) {
                String message = pluginConfig.getString("messages.cuboidNotExists");
                if (message != null) {
                    message = ChatColor.translateAlternateColorCodes('&', message);
                    player.sendMessage(message);
                    return true;
                }
                return false;
            }


            ConfigurationSection section = cuboidConfig.getConfigurationSection(cuboidName);

            if (section == null) {
                return false;
            }

            Set<String> innerKeys = section.getKeys(true);


            String message = pluginConfig.getString("messages.list");
            if (message != null) {
                message = ChatColor.translateAlternateColorCodes('&', message);

                for (String key : innerKeys) {
                    message = message.replace("%key%", key);
                    String value = (String) cuboidConfig.get(key);
                    if (value == null) {
                        value = "null";
                    }

                    message = message.replace("%value%", value);
                    player.sendMessage(message);
                    return true;
                }
            }


        }

        return false;
    }


    private boolean checkIfBoolean(String string) {
        return string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false");
    }
}
