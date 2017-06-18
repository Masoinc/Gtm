package me.masonic.mc.Hook;

import com.mtihc.minecraft.treasurechest.v8.events.TreasureChestFoundAlreadyEvent;
import com.mtihc.minecraft.treasurechest.v8.events.TreasureChestFoundEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Masonic on 2017/6/2 0002.
 */
public class HookTreasureChest implements Listener {
    @EventHandler
    public void onTreasureChestFound(TreasureChestFoundEvent e) {

    }

    @EventHandler
    public void onTreasureChestFoundAlready(TreasureChestFoundAlreadyEvent e) {
    }
}
