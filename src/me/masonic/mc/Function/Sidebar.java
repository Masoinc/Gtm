package me.masonic.mc.Function;

import me.masonic.mc.Cmd.GtmRank;
import me.masonic.mc.Core;
import net.aufdemrand.denizen.nms.impl.Sidebar_v1_10_R1;
import net.brcdev.gangs.GangsPlusApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Mason Project
 * 2017-6-27-0027
 */
public class Sidebar implements Listener {
    private static Core plugin;

    public Sidebar(Core plugin) {
        Sidebar.plugin = plugin;
    }

    @EventHandler
    void onJoin(PlayerJoinEvent e) {
        sendBar(e.getPlayer());
    }

    public static void sendBar(Player p) {
        Sidebar_v1_10_R1 bar = new Sidebar_v1_10_R1(p);
        bar.remove();
        bar.sendUpdate();
        bar.setTitle("      §8[ §6个人信息 §8]      ");
        bar.setLines(getSideInfo(p));
        bar.sendUpdate();
    }

    private static List<String> getSideInfo(Player p) {
        ArrayList<String> info = new ArrayList<>(Arrays.asList("§6◇ §7银行余额:    ",
                "§8-> §6" + Core.getEconomy().getBalance(p) + " §7黑币",
                "§0",
                "§6◇ §7犯罪等级:    ",
                "§8-> §8[ " + GtmRank.getRank$Name(p) + " §8]"));

        if (GangsPlusApi.isInGang(p)) {
            List<String> ganginfo = Arrays.asList("§1",
                    "§6◇ §7所在帮派:    ",
                    "§8-> §6" + GangsPlusApi.getPlayersGang(p).getFormattedName()
            );
            info.addAll(ganginfo);
        }

        if (Hostility.getHostility$Integer(p) > 0) {
            List<String> hostinfo = Arrays.asList("§2",
                    "§6◇ §7通缉星数:    ",
                    "§8-> §8[ " + Hostility.getHostility$Formatted(p) + " §8]");
            for (String s : hostinfo) {
                info.add(s);
            }
        }
        if (!Profession.getProMode$Id(p).equals("none")) {
            List<String> proinfo = Arrays.asList("§3",
                    "§6◇ §7职业模式:    ",
                    "§8-> §8[ " + Profession.getProMode$Name(p) + " §8]");
            for (String s : proinfo) {
                info.add(s);
            }

        }
        List<String> verinfo = Arrays.asList("§4",
                "§8‡ GTM" + Core.getVersion());
        for (String s : verinfo) {
            info.add(s);
        }

        return info;

    }

    public void sendSchedulely() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    sendBar(p);
                }
            }
        }.runTaskTimer(plugin, 0, 1200);
    }
}
