package me.masonic.mc.Hook;


import com.plotsquared.bukkit.events.PlayerTeleportToPlotEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Masonic on 2017/6/5 0005.
 */
public class HookPlotSquared implements Listener {
    static Location loc;
    @EventHandler
    public void onPlayerTeleportToPlot(PlayerTeleportToPlotEvent e) {
        switch (e.getPlot().getDefaultHome().getWorld()) {
            case "GTM_house1":
                e.setCancelled(true);
                loc = new Location(Bukkit.getWorld(e.getPlot().getCenter().getWorld()),
                        e.getPlot().getDefaultHome().getX() - 2,
                        68,
                        e.getPlot().getDefaultHome().getZ() - 3,
                        180,
                        0);

                e.getPlayer().teleport(loc);
                break;
            case "GTM_house2":
                e.setCancelled(true);
                loc = new Location(Bukkit.getWorld(e.getPlot().getCenter().getWorld()),
                        e.getPlot().getDefaultHome().getX(),
                        69,
                        e.getPlot().getDefaultHome().getZ() - 3,
                        180,
                        0);

                e.getPlayer().teleport(loc);
                break;
        }
    }
}
