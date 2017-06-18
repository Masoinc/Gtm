package me.masonic.mc.Hook;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import me.masonic.mc.Cmd.GtmCoinParticle;
import me.masonic.mc.Cmd.GtmDailyReward;
import me.masonic.mc.Core;
import me.masonic.mc.Function.House;
import org.bukkit.entity.Player;

import java.sql.SQLException;

/**
 * Created by Masonic on 2017/6/2 0002.
 */
public class HookPapi extends EZPlaceholderHook {
    private Core Plugin;

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        //%gtm_getDRCooldown%
        if (identifier.equals("getDRCooldown")) {
            try {
                return GtmDailyReward.getDRCooldown(p);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //%gtm_getMRCooldown%
        if (identifier.equals("getMRCooldown")) {
            try {
                return String.valueOf(GtmCoinParticle.getMonthlyParticle$Cooldown(p)) ;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //%gtm_getCP%
        if (identifier.equals("getCP")) {
            try {
                return String.valueOf(GtmCoinParticle.getCoinParticle(p));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //%gtm_getRank%
        if (identifier.equals("getRank")) {
            return getRank(p);
        }
        //%gtm_getHouseLimit%
        if (identifier.equals("getHouseLimit")) {
            try {
                return String.valueOf(House.getHouseLimit(p));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public HookPapi(Core Plugin) {
        super(Plugin, "gtm");
        this.Plugin = Plugin;
    }
    public static String getRank(Player p) {
        if (p.hasPermission("gtm.rank.dealer")) {
            return "§d绝命毒贩";
        } else if (p.hasPermission("gtm.rank.hunter")) {
            return "§6赏金猎手";
        } else if (p.hasPermission("gtm.rank.smuggler")) {
            return "§6走私大亨";
        } else if (p.hasPermission("gtm.rank.gangster")) {
            return "§3黑帮势力";
        } else if (p.hasPermission("gtm.rank.thug")) {
            return "§3亡命歹徒";
        } else if (p.hasPermission("gtm.rank.criminal")) {
            return "§2不法分子";
        } else if (p.hasPermission("gtm.rank.rogue")) {
            return "§2地头流氓";
        } else {
            return "§7流浪者";
        }
    }

}
