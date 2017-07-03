package me.masonic.mc.Function;

import me.masonic.mc.Utility.RunCmd;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.List;

/**
 * Mason Project
 * 2017-6-17-0017
 */
public class Secure implements Listener {
    private static List<String> BANNEDCMD = Arrays.asList("/pl", "plugins","/?","/bukkit:?","/bukkit:help","/bukkit:pl","/bukkit:plugins");

    @EventHandler
    void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/help")) {
            e.setCancelled(true);
            RunCmd.runOp(e.getPlayer(),"bs gtmphone");
            return;
        }
        if (BANNEDCMD.contains(e.getMessage())) {
            e.setCancelled(true);
        }
    }

}
