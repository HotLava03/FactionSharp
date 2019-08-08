package io.github.hotlava03.factionsharp.powerboosts.commands;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import io.github.hotlava03.factionsharp.FactionSharp;
import io.github.hotlava03.factionsharp.powerboosts.commands.util.Util;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static io.github.hotlava03.factionsharp.powerboosts.commands.strings.Strings.*;

public class BuyPowerCmd implements CommandExecutor {

    private Plugin plugin;

    public BuyPowerCmd(Plugin instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // If user is console just send help
        if(!(sender instanceof Player)) {
            sender.sendMessage("[Powerboosts] You can only run this command in-game." +
                    "\n[Usage] /buypower [faction] <confirm>");
            return true;
        }

        Player player = (Player) sender;

        if(!(player.hasPermission("powerboosts.use"))) {
            player.sendMessage(errorPrefix + noPerms);
            return true;
        }

        /*
         Sends the user help for the command.
         Also tells the user to add the argument "confirm"
         to confirm purchase of the powerboost.
         */
        if(args.length == 0) {
            player.sendMessage(
                    usagePrefix + buyPowerHelp
                            + getPrice(plugin.getConfig().getDouble("multiplier") / 100,
                            Util.getPowerboosts(MPlayer.get(player).getFaction()))
            );
            return true;
        }

        if(args.length == 2) {
            if(args[1].equals("confirm"))
                buyPowerOther(player, args[0]);
            else
                player.sendMessage(
                        usagePrefix + buyPowerHelp
                                + getPrice(plugin.getConfig().getDouble("multiplier") / 100,
                                Util.getPowerboosts(MPlayer.get(player).getFaction()))
                );
            return true;
        }

        switch(args[0]) {
            case "help":
                buyPowerConfirmHelp(player);
                break;
            case "confirm":
                buyPower(player, MPlayer.get(player).getFaction(), false);
                break;
            default:
                player.sendMessage(errorPrefix + invalidUsage);
                buyPowerConfirmHelp(player);
        }
        return true;
    }

    private void buyPowerConfirmHelp(Player player) {
        player.sendMessage(usagePrefix + buyPowerConfirmHelp);
    }

    /*
     When the player confirms purchasing
     the powerboost, call this method.
     */
    private void buyPower(Player player, Faction faction, boolean other) {

        final MPlayer mPlayer = MPlayer.get(player);

        if(!mPlayer.hasFaction() && !other) {
            player.sendMessage(errorPrefix + noFaction);
            return;
        }


        if(!(faction.getLeader() == mPlayer || mPlayer.getRank().getRankAbove().equals(faction.getLeaderRank())) && !other) {
            player.sendMessage(errorPrefix + notOfficer);
            return;
        }

        if (plugin.getConfig().getInt("limit") <= Util.getPowerboosts(faction)) {
            player.sendMessage(errorPrefix + tooManyPowerboosts);
            return;
        }

        Economy econ = FactionSharp.econ;

        final double multiplier = plugin.getConfig().getDouble("multiplier") / 100;
        final int powerboostAmt = Util.getPowerboosts(faction);

        if (econ.getBalance(player) < getPrice(multiplier, powerboostAmt) && !other) {
            player.sendMessage(errorPrefix + noMoney
                    + getPrice(multiplier, powerboostAmt));
            return;
        }

        // Set the new powerboost
        faction.setPowerBoost(faction.getPowerBoost()
                + plugin.getConfig().getInt("powerGiven"));

        if(!other)
            econ.withdrawPlayer(player, getPrice(multiplier, powerboostAmt));

        if(!Util.savePowerboost(faction)) {
            player.sendMessage(errorPrefix + fatal);
            return;
        }
        if(other)
            player.sendMessage(staffPrefix + buyPowerOtherSuccess + faction.getName());
        else
            player.sendMessage(successPrefix + buyPowerSuccess);
    }

    private void buyPowerOther(Player player, String name) {
        if(!player.hasPermission("powerboosts.admin")) {
            player.sendMessage("No perms");
            return;
        }

        if(name.equalsIgnoreCase("safezone") || name.equalsIgnoreCase("warzone") || name.equalsIgnoreCase("wilderness")) {
            player.sendMessage(errorPrefix + onlyPlayerFactions);
            return;
        }

        Faction faction = FactionColl.get().getByName(name);
        if(faction == null) {
            player.sendMessage(errorPrefix + notFound);
            return;
        }
        buyPower(player, faction, true);
    }

    private double getPrice(double multiplier, int powerboostAmt) {

        double basePrice = plugin.getConfig().getDouble("basePrice");

        if (powerboostAmt == 0)
            return basePrice;

        return basePrice + (getPrevPrice(multiplier, powerboostAmt) * multiplier * powerboostAmt);
    }

    private double getPrevPrice(double multiplier, int powerboostAmt) {
        powerboostAmt -= 1;
        double basePrice = plugin.getConfig().getDouble("basePrice");

        return basePrice + (basePrice * multiplier * powerboostAmt);
    }
}
