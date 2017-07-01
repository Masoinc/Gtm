package me.masonic.mc.Function;

import me.masonic.mc.Core;
import me.masonic.mc.Hook.HookBounty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

/**
 * Mason Project
 * 2017-6-30-0030
 */
public class Hostility implements Listener {

    private static Core plugin;

    public Hostility(Core plugin) {
        Hostility.plugin = plugin;
    }

    private static HashMap<Player, Integer> KILL_MAP = new HashMap<>();

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            //移除通缉
            KILL_MAP.remove(p);
            p.sendMessage("§8[ §6GTM §8] §7你的通缉星数已清零");

            //增加通缉
            Player k = p.getKiller();
            if (k == null) {
                return;
            }
            KILL_MAP.put(k, KILL_MAP.containsKey(p) ? KILL_MAP.get(p) + 1 : 1);
            k.sendMessage("§8[ §6GTM §8] §7你目前的通缉星数: " + getHostility$Formatted(k));

            Sidebar.sendBar(k);
            Sidebar.sendBar(p);

            new HookBounty().addBountyByGov(k);
        }
    }


    public int getHostility(Player p) {
        return KILL_MAP.getOrDefault(p, 0);
    }


    public static String getHostility$Formatted(Player p) {

        int killcount = (KILL_MAP.getOrDefault(p, 0));

        return killcount == 0 ? "§7☆☆☆☆☆" :
                (killcount <= 1 ? "§2★§7☆☆☆☆" :
                        (killcount <= 2 ? "§3★★§7☆☆☆" :
                                (killcount <= 5 ? "§6★★★§7☆☆" :
                                        (killcount <= 10 ? "§d★★★★§7☆" : "§c★★★★★"))));


    }

    public static int getHostility$Integer(Player p) {

        int killcount = (KILL_MAP.getOrDefault(p, 0));

        return killcount == 0 ? 0 :
                (killcount <= 1 ? 1 :
                        (killcount <= 2 ? 2 :
                                (killcount <= 5 ? 3 :
                                        (killcount <= 10 ? 4 : 5))));


    }

    public static int getHostility$Bounty(Player p) {

        int killcount = (KILL_MAP.getOrDefault(p, 0));

        return killcount == 0 ? 0 :
                (killcount <= 1 ? 500 :
                        (killcount <= 2 ? 1000 :
                                (killcount <= 5 ? 2000 :
                                        (killcount <= 10 ? 3000 : 5000))));
    }

    public void clearHostility() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!KILL_MAP.containsKey(p)) {
                        continue;
                    }

                    p.sendMessage("§8[ §6GTM §8] §7你的通缉星数随时间降低了" );
                    p.sendMessage("§8[ §6GTM §8] §7你目前的通缉星数: " + getHostility$Formatted(p));

                }
            }
        }.runTaskTimer(plugin, 0, 12000);

    }

}
