package me.masonic.mc.Function;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Mason Project
 * 2017-6-13-0013
 */
public class Stack implements Listener {
    private static final HashMap<String, Boolean> STACKMAP = new HashMap<>();

    static {
        STACKMAP.put("§6手枪弹匣§8[§37§8]", true);
        STACKMAP.put("§6手枪弹匣§8[§320§8]", true);
        STACKMAP.put("§6步枪弹匣§8[§330§8]", true);
        STACKMAP.put("§6狙击弹匣§8[§310§8]", true);
        STACKMAP.put("§6机枪弹鼓§8[§360§8]", true);
        STACKMAP.put("§6霰弹弹匣§8[§312§8]", true);
        STACKMAP.put("§6火箭弹§8[§31§8]", true);
        STACKMAP.put("§8CN §6制式高爆手榴弹§6", true);
        STACKMAP.put("§8C4 §6塑性炸药§6 «1»", true);
        STACKMAP.put("§8Morotov §6燃烧瓶§6", true);
        STACKMAP.put("§8Gas Tear §6催泪瓦斯§6", true);
        STACKMAP.put("§8Pro.M §6感应地雷§6", true);
    }

    public static void stackAll(Player p) {

        HashMap<ItemStack, Integer> COUNTER_A = new HashMap<>();
        ItemStack[] items = p.getInventory().getStorageContents();
        for (ItemStack item : items) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && STACKMAP.getOrDefault(item.getItemMeta().getDisplayName(), false)) {
                COUNTER_A.compute(item, (k, v) -> v == null ? item.getAmount() : v + item.getAmount());
                p.getInventory().remove(item);
            }
        }

        for (ItemStack item : COUNTER_A.keySet()) {
            item.setAmount(COUNTER_A.get(item));
            p.getInventory().setItem(p.getInventory().firstEmpty(), item);
        }
        COUNTER_A.clear();
    }
}