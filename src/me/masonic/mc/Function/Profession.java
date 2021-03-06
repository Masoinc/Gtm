package me.masonic.mc.Function;

import me.masonic.mc.Core;
import me.masonic.mc.Object.VipRank;
import me.masonic.mc.Utility.SQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static me.masonic.mc.Cmd.GtmKit.getCurrentSTime;

/**
 * Mason Project
 * 2017-7-2-0002
 */


public class Profession {

    private static HashMap<String, String> PROMODE_MAP = new HashMap<>();

    static {

        PROMODE_MAP.put("cop", "§3§l警察模式");
        PROMODE_MAP.put("hitman", "§8§l杀手模式");
        PROMODE_MAP.put("dealer", "§2§l杀手模式");
        PROMODE_MAP.put("none", "§7§l无职业");

    }

    /* TODO:
      * 警察模式
      * gtm.mode.cop
      * gtm.modeaccess.cop
      * 杀手模式
      * gtm.mode.hitman
      * gtm.modeaccess.hitman
      * 毒贩模式
      * gtm.mode.dealer
      * gtm.modeaccess.dealer
      * 切换冷却
      * 普通-4h
      * 权限组
      *
      *
      */
    public static void clearProMode(Player p) throws SQLException {
        String cdinfo = String.valueOf(VipRank.getVipRank(p).getSwitchProModeCooldown() / 60) + " §7分钟后可再次切换职业模式";
        if (getProMode$Name(p).equals("§7无")) {
            p.sendMessage("§8[ §6GTM §8] §7你目前尚无职业");
            return;
        }
        if (setSwitchCD(p)) {
            for (String mode : PROMODE_MAP.keySet()) {
                PermissionsEx.getUser(p).removePermission("gtm.mode." + mode);
            }
            p.sendMessage("§8[ §6GTM §8] §7已辞职，§3 " + cdinfo);
        }
    }

    public static String getProMode$Id(Player p) {
        for (String node : PROMODE_MAP.keySet()) {
            if (PermissionsEx.getUser(p).has("gtm.mode." + node)) {
                return node;
            }
        }
        return "none";
    }

    public static String getProMode$Name(Player p) {
        for (String node : PROMODE_MAP.keySet()) {
            if (PermissionsEx.getUser(p).has("gtm.mode." + node)) {
                return PROMODE_MAP.get(node);
            }
        }
        return "§7无";
    }


    public static long getSwitchCD(Player p) throws SQLException {

        if (!SQL.getIfExist(p, "promode")) {
            return 0;
        }

        String sql = "SELECT lastswitch FROM promode WHERE id = ? LIMIT 1;";
        PreparedStatement statement2 = Core.getConnection().prepareStatement(sql);
        statement2.setObject(1, p.getName());
        ResultSet rs = statement2.executeQuery();

        while (rs.next()) {
            if (!rs.wasNull()) {
                return rs.getInt(1) > getCurrentSTime() ? rs.getInt(1) - getCurrentSTime() : 0;
            } else {
                return 0;
            }
        }
        return 0;
    }

    public static String getFormattedCD(Player p) throws SQLException {
        if (getSwitchCD(p) == 0) {
            return "§30 §7小时 §30 §7分钟";
        } else {
            long cd = getSwitchCD(p);
            long hour = cd / 3600;
            long min = (cd % 3600) / 60;
            return "§3" + hour + " §7小时" + " §3" + min + " §7分钟";
        }
    }

    private static boolean setSwitchCD(Player p) throws SQLException {
        int cd = VipRank.getVipRank(p).getSwitchProModeCooldown();

        //无记录
        if (!SQL.getIfExist(p, "promode")) {
            String sql = "INSERT INTO promode (id,lastswitch) VALUES (?," + getCurrentSTime(cd) + ");";
            PreparedStatement statement = Core.getConnection().prepareStatement(sql);
            statement = Core.getConnection().prepareStatement(sql);
            statement.setObject(1, p.getName());
            statement.executeUpdate();
            return true;
        }

        //有记录且未过期
        if (getSwitchCD(p) != 0) {
            p.sendMessage("§8[ §6GTM §8] §7切换职业模式剩余冷却: " + getFormattedCD(p));
            return false;
        } else {
            String sql = "UPDATE promode SET lastswitch = ? WHERE id = ?;";
            PreparedStatement statement3 = Core.getConnection().prepareStatement(sql);
            statement3.setObject(1, getCurrentSTime(cd));
            statement3.setObject(2, p.getName());
            statement3.executeUpdate();
            return true;
        }
    }

    public static void switchProMode(Player p, String mode) throws SQLException {

        if (!PermissionsEx.getUser(p).has("gtm.modeaccess." + mode)) {
            p.sendMessage("§8[ §6GTM §8] §7你尚未解锁此职业模式");
            return;
        }

        if (setSwitchCD(p)) {
            for (String per : PROMODE_MAP.keySet()) {
                if (per.equals(mode)) {
                    PermissionsEx.getUser(p).addPermission("gtm.mode." + mode);
                    p.sendMessage("§8[ §6GTM §8] §7你的职业模式已切换至 " + PROMODE_MAP.get(per));
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tags set " + p.getName() + " " + mode);
                    // 切换称号
                    return;
                } else {
                    PermissionsEx.getUser(p).removePermission("gtm.mode." + per);
                }

            }
            p.sendMessage("§8[ §6GTM §8] §7你指定的职业不存在");
        }
    }
}
