package me.masonic.mc.Function;

import me.masonic.mc.Core;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

import java.util.HashMap;

/**
 * Mason Project
 * 2017-6-27-0027
 */
public class Bossbar implements Listener {
    private static Core plugin;
    public Bossbar(Core plugin) {
        Bossbar.plugin = plugin;
    }

    private static HashMap<Integer, String> contents = new HashMap<>();
    private static int contentloop = 0;

    static {
        contents.put(0, "§8[ §6GTM §8] §7感染者之都内死亡掉落, 重要的物品可要收藏好哦");
        contents.put(1, "§8[ §6GTM §8] §7手持 §8[ §7§l手机 §8] §7右键即可使用手机服务");
        contents.put(2, "§8[ §6GTM §8] §7按下 §6Shift §7+ §6完全抬头 §7即可使用手机服务");
        contents.put(3, "§8[ §6GTM §8] §7按下 §6F4 §7即可查看充值详情");
    }



    @EventHandler
    void onJoin(PlayerJoinEvent e) {
        sendBar(e.getPlayer());
    }

    private void sendBar(Player p) {
        TextComponent content = new TextComponent(contents.get(contentloop));
        contentloop++;
        if (contentloop >= 4) {
            contentloop = 0;
        }
        BossBar bossBar = BossBarAPI.addBar(p, // The receiver of the BossBar
                content, // Displayed message
                BossBarAPI.Color.WHITE, // Color of the bar
                BossBarAPI.Style.NOTCHED_6, // Bar style
                1.0f);
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
