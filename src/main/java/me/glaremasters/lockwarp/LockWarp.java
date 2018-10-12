package me.glaremasters.lockwarp;

import co.aikar.commands.BukkitCommandManager;
import io.papermc.lib.PaperLib;
import me.glaremasters.lockwarp.commands.Commands;
import me.glaremasters.lockwarp.updater.SpigotUpdater;
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
        PaperLib.suggestPaper(this);

        info("Checking for updates...");
        SpigotUpdater updater = new SpigotUpdater(this, 61342);
        updateCheck(updater);
    }

    @Override
    public void onDisable() {
        info("Saving data and shutting down....");
        saveData();
        info("Saving done! Have a nice day!");
    }

    /**
     * Loads the config and all the warps on startup
     */
    public void loadData() {
        try {
            warps = new File(getDataFolder(), "warps.yml");
            warpsConfig = YamlConfiguration.loadConfiguration(warps);
            warpsConfig.save(warps);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Save the config when needed
     */
    public void saveData() {
        try {
            warpsConfig.save(warps);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateCheck(SpigotUpdater updater) {
        try {
            if (updater.checkForUpdates()) {
                info("You appear to be running a version other than our latest stable release." + " You can download our newest version at: " + updater.getResourceURL());
            }
        } catch (Exception ex) {
            info("Could not check for updates! Stacktrace:");
            ex.printStackTrace();
        }
    }

    /**
     * Getter for the warps config
     *
     * @return getter
     */
    public FileConfiguration getWarpsConfig() {
        return warpsConfig;
    }

    /**
     * Print a nice looking logo in console
     */
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
