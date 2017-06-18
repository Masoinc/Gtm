package me.masonic.mc.Cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

import static me.masonic.mc.Utility.SQL.*;

/**
 * T Project
 * 2017-6-2-0002
 */
public class GtmCoinParticle implements CommandExecutor {
    private static HashMap<String, Integer> MONTHLY_LIST = new HashMap<>();

    static {
        MONTHLY_LIST.put("MVP", 2000);
        MONTHLY_LIST.put("SVIP+", 1000);
        MONTHLY_LIST.put("SVIP", 500);
        MONTHLY_LIST.put("VIP+", 250);
        MONTHLY_LIST.put("VIP", 100);
        MONTHLY_LIST.put("default", 0);

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player) commandSender;
        if (args[0] == null) {
            p.sendMessage("§8[ §6GTM §8] §7参数有误");
            return true;
        }
        switch (args.length) {
            case 1:
                switch (args[0]) {
                    case "getmonthlyreward":
                        try {
                            int mp = getMonthlyParticle(p);
                            switch (mp) {
                                case 0:
                                    p.sendMessage("§8[ §6GTM §8] §7仅 §6VIP §7级以上级别玩家可领取");
                                    return true;
                                case -1:
                                    p.sendMessage("§8[ §6GTM §8] §7本月特效代币已领取，下个月再来吧");
                                    return true;
                                default:
                                    p.sendMessage("§8[ §6GTM §8] §7已领取月度代币奖励， §8[ §7特效代币+§3" + String.valueOf(mp) + " §8]");

                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return true;
                    case "give":
                        if (args[1] == null || args[2] == null) {
                            p.sendMessage("§8[ §6GTM §8] §7参数有误");
                            return true;
                        }
                        try {
                            giveCoinParticle(Bukkit.getServer().getPlayer(args[1]), Integer.parseInt(args[2]));
                            Bukkit.getServer().getPlayer(args[1]).sendMessage("§8[ §6GTM §8] §7你获得了 §8[ §7特效代币§3+" + Integer.parseInt(args[2]) + " §8]");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    default:
                        return true;
                }
        }
        return true;
    }

    public static int getCoinParticle(Player p) throws SQLException {
        if (getIfExist(p, "coinparticle")) {
            return getIntValue(p, "coinparticle", "amount");
        }
        return 0;
    }

    private static int getMonthlyParticle(Player p) throws SQLException {

        //非vip用户
        if (GtmVip.getVipRank(p).equals("default")) {
            return 0;
        }

        Calendar calendar = Calendar.getInstance();
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

        if (getIfExist(p, "coinparticle")) {//存在

            if (getIntValue(p, "coinparticle", "cooldown") == month) {//已领过
                return -1;
            }
            //+特效代币
            uploadIntValue(p, "coinparticle", "amount", MONTHLY_LIST.get(GtmVip.getVipRank(p)) + getIntValue(p, "coinparticle", "amount"));

            //设置冷却
            uploadIntValue(p, "coinparticle", "cooldown", calendar.get(Calendar.MONTH) + 1);
            //返回增加数量
            return MONTHLY_LIST.get(GtmVip.getVipRank(p));
        }
        //不存在
        createColumn(p, "coinparticle");
        uploadIntValue(p, "coinparticle", "amount", MONTHLY_LIST.get(GtmVip.getVipRank(p)));

        uploadIntValue(p, "coinparticle", "cooldown", calendar.get(Calendar.MONTH) + 1);

        //返回增加数量
        return MONTHLY_LIST.get(GtmVip.getVipRank(p));
    }


    public static int getMonthlyParticle$Cooldown(Player p) throws SQLException {
        if (getIfExist(p, "coinparticle")) {
            return getIntValue(p, "coinparticle", "cooldown");
        }
        return 0;

    }


    static void giveCoinParticle(Player p, int a) throws SQLException {
        if (getIfExist(p, "coinparticle")) {
            uploadIntValue(p, "coinparticle", "amount", a + getIntValue(p, "coinparticle", "amount"));
            return;
        }
        createColumn(p, "coinparticle");
        uploadIntValue(p, "coinparticle", "amount", a);
    }
}
