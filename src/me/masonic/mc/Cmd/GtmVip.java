package me.masonic.mc.Cmd;

import me.masonic.mc.Core;
import me.masonic.mc.Function.House;
import me.masonic.mc.Utility.RunCmd;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Mason Project
 * 2017-6-12-0012
 */
public class GtmVip implements CommandExecutor {
    private static HashMap<String, Integer> VIP_LIST = new HashMap<>();
    private static HashMap<String, Integer> VIP_TAXI_LIST = new HashMap<>();

    private static void narrateCharged(Player p, String vip) {
        String[] msgs = new String[10];
        msgs[0] = "";
        msgs[1] = "";
        msgs[2] = "§8§m§l                                             §8§m§l";
        msgs[3] = "";
        msgs[4] = "             §8[ 您已成功开通 §8] ";
        msgs[5] = "              §7‡ " + vip + " §7‡";
        msgs[6] = "            §6感染者都市感谢您的支持";
        msgs[7] = "§8§m§l                                             §8§m§l";
        msgs[8] = "";
        msgs[9] = "";
        p.sendMessage(msgs);
    }

    static {
        VIP_LIST.put("default", 0);
        VIP_LIST.put("VIP", 1);
        VIP_LIST.put("VIP+", 2);
        VIP_LIST.put("SVIP", 3);
        VIP_LIST.put("SVIP+", 4);
        VIP_LIST.put("MVP", 5);
        VIP_TAXI_LIST.put("VIP", 12);
        VIP_TAXI_LIST.put("VIP+", 10);
        VIP_TAXI_LIST.put("SVIP", 8);
        VIP_TAXI_LIST.put("SVIP+", 6);
        VIP_TAXI_LIST.put("MVP", 5);
    }

    private static Permission per;
    static String name;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            //gtmvip upgrade vip Masonic
            switch (args.length) {
                case 3:
                    switch (args[0]) {
                        case "upgrade":
                            Player p = Bukkit.getPlayer(args[2]);
                            giveVipPer(args[1].toLowerCase(), p);
                            return true;

                        default:
                            return true;

                    }
                default:
                    return true;
            }
        } else {
            Player p = (Player) commandSender;

            switch (args.length) {
                case 0:
                    return true;

                case 2:
                    switch (args[0]) {
                        case "upgrade":
                            giveVipPer(args[1].toLowerCase(),p);
                            return true;
                        default:
                            return true;

                    }
                default:
                    return true;
            }
        }

    }
//    public static String getVipRank(Player p,boolean...output$priority) {
//        if (output$priority !=null) {
//
//            for (String group : VIP_LIST.keySet()) {
//                if (PermissionsEx.getUser(p).inGroup(group))
//                    return VIP_LIST.get(group);
//            }
//            for (String group : VIP_LIST.keySet()) {
//                if (PermissionsEx.getUser(p).inGroup(group))
//                    return group;
//            }
//            return "default";
//        }

    private static void giveVipPer(String rank, Player p){
        switch (rank) {
            case "vip":
                name = "§6VIP";
                narrateCharged(p, name);
                RunCmd.runOp(p, "pex user " + p.getName() + " group add vip");
                Core.getEconomy().depositPlayer(p, 100000);
                try {
                    House.addHouseLimit(p, 1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            case "vip+":
                name = "§aVIP+";
                narrateCharged(p, name);
                RunCmd.runOp(p, "pex user " + p.getName() + " group add vip+");
                Core.getEconomy().depositPlayer(p, 250000);
                try {
                    House.addHouseLimit(p, 2);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            case "svip":
                name = "§3SVIP";
                RunCmd.runOp(p, "pex user " + p.getName() + " group add svip");
                Core.getEconomy().depositPlayer(p, 500000);
                try {
                    House.addHouseLimit(p, 3);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            case "svip+":
                name = "§5SVIP+";
                narrateCharged(p, name);
                RunCmd.runOp(p, "pex user " + p.getName() + " group add svip+");
                Core.getEconomy().depositPlayer(p, 1000000);
                try {
                    House.addHouseLimit(p, 5);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            case "mvp":
                name = "§cMVP";
                narrateCharged(p, name);
                RunCmd.runOp(p, "pex user " + p.getName() + " group add mvp");
                Core.getEconomy().depositPlayer(p, 2000000);
                try {
                    House.addHouseLimit(p, 10);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            default:
                return;
        }
    }
    public static String getVipRank(Player p) {
        String res = "default";
        for (String group : VIP_LIST.keySet()) {
            if (PermissionsEx.getUser(p).inGroup(group) && VIP_LIST.get(group) > VIP_LIST.get(res)) {
                res = group;
            }
        }
        return res;
    }

    public static int getVipRank$Priority(Player p) {
        int priority = 0;
        for (String group : VIP_LIST.keySet()) {
            if (PermissionsEx.getUser(p).inGroup(group) && VIP_LIST.get(group) > priority) {
                priority = VIP_LIST.get(group);
            }
        }
        return priority;
    }

    public static int getVipRank$Taxi(Player p) {
        int cooldown = 15;
        for (String group : VIP_TAXI_LIST.keySet()) {
            if (PermissionsEx.getUser(p).inGroup(group) && VIP_TAXI_LIST.get(group) < cooldown) {
                cooldown = VIP_LIST.get(group);
            }
        }
        return cooldown;
    }

    public static int getVipRank$RandomTaxi(Player p) {
        int cooldown = 10;
        if (getVipRank$Priority(p) > 1) {
            cooldown = 0;
        }
        return cooldown;
    }
}
