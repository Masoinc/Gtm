package me.masonic.mc.Hook;

import me.masonic.mc.Cmd.GtmVip;
import net.brcdev.gangs.event.GangLevelUpEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * Mason Project
 * 2017-6-26-0026
 */
public class HookGangPlus implements Listener {
    void onGangLvUP(GangLevelUpEvent e) {
        Player p = (Player) e.getGang().getOwner();
        switch (e.getCurrentLevel()) {
            case 3:
                if (!PermissionsEx.getUser(p).has("gtm.rank.smuggler") && GtmVip.getVipRank$Priority(p) < 1) {
                    p.sendMessage("§8[ §6GTM §8] §7你的帮派最高等级为§6 Lv.2 §7升级犯罪等级至 §6§l赏金猎手 §7或 §6充值§6VIP §7可提升等级上限");
                    e.setCancelled(true);
                }
                return;
            case 5:
                if (!PermissionsEx.getUser(p).has("gtm.rank.hunter") && GtmVip.getVipRank$Priority(p) < 1) {
                    p.sendMessage("§8[ §6GTM §8] §7你的帮派最高等级为§6 Lv.4 §7升级犯罪等级至 §6§l绝命毒贩 §7或 §6充值§6VIP §7可提升等级上限");
                    e.setCancelled(true);
                }
                return;
            case 6:
                if (GtmVip.getVipRank$Priority(p) < 2) {
                    p.sendMessage("§8[ §6GTM §8] §7你的帮派最高等级为§6 Lv.5§7， §6充值§aVIP+ §7可提升等级上限");
                    e.setCancelled(true);
                }
                return;
            case 7:
                if (GtmVip.getVipRank$Priority(p) < 3) {
                    p.sendMessage("§8[ §6GTM §8] §7你的帮派最高等级为§6 Lv.6§7， §6充值§2SVIP §7可提升等级上限");
                    e.setCancelled(true);
                }
                return;
            case 8:
                if (GtmVip.getVipRank$Priority(p) < 4) {
                    p.sendMessage("§8[ §6GTM §8] §7你的帮派最高等级为§6 Lv.7§7， §6充值§dSVIP+ §7可提升等级上限");
                    e.setCancelled(true);
                }
                return;
            case 9:
                if (GtmVip.getVipRank$Priority(p) < 5) {
                    p.sendMessage("§8[ §6GTM §8] §7你的帮派最高等级为§6 Lv.8§7， §6充值§cMVP §7可提升等级上限");
                    e.setCancelled(true);
                }
                return;
            default:
                return;
        }
    }
}
