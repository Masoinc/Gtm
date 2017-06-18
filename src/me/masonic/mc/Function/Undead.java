package me.masonic.mc.Function;

import me.masonic.mc.Core;
import net.minecraft.server.v1_10_R1.ChatComponentText;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

/**
 * Mason Project
 * 2017-6-12-0012
 */
public class Undead implements Listener {
    private static void sendPacket(Player player, Packet packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    private void showTitle(Player player, String title, String subtitle, int fadeInTicks, int stayTicks, int fadeOutTicks) {
        sendPacket(player, new PacketPlayOutTitle(fadeInTicks, stayTicks, fadeOutTicks));
        if (title != null) {
            sendPacket(player, new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, new ChatComponentText(title)));
        }

        if (subtitle != null) {
            sendPacket(player, new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, new ChatComponentText(subtitle)));
        }

    }

    private static Core plugin;

    public Undead(Core plugin) {
        Undead.plugin = plugin;
    }

    private static HashMap<String, Boolean> UNDEAD_STATE = new HashMap<>();

    static {
        UNDEAD_STATE.put("DUSK", false);
        UNDEAD_STATE.put("DAWN", false);
    }

    public void checkTime() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getWorld("GTM_city").getTime() >= 12500 && Bukkit.getWorld("GTM_city").getTime() <= 13500) {
                    if (UNDEAD_STATE.get("DUSK")) {
                        return;
                    }
                    Bukkit.getWorld("GTM_city").setSpawnFlags(true, false);
                    UNDEAD_STATE.put("DUSK", true);
                    UNDEAD_STATE.put("DAWN", false);

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage("§8[ §6GTM §8] §7末日尸潮将在 §c10 §7秒后来袭");
                    }

                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                showTitle(p, "§8[ §c末日尸潮来袭 §8]", "", 10, 40, 10);
                            }
                        }
                    }.runTaskLater(Undead.plugin, 200);


                } else if (Bukkit.getWorld("GTM_city").getTime() >= 23000) {
                    if (UNDEAD_STATE.get("DAWN")) {
                        return;
                    }
                    Bukkit.getWorld("GTM_city").setSpawnFlags(false, false);
                    UNDEAD_STATE.put("DAWN", true);
                    UNDEAD_STATE.put("DUSK", false);

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage("§8[ §6GTM §8] §7末日尸潮将在 §c10 §7秒后结束");
                    }

                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                showTitle(p, "§8[ §c末日尸潮已结束 §8]", "", 10, 40, 10);
                            }
                        }
                    }.runTaskLater(Undead.plugin, 200);
                }
            }
        }.runTaskTimer(plugin, 0, 999);
    }


//    @EventHandler
//    public void onSpawn(final EntitySpawnEvent e) {
//
//        if (e.getEntity().getCustomName() == null) {
//            Bukkit.getPlayer("Masonic").sendMessage("cleared");
//            e.getEntity().remove();
//        }
//
//
//    }
}
