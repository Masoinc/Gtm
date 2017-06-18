package me.masonic.mc.Utility;

import org.bukkit.entity.Player;

/**
 * Masonic Project
 * 2017/5/23
 */
public class RunCmd {
    public static void runOp(Player p, String cmd) {
        if (p.isOp()) {
            p.performCommand(cmd);
            return;
        }
        p.setOp(true);
        p.performCommand(cmd);
        p.setOp(false);
    }
}
