package me.masonic.mc.Cmd;

import me.masonic.mc.Function.Profession;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

/**
 * Mason Project
 * 2017-7-2-0002
 */
public class GtmPro implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender c, Command command, String s, String[] args) {

        if (!(c instanceof Player)) {
            c.sendMessage("§8[ §6GTM §8] §7仅玩家可使用此指令");
            return true;
        }
        Player p = (Player) c;

        switch (args.length) {
            case 2:
                switch (args[0]) {
                    case "switch":
                        try {
                            Profession.switchProMode(p, args[1]);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return true;
                    default:
                        c.sendMessage("§8[ §6GTM §8] §7参数有误");
                }
            default:
                c.sendMessage("§8[ §6GTM §8] §7参数有误");
        }


        return true;
    }
}
