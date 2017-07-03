package me.masonic.mc.Object;

import org.bukkit.entity.Player;

/**
 * Mason Project
 * 2017-7-2-0002
 */
public abstract class GtmPlayer implements Player {
    public VipRank getVipRank() {
        return VipRank.getVipRank(this);
    }
}
