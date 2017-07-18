package me.masonic.mc.Cmd;

import me.masonic.mc.Function.House;
import me.masonic.mc.Utility.Announce;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Masonic on 2017/5/30.
 */
public class GtmRank implements CommandExecutor, Listener {
    private static String rank;
    private static String rankname;
    private static HashMap<String, Integer> RANK_MAP = new HashMap<>();
    private static HashMap<String, String> RANK_NAME = new HashMap<>();

    static {
        RANK_MAP.put("hobo", -1);
        RANK_MAP.put("rogue", 0);
        RANK_MAP.put("criminal", 1);
        RANK_MAP.put("thug", 2);
        RANK_MAP.put("gangster", 3);
        RANK_MAP.put("smuggler", 4);
        RANK_MAP.put("hunter", 5);
        RANK_MAP.put("dealer", 6);
        RANK_MAP.put("mafia", 7);
        RANK_MAP.put("godfather", 8);

        RANK_NAME.put("hobo", "§7§l流浪者");
        RANK_NAME.put("rogue", "§2§l地头流氓");
        RANK_NAME.put("criminal", "§2§l不法分子");
        RANK_NAME.put("thug", "§3§l亡命歹徒");
        RANK_NAME.put("gangster", "§3§l黑帮势力");
        RANK_NAME.put("smuggler", "§6§l走私大亨");
        RANK_NAME.put("hunter", "§6§l赏金猎手");
        RANK_NAME.put("dealer", "§d§l绝命毒贩");
        RANK_NAME.put("mafia", "§d§l地下龙头");
        RANK_NAME.put("godfather", "§c§l黑道教父");
    }

    @EventHandler
    void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tags set " + p.getName() + " " + getRank$ID(p));
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
                    rankname = RANK_NAME.get(rank);
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "criminal":
                    //gtm.rank.criminal
                    rank = "criminal";
                    rankname = RANK_NAME.get(rank);
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    //解锁帮派
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "thug":
                    //gtm.rank.thug
                    rank = "thug";
                    rankname = RANK_NAME.get(rank);
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
                    rankname = RANK_NAME.get(rank);
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "smuggler":
                    //gtm.rank.smuggler
                    rank = "smuggler";
                    rankname = RANK_NAME.get(rank);
                    try {
                        House.addHouseLimit(p, 1);
                    } catch (SQLException e) {
                    }
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "hunter":
                    //gtm.rank.hunter
                    rank = "hunter";
                    rankname = RANK_NAME.get(rank);
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "dealer":
                    //gtm.rank.dealer
                    rank = "dealer";
                    rankname = RANK_NAME.get(rank);
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "mafia":
                    //gtm.rank.mafia
                    rank = "mafia";
                    rankname = RANK_NAME.get(rank);
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                case "godfather":
                    //gtm.rank.godfather
                    rank = "godfather";
                    rankname = RANK_NAME.get(rank);
                    setPrefix(p, rank);
                    addPermission(p, rank);
                    Announce.announceMsg("§8[ §6GTM §8] §7" + p.getName() + "刚刚升级为 " + rankname);
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    public static String getRank$ID(Player p) {
        String res = "hobo";
        for (String perm : RANK_MAP.keySet()) {
            if (PermissionsEx.getUser(p).has("gtm.rank." + perm) && RANK_MAP.get(perm) > RANK_MAP.get(res)) {
                res = perm;
            }
        }
        return res;
    }

    public static String getRank$Perm(Player p) {
        String res = "hobo";
        for (String perm : RANK_MAP.keySet()) {
            if (PermissionsEx.getUser(p).has("gtm.rank." + perm) && RANK_MAP.get(perm) > RANK_MAP.get(res)) {
                res = perm;
            }
        }
        return "gtm.rank." + res;
    }

    public static String getRank$Name(Player p) {
        return RANK_NAME.get(getRank$ID(p));
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
