package me.masonic.mc.Utility;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Masonic Project
 * 2017/5/31
 */
public class EquipSpecialAccessory implements Listener {
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent e) {
        if (e.hasItem() && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName()) {
            if (e.getItem().getItemMeta().getDisplayName().equals("§8Thug Life §6黑框眼镜")) {
                if (e.getPlayer().getInventory().getItemInOffHand().hasItemMeta() &&
                        e.getPlayer().getInventory().getItemInOffHand().getItemMeta().hasDisplayName() &&
                        e.getPlayer().getInventory().getItemInOffHand().getItemMeta().getDisplayName().equals("§8Thug Life §6黑框眼镜")) {
                    return;
                }
                ItemStack pitem = e.getPlayer().getEquipment().getHelmet();
                e.getPlayer().getEquipment().setHelmet(e.getItem());
                e.getPlayer().getInventory().setItemInMainHand(pitem);
            }
        }
    }
}
