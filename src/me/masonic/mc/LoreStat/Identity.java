package me.masonic.mc.LoreStat;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Masonic on 2017/5/31 0031.
 */
public abstract class Identity {
    public static Double getPvAmplifier(ItemStack i, String type) {
        //type = PvP/PvE
        if (i.getItemMeta() != null && i.getItemMeta().getLore() != null) {
            List lores = i.getItemMeta().getLore();
            for (Object lore1 : lores) {
                String lore = (String) lore1;
                if (lore.contains("§7▷ " + type + "攻击系数: §2")) {
                    String targetl = lore.replace("§7▷ " + type + "攻击系数: §2", "").replace("%", "");
                    return Double.parseDouble(targetl);
                }
            }
            return 100.0;
        } else {
            return 100.0;
        }
    }

}
