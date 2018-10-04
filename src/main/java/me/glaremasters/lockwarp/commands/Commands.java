package me.glaremasters.lockwarp.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.entity.Player;

/**
 * Created by GlareMasters
 * Date: 10/4/2018
 * Time: 9:01 AM
 */
@CommandAlias("lw")
public class Commands extends BaseCommand {

    @Subcommand("setwarp")
    @CommandPermission("lw.setwarp")
    @Syntax("<warp name>")
    public void onSetWarp(Player player, String warpName) {

    }


    @HelpCommand
    @Syntax("")
    @CommandPermission("lw.help")
    public void onHelp(CommandHelp help) {
        help.showHelp();
    }
}
