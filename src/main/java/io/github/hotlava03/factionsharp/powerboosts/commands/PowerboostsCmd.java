package io.github.hotlava03.factionsharp.powerboosts.commands;

import com.massivecraft.factions.entity.MPlayer;
import io.github.hotlava03.factionsharp.FactionSharp;
import io.github.hotlava03.factionsharp.powerboosts.commands.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.hotlava03.factionsharp.powerboosts.commands.strings.Strings.*;

public class PowerboostsCmd implements CommandExecutor {

    private FactionSharp plugin;

    public PowerboostsCmd(FactionSharp instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // If user is console just send help
        if(!(sender instanceof Player)) {
            sender.sendMessage("[Powerboosts] Only in game users can run this.");
            return true;
        }

        Player player = (Player) sender;

        if(!(player.hasPermission("powerboosts.use"))) {
            player.sendMessage(errorPrefix + noPerms);
            return true;
        }

        if(args.length == 0) {
            player.sendMessage(successPrefix + powerboostAmt
                    + Util.getPowerboosts(MPlayer.get(player).getFaction()));
            return true;
        }

        if ("reload".equals(args[0])) {
            if(!player.hasPermission("powerboosts.admin")) {
                player.sendMessage(errorPrefix + noPerms);
            }
            plugin.reload();
            player.sendMessage(staffPrefix + reloadSuccess);
        } else {
            player.sendMessage(ChatColor.DARK_PURPLE+ "Faction#"
                    + ChatColor.DARK_GRAY + ChatColor.BOLD +" \u00BB "
                    + ChatColor.GRAY + "Running powerboosts "
                    + ChatColor.WHITE + "1.0-SNAPSHOT"
                    + ChatColor.GRAY + " by "
                    + ChatColor.WHITE +"HotLava03"
                    + ChatColor.GRAY +"."
            );
        }
        return true;
    }
}
