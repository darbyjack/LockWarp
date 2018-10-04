package me.glaremasters.lockwarp;

import co.aikar.commands.BukkitCommandManager;
import me.glaremasters.lockwarp.commands.Commands;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import static co.aikar.commands.ACFBukkitUtil.color;

public final class LockWarp extends JavaPlugin {

    private BukkitCommandManager manager;
    private File warps;
    private FileConfiguration warpsConfig;
    private String logPrefix = "&c[&8LockWarp&c]&r ";

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        logo();
        saveDefaultConfig();
        info("Loading Commands...");
        this.manager = new BukkitCommandManager(this);
        manager.enableUnstableAPI("help");
        manager.registerCommand(new Commands());
        info("Loading Warps...");
        loadData();
        info("Ready to go! That only took " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadData() {
        try {
            warps = new File(getDataFolder(), "warps.yml");
            warpsConfig = YamlConfiguration.loadConfiguration(warps);
            warpsConfig.save(warps);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveData() {
        try {
            warpsConfig.save(warps);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public FileConfiguration getWarpsConfig() {
        return warpsConfig;
    }

    private void logo() {
        info("");
        info("  _               _   __        __               ");
        info(" | |    ___   ___| | _\\ \\      / /_ _ _ __ _ __  ");
        info(" | |   / _ \\ / __| |/ /\\ \\ /\\ / / _` | '__| '_ \\ ");
        info(" | |__| (_) | (__|   <  \\ V  V / (_| | |  | |_) |");
        info(" |_____\\___/ \\___|_|\\_\\  \\_/\\_/ \\__,_|_|  | .__/ ");
        info("                                          |_|    ");
        info("");
    }

    public void info(String msg) {
        Bukkit.getServer().getConsoleSender().sendMessage(color(logPrefix + msg));
    }
}
