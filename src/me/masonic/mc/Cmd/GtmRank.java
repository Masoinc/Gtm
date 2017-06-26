package me.masonic.mc.Cmd;

import me.masonic.mc.Function.House;
import me.masonic.mc.Utility.Announce;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

/**
 * Created by Masonic on 2017/5/30.
 */
public class GtmRank implements CommandExecutor {
    private static String rank;
    private static String rankname;

    private void setPrefix(Player p, String tag) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add deluxetags.tag." + tag);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tags set " + p.getName() + " " + tag);
    }

    private void addPermission(Player p, String rank) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add gtm.rank." + rank);
    }

    private void addGunPermission(Player p, String gun) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add crackshot.use." + gun);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            switch (args[0]) {
                case "rogue":
                    //gtm.rank.rogue
                    rank = "rogue";
                    rankname = "§2§l地头流氓";
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "criminal":
                    //gtm.rank.criminal
                    rank = "criminal";
                    rankname = "§2§l不法分子";
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    //解锁帮派
                    addPermission(p, "gangsplus.gang.*");
                    addPermission(p, "gangsplus.fight.*");
                    addPermission(p, "gangsplus.gangchat");
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "thug":
                    //gtm.rank.thug
                    rank = "thug";
                    rankname = "§3§l亡命歹徒";
                    try {
                        House.addHouseLimit(p, 1);
                    } catch (SQLException e) {
                    }
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    addGunPermission(p, "Deagle_a");
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "gangster":
                    //gtm.rank.gangster
                    rank = "gangster";
                    rankname = "§3§l黑帮势力";
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    addGunPermission(p, "qbz95_a");
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "smuggler":
                    //gtm.rank.smuggler
                    rank = "smuggler";
                    rankname = "§6§l走私大亨";
                    try {
                        House.addHouseLimit(p, 1);
                    } catch (SQLException e) {
                    }
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    addGunPermission(p, "rpg7_a");
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "hunter":
                    //gtm.rank.hunter
                    rank = "hunter";
                    rankname = "§6§l赏金猎手";
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    addGunPermission(p, "Barrett_a");
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "dealer":
                    //gtm.rank.dealer
                    rank = "dealer";
                    rankname = "§6§l绝命毒贩";
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    addGunPermission(p, "g36_a");
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }
}
