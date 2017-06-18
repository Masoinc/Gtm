package me.masonic.mc.Cmd;

import me.masonic.mc.Core;
import me.masonic.mc.Function.Taxi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Mason Project
 * 2017-6-9-0009
 */
public class GtmTaxi implements CommandExecutor {

    private final Core plugin;

    public GtmTaxi(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player) commandSender;
        if (args.length != 1) {
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "random":
                Taxi taxi = new Taxi(this.plugin);
                taxi.runRandomTaxi(p);
                return true;
            case "warp":
                p.openInventory(Taxi.getWarpTaxiGUI());
                return true;
            case "spawn":
                Taxi taxi2 = new Taxi(this.plugin);
                taxi2.runSpawnTaxi(p);
                return true;
            default:
                return true;
        }
    }
}
