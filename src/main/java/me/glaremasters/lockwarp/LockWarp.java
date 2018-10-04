package me.glaremasters.lockwarp;

import co.aikar.commands.BukkitCommandManager;
import me.glaremasters.lockwarp.commands.Commands;
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
        manager.registerCommand(new Commands());
        loadData();
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
}
