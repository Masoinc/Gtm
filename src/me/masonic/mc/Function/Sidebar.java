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

    private void sendBar(Player p) {
        Sidebar_v1_10_R1 bar = new Sidebar_v1_10_R1(p);
        bar.remove();
        bar.sendUpdate();
        bar.setTitle("      §8[ §6个人信息 §8]      ");
        if (GangsPlusApi.isInGang(p)) {
            List<String> info = Arrays.asList(
                    "§6◇ §7银行余额:    ",
                    "§8-> §6" + Core.getEconomy().getBalance(p) + " §7黑币",
                    "§1",
                    "§6◇ §7所在帮派:    ",
                    "§8-> §6" + GangsPlusApi.getPlayersGang(p).getFormattedName(),
                    "§2",
                    "§6◇ §7犯罪等级:    ",
                    "§8-> §8[ " + GtmRank.getRank$Name(p) + " §8]",
                    "§3",
                    "§8‡ GTM α v1.2"
            );
            bar.setLines(info);
            bar.sendUpdate();
        } else {
            List<String> info = Arrays.asList(
                    "§6◇ §7银行余额:    ",
                    "§8-> §6" + Core.getEconomy().getBalance(p) + " §7黑币",
                    "§2",
                    "§6◇ §7犯罪等级:    ",
                    "§8-> §8[ " + GtmRank.getRank$Name(p) + " §8]",
                    "§3",
                    "§8‡ GTM α v1.2"
            );
            bar.setLines(info);
            bar.sendUpdate();
        }
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
