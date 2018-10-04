package me.glaremasters.lockwarp.commands;

import co.aikar.commands.ACFBukkitUtil;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import me.glaremasters.lockwarp.LockWarp;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import static co.aikar.commands.ACFBukkitUtil.color;

/**
 * Created by GlareMasters
 * Date: 10/4/2018
 * Time: 9:01 AM
 */
@CommandAlias("lw")
public class Commands extends BaseCommand {

    @Dependency private LockWarp lockWarp;

    private Set<String> coolDown = new HashSet<>();

    @Subcommand("setwarp")
    @CommandPermission("lw.setwarp")
    @Syntax("<warp name> <cooldown> <require permission (true / false)> <permission node>")
    public void onSetWarp(Player player, String warpName, Integer cooldown, boolean requirePermission, @Optional String permissionNode) {
        lockWarp.getWarpsConfig().set(warpName + ".loc", ACFBukkitUtil.fullLocationToString(player.getLocation()));
        lockWarp.getWarpsConfig().set(warpName + ".cd", cooldown);
        lockWarp.getWarpsConfig().set(warpName + ".checkPerm", requirePermission);
        if (permissionNode != null) {
            lockWarp.getWarpsConfig().set(warpName + ".permNode", permissionNode);
        }
        lockWarp.saveData();
        player.sendMessage(color(lockWarp.getConfig().getString("messages.setwarp")).replace("{warp}", warpName).replace("{time}", String.valueOf(cooldown)));
    }

    @Subcommand("list")
    @CommandPermission("lw.list")
    public void onWarpList(Player player) {
        StringJoiner joiner = new StringJoiner(", ");
        for (String warp : lockWarp.getWarpsConfig().getKeys(false)) {
            joiner.add(warp);
        }
        player.sendMessage(color(lockWarp.getConfig().getString("messages.list").replace("{warps}", joiner.toString())));
    }

    @Subcommand("delwarp")
    @CommandPermission("lw.delwarp")
    @Syntax("<warp name>")
    public void onWarpRemove(Player player, String warpName) {
        FileConfiguration warpsConfig = lockWarp.getWarpsConfig();
        if (!warpsConfig.contains(warpName)) {
            player.sendMessage(color(lockWarp.getConfig().getString("messages.warp-invalid")));
            return;
        }
        warpsConfig.set(warpName, null);
        lockWarp.saveData();
    }

    @Subcommand("warp")
    @CommandPermission("lw.warp")
    @Syntax("<warp name>")
    public void onWarp(Player player, String warpName) {
        FileConfiguration warpsConfig = lockWarp.getWarpsConfig();
        if (!warpsConfig.contains(warpName)) {
            player.sendMessage(color(lockWarp.getConfig().getString("messages.warp-invalid")));
            return;
        }
        if (warpsConfig.getBoolean(warpName + ".checkPerm")) {
            if (!player.hasPermission(warpsConfig.getString(warpName + ".permNode"))) {
                player.sendMessage(color(lockWarp.getConfig().getString("messages.no-permission")));
                return;
            }
        }
        if (coolDown.contains(warpName)) {
            player.sendMessage(color(lockWarp.getConfig().getString("messages.cooldown")));
            return;
        }
        player.teleport(ACFBukkitUtil.stringToLocation(lockWarp.getWarpsConfig().getString(warpName + ".loc")));
        coolDown.add(warpName);
        Bukkit.getScheduler().scheduleAsyncDelayedTask(lockWarp, () -> coolDown.remove(warpName), (20 * lockWarp.getWarpsConfig().getInt(warpName + ".cd")));
    }


    @HelpCommand
    @Syntax("")
    @CommandPermission("lw.help")
    public void onHelp(CommandHelp help) {
        help.showHelp();
    }
}
