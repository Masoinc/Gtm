package me.masonic.mc.Function;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

/**
 * Mason Project
 * 2017-6-30-0030
 */
public class Hostility implements Listener {
    private static HashMap<Player, Integer> KILL_MAP = new HashMap<>();
    private static HashMap<Player, Integer> HOSTILITY_MAP = new HashMap<>();


    public void onKill(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            //移除通缉
            KILL_MAP.remove(p);
            p.sendMessage("§8[ §6GTM §8] §7你的通缉星数已清零");

            //增加通缉
            Player killer = p.getKiller();
            if (killer != null) {

                KILL_MAP.compute(killer, (k, v) -> k == null ? null : v + 1);
                killer.sendMessage("§8[ §6GTM §8] §7你目前的通缉星数: " + getHostility$Formatted(killer));

            }

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

}
