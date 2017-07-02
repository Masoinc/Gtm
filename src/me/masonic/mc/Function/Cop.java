package me.masonic.mc.Function;

import me.masonic.mc.Core;
import me.masonic.mc.Hook.HookMythicMobs;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Mason Project
 * 2017-7-1-0001
 */
public class Cop {


    public static void summonCop(Player p) {

        new BukkitRunnable() {
            @Override
            public void run() {
                switch (Hostility.getHostility$Integer(p)) {
                    case 0:
                        return;
                    case 1:
                        HookMythicMobs.spawnCop(p, 1, 3, 20);
                        return;
                    case 2:
                        HookMythicMobs.spawnCop(p, 1, 6, 15);
                        return;
                    case 3:
                        HookMythicMobs.spawnCop(p, 2, 5, 15);
                        return;
                    case 4:
                        HookMythicMobs.spawnCop(p, 2, 8, 12);
                        return;
                    case 5:
                        HookMythicMobs.spawnCop(p, 3, 10, 10);

                }
            }


        }.runTaskLater(Core.getInstance(), 200);
    }
}
