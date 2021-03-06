package me.masonic.mc.Object;

import org.bukkit.entity.Player;

/**
 * Mason Project
 * 2017-7-2-0002
 */
public enum VipRank {
    DEFAULT("default", "", 14400, 15, 0, 10),
    VIP("VIP", "§6VIP", 7200, 12, 1, 15),
    VIPPLUS("VIP+", "§aVIP+", 5400, 10, 2, 20),
    SVIP("SVIP", "§bSVIP", 3600, 8, 3, 25),
    SVIPPLUS("SVIP+", "§dSVIP+", 2400, 6, 4, 35),
    MVP("MVP", "§cMVP", 1800, 5, 5, 50),;

    private int switchProModeCooldown;
    private int taxiCooldown;
    private String formattedName;
    private String name;
    private int priortiy;
    private int gpsradius;


    VipRank(String name, String formattedName, int switchProModeCooldown, int taxiCooldown, int priority, int gpsradius) {
        this.switchProModeCooldown = switchProModeCooldown;
        this.taxiCooldown = taxiCooldown;
        this.name = name;
        this.formattedName = formattedName;
        this.priortiy = priority;
        this.gpsradius = gpsradius;


    }

    public int getGpsradius() {
        return gpsradius;
    }

    public int getPriortiy() {
        return priortiy;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public int getSwitchProModeCooldown() {
        return switchProModeCooldown;
    }

    public int getTaxiCooldown() {
        return taxiCooldown;
    }

    public String getName() {
        return name;
    }

    public static VipRank getVipRank(Player p) {
        VipRank res = DEFAULT;
        for (VipRank rank : VipRank.values()) {
            if (rank.priortiy > res.priortiy) {
                res = rank;
            }
        }
        return res;
    }

}

