package me.masonic.mc.Cmd;

import me.masonic.mc.Function.Sell;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Mason on 2017-6-11-0011.
 */
public class GtmSell implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player)commandSender;
            p.openInventory(Sell.getSellGUI());

        } else {
            return true;
        }
        return true;

    }
}
