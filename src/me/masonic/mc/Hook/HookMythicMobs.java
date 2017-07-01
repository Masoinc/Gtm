package me.masonic.mc.Hook;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Mason Project
 * 2017-7-1-0001
 */
public class HookMythicMobs {

    public static void spawnCop(Player p, int lv) {
        int x = (int) (Math.random() * 15) * (Math.random() > 0.5 ? 1 : -1);
        int z = (int) (Math.random() * 15) * (Math.random() > 0.5 ? 1 : -1);
        Location loc = p.getLocation().add(x, 0, z);


        ActiveMob a = MythicMobs.inst().getMobManager().spawnMob("gtmcopa", loc, lv);

    }

    public static void spawnCop(Player p, int lv, int amount, int radius) {

        for (int i = 0; i < amount; i++) {
            int x = (int) (Math.random() * radius) * (Math.random() > 0.5 ? 1 : -1);
            int z = (int) (Math.random() * radius) * (Math.random() > 0.5 ? 1 : -1);
            Location loc = p.getLocation().add(x, 0, z);


            ActiveMob a = MythicMobs.inst().getMobManager().spawnMob("gtmcopa", loc, lv);
        }
    }
}
