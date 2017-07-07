package me.masonic.mc.Function;

import me.masonic.mc.Cmd.GtmRank;
import me.masonic.mc.Object.VipRank;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Mason Project
 * 2017-7-7-0007
 */
public class GPS implements Listener {
    private static ItemStack gps = new ItemStack(Material.NETHER_STAR);

    static {
        ItemMeta gmeta = gps.getItemMeta();
        gmeta.setLore(Arrays.asList(
                "§7",
                "§7◇ 杀手模式限定使用",
                "§7◇ 探测周围的人类",
                "§7",
                "§8-> §6右键 §7使用"));
        gmeta.setDisplayName("§8GPS §6定位装置");
        gps.setItemMeta(gmeta);
    }

    @EventHandler
    private void onClick(PlayerInteractEvent e) {
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
                && e.hasItem()
                && e.getItem().hasItemMeta()
                && e.getItem().getItemMeta().hasDisplayName()
                && e.getItem().getItemMeta().getDisplayName().equals(gps.getItemMeta().getDisplayName())
                ) {
            if (!Profession.getProMode$Id(e.getPlayer()).equals("hitman")) {
                e.getPlayer().sendMessage("§8[ §6GTM §8] §7处于 §8§l职业杀手模式 §7才可使用此物品");
                return;
            }
            getNearByPlayer(e.getPlayer());
        }
    }

    private void getNearByPlayer(Player p) {
        int radius = VipRank.getVipRank(p).getGpsradius();

        List<String> output = new ArrayList<>();
        output.add("§8-> §7你附近的人类: §8[§6 " + radius + "§7格内 §8]");

        Location pL = p.getLocation();

        for (Player u : Bukkit.getOnlinePlayers()) {
            if (u.equals(p)) {
                continue;
            }
            long distance = (long) pL.distanceSquared(u.getLocation());
            StringBuilder suboutput = new StringBuilder();
            if (u.getLocation().getWorld() == pL.getWorld() && distance < radius * radius) {
                suboutput.append(GtmRank.getRank$Name(u)).append(" ").append(u.getName()).append(" §8[ §7约§3").append(Math.round(Math.sqrt(distance))).append("§7格 §8]");
                output.add(String.valueOf(suboutput));
            }
        }
        if (output.size() == 1) {
            output.add("§7暂无");
        }
        String[] str = new String[output.size()];
        p.sendMessage(output.toArray(str));

    }
}
