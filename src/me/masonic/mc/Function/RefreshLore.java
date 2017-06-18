package me.masonic.mc.Function;

import com.shampaggon.crackshot.CSUtility;
import me.DeeCaaD.CrackShotPlus.CSPAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Mason Project
 * 2017-6-13-0013
 */
public class RefreshLore implements Listener {

    public static void refreshWeapons(Player p) {
        List<ItemStack> items = Arrays.asList(p.getInventory().getStorageContents());
        int index = -1;
        CSUtility cs = new CSUtility();
        for (ItemStack item : items) {
            index++;
            if (cs.getWeaponTitle(item) != null) {
                items.set(index, CSPAPI.updateItemStackFeatures(cs.getWeaponTitle(item), item, p));
            }
        }
        p.getInventory().setStorageContents((ItemStack[]) items.toArray());
//        for (ItemStack item : p.getInventory().getContents()) {
//            if (cs.getWeaponTitle(item) != null) {
//                p.sendMessage(cs.getWeaponTitle(item));
//
//                CSPAPI.updateItemStackFeatures(cs.getWeaponTitle(item),item,p);
//            }
//        }
    }

}
