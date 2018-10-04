package me.glaremasters.lockwarp.commands;

import co.aikar.commands.ACFBukkitUtil;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import me.glaremasters.lockwarp.LockWarp;
import org.bukkit.entity.Player;

/**
 * Created by GlareMasters
 * Date: 10/4/2018
 * Time: 9:01 AM
 */
@CommandAlias("lw")
public class Commands extends BaseCommand {

    @Dependency private LockWarp lockWarp;

    @Subcommand("setwarp")
    @CommandPermission("lw.setwarp")
    @Syntax("<warp name>")
    public void onSetWarp(Player player, String warpName) {
        lockWarp.getWarpsConfig().set(warpName, ACFBukkitUtil.fullLocationToString(player.getLocation()));
        lockWarp.saveData();
    }


    @HelpCommand
    @Syntax("")
    @CommandPermission("lw.help")
    public void onHelp(CommandHelp help) {
        help.showHelp();
    }
}
