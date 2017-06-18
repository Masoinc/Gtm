package me.masonic.mc.Utility;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Mason Project
 * 2017-6-9-0009
 */
public abstract class GUI {
    public static Inventory getGtmStyleGUI(String title) {
        Inventory HGUI = Bukkit.createInventory(null, 54, title);
        HashMap<Integer, ItemStack> items = new HashMap<>();
        int[] barray = {1, 10, 19, 28, 37, 46, 7, 16, 25, 34, 43, 52};
        for (int index : barray) {
            items.put(index, new ItemStack(Material.STAINED_GLASS_PANE));
        }
        int[] warray = {2, 3, 4, 5, 6, 47, 48, 49, 50, 51, 0, 9, 18, 27, 36, 45, 8, 17, 26, 35, 44, 53};

        for (int index : warray) {
            items.put(index, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
        }
        for (Map.Entry<Integer, ItemStack> ItemEntry : items.entrySet()) {
            Integer index = ItemEntry.getKey();
            ItemStack item = ItemEntry.getValue();
            HGUI.setItem(index, item);
        }

        return HGUI;
    }
}
