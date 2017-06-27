package me.masonic.mc.Cmd;

import me.masonic.mc.Function.House;
import me.masonic.mc.Utility.Announce;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Masonic on 2017/5/30.
 */
public class GtmRank implements CommandExecutor {
    private static String rank;
    private static String rankname;
    private static HashMap<String, Integer> RANK_MAP = new HashMap<>();
    private static HashMap<String, String> RANK_NAME = new HashMap<>();

    static {
        RANK_MAP.put("gtm.rank.hobo", -1);
        RANK_MAP.put("gtm.rank.rogue", 0);
        RANK_MAP.put("gtm.rank.criminal", 1);
        RANK_MAP.put("gtm.rank.thug", 2);
        RANK_MAP.put("gtm.rank.gangster", 3);
        RANK_MAP.put("gtm.rank.smuggler", 4);
        RANK_MAP.put("gtm.rank.hunter", 5);
        RANK_MAP.put("gtm.rank.dealer", 6);

        RANK_NAME.put("gtm.rank.hobo", "§7§l流浪者");
        RANK_NAME.put("gtm.rank.rogue", "§2§l地头流氓");
        RANK_NAME.put("gtm.rank.criminal", "§2§l不法分子");
        RANK_NAME.put("gtm.rank.thug", "§3§l亡命歹徒");
        RANK_NAME.put("gtm.rank.gangster", "§3§l黑帮势力");
        RANK_NAME.put("gtm.rank.smuggler", "§6§l走私大亨");
        RANK_NAME.put("gtm.rank.hunter", "§6§l赏金猎手");
        RANK_NAME.put("gtm.rank.dealer", "§d§l绝命毒贩");
    }

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
                    rankname = RANK_NAME.get("gtm.rank." + rank);
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "criminal":
                    //gtm.rank.criminal
                    rank = "criminal";
                    rankname = RANK_NAME.get("gtm.rank." + rank);
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    //解锁帮派
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "thug":
                    //gtm.rank.thug
                    rank = "thug";
                    rankname = RANK_NAME.get("gtm.rank." + rank);
                    try {
                        House.addHouseLimit(p, 1);
                    } catch (SQLException e) {
                    }
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "gangster":
                    //gtm.rank.gangster
                    rank = "gangster";
                    rankname = RANK_NAME.get("gtm.rank." + rank);
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "smuggler":
                    //gtm.rank.smuggler
                    rank = "smuggler";
                    rankname = RANK_NAME.get("gtm.rank." + rank);
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
                    rankname = RANK_NAME.get("gtm.rank." + rank);
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    addGunPermission(p, "Barrett_a");
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "dealer":
                    //gtm.rank.dealer
                    rank = "dealer";
                    rankname = RANK_NAME.get("gtm.rank." + rank);
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

    public static String getRank$Perm(Player p) {
        String res = "gtm.rank.hobo";
        for (String perm : RANK_MAP.keySet()) {
            if (PermissionsEx.getUser(p).has(perm) && RANK_MAP.get(perm) > RANK_MAP.get(res)) {
                res = perm;
            }
        }
        return res;
    }
    public static String getRank$Name(Player p) {
        return RANK_NAME.get(getRank$Perm(p));
    }

    void giveRankPermission(Player p, String rank) {
        switch (rank) {
            case "rogue":
                PermissionsEx.getUser(p).addPermission("");
            case "criminal":
                PermissionsEx.getUser(p).addPermission("gangsplus.gang.*");
                PermissionsEx.getUser(p).addPermission("gangsplus.fight.*");
                PermissionsEx.getUser(p).addPermission("gangsplus.gangchat");
        }
    }
}
