package me.masonic.mc.Function;

import me.masonic.mc.Core;
import me.masonic.mc.Utility.RunCmd;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.sql.SQLException;

/**
 * Mason Project 
 * 2017-6-10-0010
 */
public class Frame implements Listener {

    private final Core plugin;

    public Frame(Core plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerInteract(final PlayerInteractEntityEvent e) throws SQLException {

        if (e.getRightClicked().getType().equals(EntityType.ITEM_FRAME)) {
            Player p = e.getPlayer();
            ItemFrame it = (ItemFrame) e.getRightClicked();
            if (it.getItem().hasItemMeta() &&
                    it.getItem().getItemMeta().hasDisplayName()) {
                switch (it.getItem().getItemMeta().getDisplayName()) {
                    case "§7前往§6感染者都市":
                        e.setCancelled(true);
                        Taxi taxi = new Taxi(this.plugin);
                        taxi.runRandomTaxi(p);
                        return;
                    case "§6ATM":
                        e.setCancelled(true);
                        RunCmd.runOp(p, "bs gtmatm");
                        return;
                    case "§7购买房产§6H1":
                        e.setCancelled(true);
                        p.openInventory(House.getBuyHouseGUI(p,"GTM_house1"));
                        return;
                    case "§7购买房产§6H2":
                        e.setCancelled(true);
                        p.openInventory(House.getBuyHouseGUI(p,"GTM_house2"));
                        return;
                }
            }
        }
    }
}
