package me.glaremasters.lockwarp.commands;

import co.aikar.commands.ACFBukkitUtil;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import me.glaremasters.lockwarp.LockWarp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

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
        FileConfiguration warpsConfig = lockWarp.getWarpsConfig();
        warpsConfig.set(warpName + ".loc", ACFBukkitUtil.fullLocationToString(player.getLocation()));
        warpsConfig.set(warpName + ".cd", cooldown);
        warpsConfig.set(warpName + ".checkPerm", requirePermission);
        warpsConfig.set(warpName + ".permNode", permissionNode);
        lockWarp.saveData();
        player.sendMessage(color(lockWarp.getConfig().getString("messages.setwarp")).replace("{warp}", warpName).replace("{time}", String.valueOf(cooldown)));
    }


    @HelpCommand
    @Syntax("")
    @CommandPermission("lw.help")
    public void onHelp(CommandHelp help) {
        help.showHelp();
    }
}
