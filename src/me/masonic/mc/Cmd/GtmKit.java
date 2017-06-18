package me.masonic.mc.Cmd;

import me.masonic.mc.Core;
import me.masonic.mc.Function.RefreshLore;
import me.masonic.mc.Utility.SQL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

//Mysql query returns a sheet, and what you need may locate at the second line, cuz the column name takes the position of first line.

public class GtmKit implements CommandExecutor {
    private static String name;

    static long getCurrentSTime(long cd) {
        return System.currentTimeMillis() / 1000 + cd;
    }

    static long getCurrentSTime() {
        return System.currentTimeMillis() / 1000;
    }


    private void silentNarrateDelivered(Player p, String kit) {
        String[] msgs = new String[10];
        msgs[0] = "";
        msgs[1] = "";
        msgs[2] = "§8§m§l                                             §8§m§l";
        msgs[3] = "";
        msgs[4] = "             §8[ " + kit + "补给 §8] ";
        msgs[5] = "               §7‡ §6已发放 §7‡";
        msgs[6] = "";
        msgs[7] = "§8§m§l                                             §8§m§l";
        msgs[8] = "";
        msgs[9] = "";
        p.sendMessage(msgs);
    }

    /**
     * @param kit Vip补给代码
     * @param p   玩家
     * @return 冷却未过为FALSE
     * @throws SQLException SQL异常
     */
    private boolean handleVipCoolDown(String kit, Player p) throws SQLException {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if (SQL.getIfExist(p, "kit") && SQL.getIntValue(p, "kit", kit) == day) {
            return false;
        }
        SQL.uploadIntValue(p, "kit", kit, day);
        return true;
    }


    private void setCoolDown(String kit, Player p, int cd) throws SQLException {
        String sql = "UPDATE kit SET " + kit + " = ? where id = ?;";
        PreparedStatement statement3 = Core.getConnection().prepareStatement(sql);
        statement3.setObject(1, getCurrentSTime(cd));
        statement3.setObject(2, p.getName());
        statement3.executeUpdate();
    }

