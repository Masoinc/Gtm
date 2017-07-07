package me.masonic.mc.Function;

import me.masonic.mc.Core;
import me.masonic.mc.Hook.HookBounty;
import org.bukkit.Bukkit;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
    public void onKilled(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            //移除通缉
            Sidebar.sendBar(p);

            //增加通缉
            Player k = p.getKiller();

            if (k == null) {
                return;
            }

            if (getHostility$Integer(p) > 1 || HookBounty.getBounty(p) > 0) {

                if(Profession.getProMode$Id(k).equals("cop")) {
                    k.sendMessage("§8[ §6GTM §8] §7你击杀了一位通缉星数为 " + getHostility$Formatted(p) + " §7的嫌疑犯");
                    k.sendMessage("§8[ §6GTM §8] §7已获得其赏金的 §330% §7奖励");

                    Core.getEconomy().depositPlayer(k, HookBounty.getBounty(p));
                    k.sendMessage("§8[ §6GTM §8] §6" + HookBounty.getBounty(p) + "§7黑币已存入你的银行账户");
                    HookBounty.removeBounty(p);

                }
                return;
            }


            KILL_MAP.put(k, KILL_MAP.containsKey(k) ? KILL_MAP.get(k) + 1 : 1);
            k.sendMessage("§8[ §6GTM §8] §7你目前的通缉星数: " + getHostility$Formatted(k));

            Sidebar.sendBar(k);

            Cop.summonCop(k);

            new HookBounty().addBountyByGov(k);
        }
    }
    @EventHandler
    public void onDamaged(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof PigZombie) {
            Player p = (Player) e.getEntity();
            if (e.getFinalDamage() > p.getHealth() && getHostility$Integer(p) > 0) {

                for (Player recv: Bukkit.getOnlinePlayers()) {
                    recv.sendMessage("§8[ §6GTM §8] §6" + p.getName() + " 已被警署佣兵击杀，悬赏额已清零");
                }

                p.sendMessage("§8[ §6GTM §8] §7已从你的银行账户中扣除 §6" + HookBounty.getBounty(p) + "§7黑币");

                Core.getEconomy().withdrawPlayer(p,HookBounty.getBounty(p));

            }
        }
    }

    public int getHostility(Player p) {
        return KILL_MAP.getOrDefault(p, 0);
    }

    public void setHostility(Player p, int killcount) {
        KILL_MAP.put(p, killcount);

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

                    p.sendMessage("§8[ §6GTM §8] §7你的通缉星数随时间降低了");
                    p.sendMessage("§8[ §6GTM §8] §7你目前的通缉星数: " + getHostility$Formatted(p));

                }


            }
        }.runTaskTimer(plugin, 0, 12000);

    }

}
