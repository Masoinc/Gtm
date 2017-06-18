package me.masonic.mc.Function;

import net.aufdemrand.denizen.nms.NMSHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Timer;
import java.util.TimerTask;

import static org.bukkit.potion.PotionEffectType.*;

/**
 * Masonic Project 
 * 2017/5/13
 */
public class Drug implements Listener {
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent e) {
        if ((e.getAction() == Action.RIGHT_CLICK_AIR
                && e.getItem().hasItemMeta()
                && e.getItem().getItemMeta().hasDisplayName()
        )) {

            Player player = e.getPlayer();
            ItemStack pitem = e.getItem();
            switch (e.getItem().getItemMeta().getDisplayName()) {
                case "§8BST §6保加利亞玫瑰爽膚純露":
                    pitem.setAmount(pitem.getAmount() - 1);
                    player.getInventory().setItemInMainHand(pitem);
                    player.setHealth(0);
                    break;
                case "§8Joint §3大麻烟卷":
                    if (!Drug.drugDealer(player, pitem, SLOW, SLOW_DIGGING)) {
                        return;
                    }

                    player.addPotionEffect(new PotionEffect(SLOW, 2400, 1));
                    player.addPotionEffect(new PotionEffect(SLOW_DIGGING, 2400, 1));
                    Drug.addHunger(player, 20000);
                    break;
                case "§8MDMA §2摇头丸":
                    if (!Drug.drugDealer(player, pitem, SLOW, CONFUSION)) {
                        return;
                    }
                    player.addPotionEffect(new PotionEffect(CONFUSION, 2400, 1));
                    player.addPotionEffect(new PotionEffect(SLOW, 1200, 1));
                    Drug.addHunger(player, 30000);
                    break;
                case "§8Heroin §6海洛因":
                    if (!Drug.drugDealer(player, pitem, SPEED, INCREASE_DAMAGE)) {
                        return;
                    }
                    player.addPotionEffect(new PotionEffect(SPEED, 2400, 0));
                    player.addPotionEffect(new PotionEffect(INCREASE_DAMAGE, 2400, 1));
                    Drug.addHunger(player, 60000);
                    break;
                case "§8Viper §6冰毒":
                    if (!Drug.drugDealer(player, pitem, SPEED, JUMP)) {
                        return;
                    }
                    player.addPotionEffect(new PotionEffect(SPEED, 2400, 0));
                    player.addPotionEffect(new PotionEffect(JUMP, 2400, 0));
                    Drug.addHunger(player, 30000);
                    break;
            }
        }
    }

//    public static boolean drugDealer(Player player, ItemStack pitem, PotionEffectType pe1, PotionEffectType pe2, PotionEffectType pe3) {
//        if (player.hasPotionEffect(pe1) && player.hasPotionEffect(pe2) && player.hasPotionEffect(pe3)) {
//            //player.sendTitle("","§8[ §6 药效尚未结束 §8]");
//            NMSHandler.getInstance().getPacketHelper().showTitle(player, "", "§8[ §6药效尚未结束 §8]", 10, 20, 10);
//            return false;
//        }
//        pitem.setAmount(pitem.getAmount() - 1);
//        player.getInventory().setItemInMainHand(pitem);
//        return true;
//    }

    private static boolean drugDealer(Player player, ItemStack pitem, PotionEffectType pe1, PotionEffectType pe2) {
        if (player.hasPotionEffect(pe1) && player.hasPotionEffect(pe2)) {
            //player.sendTitle("","§8[ §6 药效尚未结束 §8]");
            NMSHandler.getInstance().getPacketHelper().showTitle(player, "", "§8[ §6药效尚未结束 §8]", 10, 20, 10);
            return false;
        }
        pitem.setAmount(pitem.getAmount() - 1);
        player.getInventory().setItemInMainHand(pitem);
        return true;
    }

    private static void addHunger(Player player, long time) {
        long targett = System.currentTimeMillis() + time;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               player.setFoodLevel(player.getFoodLevel() + 2);
                               if (System.currentTimeMillis() > targett) {
                                   timer.cancel();
                               }
                           }
                       }

                , 5000, 5000);
        // 每5秒加一次饥饿值
    }
}
