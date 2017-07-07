package me.masonic.mc.Hook;

import me.masonic.gtmbounty.AdvancedBounties;
import me.masonic.gtmbounty.bounty.BountyInfo;
import me.masonic.gtmbounty.bounty.BountyManager;
import me.masonic.mc.Function.Hostility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Mason Project
 * 2017-6-30-0030
 */
public class HookBounty {

    private AdvancedBounties advancedBounties = AdvancedBounties.getInstance();
    private BountyManager bountyManager = advancedBounties.bountyManager;
    public static void removeBounty(Player p) {
        AdvancedBounties advancedBounties = AdvancedBounties.getInstance();
        BountyManager bountyManager = advancedBounties.bountyManager;
        bountyManager.removeBountyInfo(p.getUniqueId());

    }
    public static double getBounty(Player p) {
        AdvancedBounties advancedBounties = AdvancedBounties.getInstance();
        BountyManager bountyManager = advancedBounties.bountyManager;
        BountyInfo bountyInfo;
        if ((bountyInfo = bountyManager.getBountyInfo(p.getUniqueId())) == null) {
            return 0;
        } else {
            return bountyInfo.getBountyPrice();
        }
    }

    public void addBountyByGov(Player p) {
        BountyInfo bountyInfo;
        double b = Hostility.getHostility$Bounty(p);
        if ((bountyInfo = bountyManager.getBountyInfo(p.getUniqueId())) == null) {
            bountyManager.addBountyInfo(p.getUniqueId(), p.getName(), b, true);

            for (Player recv : Bukkit.getOnlinePlayers()) {
                recv.sendMessage("§8[ §6GTM §8] §7感染区联合警署对 §6" + p.getName() + " §7追加了 §6" + b + " §7黑币悬赏额");
            }

        } else {

            bountyInfo = bountyManager.getBountyInfo(p.getUniqueId());
            if (bountyInfo.getBountyPrice() < Hostility.getHostility$Bounty(p)) {
                b = Hostility.getHostility$Bounty(p) - bountyInfo.getBountyPrice();
                bountyInfo.addBountyPrice(b);

                for (Player recv : Bukkit.getOnlinePlayers()) {
                    recv.sendMessage("§8[ §6GTM §8] §7感染区联合警署对 §6" + p.getName() + " §7追加了 §6" + b + " §7黑币悬赏额");
                }
            }
        }
    }

}
