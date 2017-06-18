package me.masonic.mc.Cmd;

import me.masonic.mc.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static me.masonic.mc.Cmd.GtmKit.getCurrentSTime;


/**
 * Masonic Project
 * 2017/6/1 0001
 */
public class GtmDailyReward implements CommandExecutor {
    private static PreparedStatement statement;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        try {
            if (getIfExist(p)) {
                if (getCoolDown(p) != 0) {
                    p.sendMessage("§8[ §6GTM §8] §7冷却尚未结束，剩余冷却时间 " + getCoolDownFormatted(getCoolDown(p)));
                    return true;
                }
            } else createColumn(p);
            refreshCoolDown(p, getCurrentSTime(86400));
            Core.getEconomy().depositPlayer(p, 2000);
            GtmCoinParticle.giveCoinParticle(p,2);
            p.sendMessage("§8[ §6GTM §8] §7每日奖励已发放, §8[ §7黑币 §3+ 2000 §8]");
            p.sendMessage("§8[ §6GTM §8] §7每日奖励 §8[ §7特效代币§3+2 §8]");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void createColumn(Player p) throws SQLException {
        String sql = "INSERT INTO dailyreward (id) VALUES (?);";
        statement = Core.getConnection().prepareStatement(sql);
        statement.setObject(1, p.getName());
        statement.executeUpdate();
    }


    private static String getCoolDownFormatted(long cd) {
        long hour = cd / 3600;
        long minute = (cd - hour * 3600) / 60;
        return "§3" + hour + " §7小时§3 " + minute + " §7分钟";
    }

    public static String getDRCooldown(Player p) throws SQLException {
        return getCoolDown(p) == 0 ? "&3 0 §7分钟" : getCoolDownFormatted(getCoolDown(p));
    }

    private static long getCoolDown(Player p) throws SQLException {
        String sql = "SELECT lastget FROM dailyreward WHERE id = ? LIMIT 1;";
        statement = Core.getConnection().prepareStatement(sql);
        statement.setObject(1, p.getName());
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            if (rs.wasNull()) {
                return 0;
            }
            return rs.getInt(1) > getCurrentSTime() ? rs.getInt(1) - getCurrentSTime() : 0;
        }
        return 0;
    }

    private void refreshCoolDown(Player p, long cd) throws SQLException {
        String sql = "UPDATE dailyreward SET lastget = ? WHERE id = ?;";
        statement = Core.getConnection().prepareStatement(sql);
        statement.setObject(1, cd);
        statement.setObject(2, p.getName());
        statement.executeUpdate();
    }

    private boolean getIfExist(Player p) throws SQLException {
        String sql = "SELECT COUNT(*) FROM dailyreward WHERE id = ? LIMIT 1;";
        statement = Core.getConnection().prepareStatement(sql);
        statement.setObject(1, p.getName());
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            return rs.getInt(1) == 1;
        }
        return false;
    }
}
