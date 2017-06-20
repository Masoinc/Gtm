package me.masonic.mc.Function;

import me.masonic.mc.Utility.RunCmd;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InvIcon implements Listener {

    private static ItemStack stacker = new ItemStack(Material.IRON_SWORD, 1, (short) 65);
    private static ItemStack backpack = new ItemStack(Material.CHEST);
    private static ItemStack phone = new ItemStack(Material.WATCH);
    private static List<String> ICONS = new ArrayList<>();

    static {
        ItemMeta bmeta = backpack.getItemMeta();
        bmeta.setDisplayName("§2§l随身背包");
        backpack.setItemMeta(bmeta);

        ItemMeta smeta = stacker.getItemMeta();
        smeta.setDisplayName("§3§l压缩弹夹");
        smeta.spigot().setUnbreakable(true);
        smeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        smeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        //smeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        stacker.setItemMeta(smeta);

        ItemMeta pmeta = phone.getItemMeta();
        pmeta.setDisplayName("§7§l手机");
        phone.setItemMeta(pmeta);
        ICONS = Arrays.asList("§3§l压缩弹夹","§2§l随身背包","§7§l手机");
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        giveInvIcon(event.getPlayer());
    }

    @EventHandler
    public void onPickUp(final PlayerPickupItemEvent e) {
        if (e.getItem().getItemStack().hasItemMeta() && e.getItem().getItemStack().getItemMeta().hasDisplayName()) {
            if (ICONS.contains(e.getItem().getItemStack().getItemMeta().getDisplayName())) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onDrops(final PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().hasItemMeta() && e.getItemDrop().getItemStack().getItemMeta().hasDisplayName()) {
            if (ICONS.contains(e.getItemDrop().getItemStack().getItemMeta().getDisplayName())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!(e.getCurrentItem() == null)
                && e.getCurrentItem().hasItemMeta()
                && e.getCurrentItem().getItemMeta().hasDisplayName()) {
            switch (e.getCurrentItem().getItemMeta().getDisplayName()) {
                case "§2§l随身背包":
                    e.setCancelled(true);
                    ((Player) e.getWhoClicked()).performCommand("backpack");
                    break;
                case "§3§l压缩弹夹":
                    e.setCancelled(true);
                    Stack.stackAll(((Player) e.getWhoClicked()));
                    break;
                case "§7§l手机":
                    e.setCancelled(true);
                    ((Player) e.getWhoClicked()).performCommand("bs gtmphone");
                    break;
                default:
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(final PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().hasItemMeta()
                && e.getItemDrop().getItemStack().getItemMeta().hasDisplayName()) {
            switch (e.getItemDrop().getItemStack().getItemMeta().getDisplayName()) {
                case "§7§l手机":
                    e.setCancelled(true);
                default:
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent e) {
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
                && e.hasItem()
                && e.getItem().hasItemMeta()
                && e.getItem().getItemMeta().hasDisplayName()
                && e.getItem().getItemMeta().getDisplayName().equals(phone.getItemMeta().getDisplayName())
                ) {
            RunCmd.runOp(e.getPlayer(), "bs gtmphone");
        }
    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        giveInvIcon(event.getPlayer());
    }


    private void giveInvIcon(Player p) {
        p.getInventory().setItem(8, phone);
        p.getInventory().setItem(17, backpack);
        p.getInventory().setItem(26, stacker);
    }
}




