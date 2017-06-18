package me.masonic.mc.Cmd;

import me.masonic.mc.Function.House;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

/**
 * Mason Project 
 * 2017-6-7-0007
 */
public class GtmHouse implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player) || args == null ) {
            return true;
        }
        Player p = (Player) commandSender;
        if (args.length <1) {
            p.sendMessage("§8[ §6GTM §8] §7参数有误");
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "gui":
                p.openInventory(House.getHouseGUI(p));
                return true;
            case "buy":
                if (args.length != 2) {
                    p.sendMessage("§8[ §6GTM §8] §7参数有误");
                    return true;
                }
                if (Bukkit.getWorld(args[1]) == null) {
                    p.sendMessage("§8[ §6GTM §8] §7世界名有误");
                    return true;
                }
                try {
                    p.openInventory(House.getBuyHouseGUI(p,args[1]));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            case "addlimit":
                if (args.length != 2) {
                    p.sendMessage("§8[ §6GTM §8] §7参数有误");
                    return true;
                }
                try {
                    House.addHouseLimit(Bukkit.getPlayer(args[1]), 1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return true;
    }
}
