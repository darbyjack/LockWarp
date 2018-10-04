package me.glaremasters.lockwarp;

import co.aikar.commands.BukkitCommandManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class LockWarp extends JavaPlugin {

    private BukkitCommandManager manager;
    private File warps;
    private FileConfiguration warpsConfig;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.manager = new BukkitCommandManager(this);
        manager.enableUnstableAPI("help");
        saveData();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void saveData() {
        try {
            warps = new File(getDataFolder(), "warps.yml");
            warpsConfig = YamlConfiguration.loadConfiguration(warps);
            warpsConfig.save(warps);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