    private boolean handleCoolDown(String kit, Player p) throws SQLException {
        String sql = "SELECT " + kit + " FROM kit WHERE id = ? limit 1;";
        PreparedStatement statement2 = Core.getConnection().prepareStatement(sql);
        statement2.setObject(1, p.getName());
        ResultSet rs = statement2.executeQuery();
        while (rs.next()) {
            if (!rs.wasNull()) {
                if (rs.getInt(1) > getCurrentSTime()) {
                    float lftime = (rs.getInt(1) - getCurrentSTime()) / 60;
                    p.sendMessage("§8[ §6GTM §8] §7冷却尚未结束，剩余冷却时间§3 " + lftime + " §7分钟");
                    return true;
                }
                return false;
            }
        }
        p.sendMessage("§c系统异常");
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (sender instanceof Player && args[0] != null) {
            Player p = (Player) sender;
            String pname = p.getName();
            String sql = "SELECT COUNT(*) FROM kit WHERE id = ? LIMIT 1;";//检测ID
            try {
                PreparedStatement statement = Core.getConnection().prepareStatement(sql);
                statement.setObject(1, pname);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    if (result.getInt(1) != 1) { //未检测到ID,创建
                        sql = "INSERT INTO kit (id) VALUES (?);";
                        statement = Core.getConnection().prepareStatement(sql);
                        statement.setObject(1, pname);
                        statement.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            switch (args[0].toLowerCase()) {
                case "hobo":
                    name = "§7流浪者 ";

                    try {
                        if (handleCoolDown("kit1", p)) {
                            return true;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " gtmf2 3");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " grenade_a 2");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " glock17_a 1");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load melee1 " + pname + " 1");

                    silentNarrateDelivered(p, name);
                    RefreshLore.refreshWeapons(p);

                    try {
                        setCoolDown("kit1", p, 300);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return true;

                case "rogue":
                    name = "§2地头流氓";

                    try {
                        if (handleCoolDown("kit2", p)) {
                            return true;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load melee1 " + pname + " 1"); //餐刀
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " glock17_a 1"); //glock17
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " grenade_a 2"); //手雷
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " gtmf2 2");//墨西哥卷饼
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " gtmf1 3");//压缩饼干
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb1 2");//手枪弹匣

                    silentNarrateDelivered(p, name);
                    RefreshLore.refreshWeapons(p);

                    try {
                        setCoolDown("kit2", p, 900);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return true;

                case "criminal":
                    name = "§2不法分子";

                    try {
                        if (handleCoolDown("kit3", p)) {
                            return true;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load melee2 " + pname + " 1"); //太刀
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma1 " + pname + " 1"); //基本款T恤
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " glock17_a 1"); //glock17
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " grenade_a 3"); //手雷
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " toaster_a 1"); //感应地雷
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " gtmf2 3");//墨西哥卷饼
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " gtmf1 5");//压缩饼干
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb1 3");//手枪弹匣

                    silentNarrateDelivered(p, name);
                    RefreshLore.refreshWeapons(p);

                    try {
                        setCoolDown("kit3", p, 900);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return true;
                case "thug":
                    name = "§3亡命歹徒";

                    try {
                        if (handleCoolDown("kit4", p)) {
                            return true;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load melee2 " + pname + " 1"); //太刀
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma1 " + pname + " 1"); //基本款T恤
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " usp45_a 1"); //glock17
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " grenade_a 5"); //手雷
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " putty_a 2"); //C4
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " gtmf2 3");//墨西哥卷饼
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " gtmf6 2");//汉堡
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb1 4");//手枪弹匣


                    silentNarrateDelivered(p, name);
                    RefreshLore.refreshWeapons(p);

                    try {
                        setCoolDown("kit4", p, 900);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return true;
                case "gangster":
                    name = "§3黑帮势力";

                    try {
                        if (handleCoolDown("kit5", p)) {
                            return true;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load melee3 " + pname + " 1"); //警棍
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma1 " + pname + " 1"); //基本款T恤
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " mp5_a 1"); //mp5
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " grenade_a 5"); //手雷
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " putty_a 2"); //C4
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " toaster_a 1"); //感应地雷
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " gtmf6 3");//汉堡
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb1 3");//手枪弹匣


                    silentNarrateDelivered(p, name);
                    RefreshLore.refreshWeapons(p);

                    try {
                        setCoolDown("kit5", p, 900);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return true;
                case "smuggler":
                    name = "§6走私大亨";

                    try {
                        if (handleCoolDown("kit6", p)) {
                            return true;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load melee1 " + pname + " 1");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma1 " + pname + " 1"); //基本款T恤
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma4 " + pname + " 1");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " mp5_a 1");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " s54_a 1");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " grenade_a 5"); //手雷
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " putty_a 2"); //C4
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " toaster_a 1"); //感应地雷
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " gtmf6 5");//汉堡
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb6 2");//霰弹弹匣


                    silentNarrateDelivered(p, name);
                    RefreshLore.refreshWeapons(p);

                    try {
                        setCoolDown("kit6", p, 1200);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return true;
                case "hunter":
                    name = "§6赏金猎手";

                    try {
                        if (handleCoolDown("kit7", p)) {
                            return true;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load melee1 " + pname + " 1");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma3 " + pname + " 1");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " glock17 1"); //glock
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " awm_a 1");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " putty_a 2"); //C4
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " toaster_a 1"); //感应地雷
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " gtmf2 5");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb4 2");//狙击弹匣


                    silentNarrateDelivered(p, name);
                    RefreshLore.refreshWeapons(p);

                    try {
                        setCoolDown("kit7", p, 1200);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return true;
                case "drugdealer":
                    name = "§6绝命毒贩";

                    try {
                        if (handleCoolDown("kit8", p)) {
                            return true;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load melee2 " + pname + " 1");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma3 " + pname + " 1");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma2 " + pname + " 1");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " m4a4_a 1");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " awm_a 1");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " grenade_a 5"); //手雷
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " toaster_a 1"); //感应地雷
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " gtmf2 10");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb4 2");//狙击弹匣

                    silentNarrateDelivered(p, name);
                    RefreshLore.refreshWeapons(p);

                    try {
                        setCoolDown("kit8", p, 1800);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return true;
                case "mafia":
                    name = "§6黑道龙头";
                case "vip":
                    name = "§6§lVIP";


                    if (GtmVip.getVipRank$Priority(p) > 0) {
                        try {
                            if (handleVipCoolDown("kitvip1", p)) {

                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load melee2 " + pname + " 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " M1911A1_a 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " XM1014_a 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " morotov_a 3");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " gastear_a 3");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " putty_a 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb1 5");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb6 3");

                                silentNarrateDelivered(p, name);
                                RefreshLore.refreshWeapons(p);

                                return true;

                            } else {
                                p.sendMessage("§8[ §6GTM §8] §7今天已领取此补给，请明天再来吧");
                                return true;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        p.sendMessage("§8[ §6GTM §8] §7尚未开通" + name);
                        return true;
                    }
                case "vip+":
                    name = "§a§lVIP+";
                    if (GtmVip.getVipRank$Priority(p) > 1) {
                        try {
                            if (handleVipCoolDown("kitvip2", p)) {

                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load melee4 " + pname + " 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma3 " + pname + " 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma7 " + pname + " 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " gusenberg_a 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " rpg7_a 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " grenade_a 3");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " morotov_a 3");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " toaster_a 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " putty_a 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb1 5");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb6 3");

                                silentNarrateDelivered(p, name);
                                RefreshLore.refreshWeapons(p);

                                return true;

                            } else {
                                p.sendMessage("§8[ §6GTM §8] §7今天已领取此补给，请明天再来吧");
                                return true;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        p.sendMessage("§8[ §6GTM §8] §7尚未开通" + name);
                        return true;
                    }
                case "svip":
                    name = "§3§lSVIP";
                    if (GtmVip.getVipRank$Priority(p) > 2) {

                        try {
                            if (handleVipCoolDown("kitvip3", p)) {

                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load melee4 " + pname + " 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma5 " + pname + " 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma7 " + pname + " 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " barrett_a 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " bren_a 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " grenade_a 5");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " morotov_a 5");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " toaster_a 2");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " putty_a 2");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb3 5");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb4 3");



                                silentNarrateDelivered(p, name);
                                RefreshLore.refreshWeapons(p);

                                return true;

                            } else {
                                p.sendMessage("§8[ §6GTM §8] §7今天已领取此补给，请明天再来吧");
                                return true;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        p.sendMessage("§8[ §6GTM §8] §7尚未开通" + name);
                        return true;
                    }

                case "svip+":
                    name = "§d§lSVIP+";
                    if (GtmVip.getVipRank$Priority(p) > 3) {
                        try {
                            if (handleVipCoolDown("kitvip4", p)) {

                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load melee4 " + pname + " 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma5 " + pname + " 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma6 " + pname + " 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " m249_a 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " l6_a 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " rpg7_a 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " grenade_a 6");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " morotov_a 3");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " gastear_a 5");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " toaster 2");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " putty_a 2");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb5 3");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb7 1");

                                silentNarrateDelivered(p, name);
                                RefreshLore.refreshWeapons(p);


                                return true;

                            } else {
                                p.sendMessage("§8[ §6GTM §8] §7今天已领取此补给，请明天再来吧");
                                return true;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                    } else {
                        p.sendMessage("§8[ §6GTM §8] §7尚未开通" + name);
                        return true;
                    }

                case "mvp":
                    name = "§c§lMVP";
                    if (GtmVip.getVipRank$Priority(p) > 4) {
                        try {
                            if (handleVipCoolDown("kitvip5", p)) {

                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load melee4 " + pname + " 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma6 " + pname + " 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi load gtma8 " + pname + " 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " m72_a 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " mg_a 1");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " grenade_a 5");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " morotov_a 5");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " gastear_a 5");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " toaster_a 3");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "shot give " + pname + " putty_a 3");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb3 5");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm i give " + pname + " mxb4 5");

                                silentNarrateDelivered(p, name);
                                RefreshLore.refreshWeapons(p);


                                return true;

                            } else {
                                p.sendMessage("§8[ §6GTM §8] §7今天已领取此补给，请明天再来吧");
                                return true;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                    } else {
                        p.sendMessage("§8[ §6GTM §8] §7尚未开通" + name);
                        return true;
                    }


                default:
                    p.sendMessage("§8[ §6GTM §8] §7指定的补给不存在");
                    return true;
            }
        }
        return true;
    }
}
