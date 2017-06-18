package me.masonic.mc.LoreStat;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import static me.masonic.mc.LoreStat.Identity.getPvAmplifier;

/**
 * Created by Masonic on 2017/5/31 0031.
 */
public class StatPvdmg implements Listener {
    @EventHandler(
            priority = EventPriority.HIGH
    )
    public void onCrackShotDamage(WeaponDamageEntityEvent e) {
        if (!(e.getVictim() instanceof Player)) {
            Player p = e.getPlayer();
            ItemStack i = p.getInventory().getItemInMainHand();
            if (getPvAmplifier(i, "PvE") != 0) {
                e.setDamage(e.getDamage() * (getPvAmplifier(i, "PvE") / 100));
            }
        } else {
            Player p = e.getPlayer();
            ItemStack i = p.getInventory().getItemInMainHand();
            if (getPvAmplifier(i, "PvP") != 0) {
                e.setDamage(e.getDamage() * (getPvAmplifier(i, "PvP") / 100));
            }
        }
    }
}
