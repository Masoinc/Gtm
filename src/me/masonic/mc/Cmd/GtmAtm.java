package me.masonic.mc.Cmd;

import me.masonic.mc.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Masonic Project 
 * 2017/5/19
 */
public class GtmAtm implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (sender instanceof Player && args[0] != null) {
            Player p = (Player) sender;
            switch (args[0]) {
                case "restore":
                    translateToDigiMoney(p);
                    return true;
            }
        }
        return false;
    }

    private static void translateToDigiMoney(Player player) {
        ItemStack[] items = player.getInventory().getContents();
        Integer sum = 0;
        Integer index = 0;
        for (ItemStack item : items) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                switch (item.getItemMeta().getDisplayName()) {
                    case "§8纸质黑币 §61$":
                        sum += item.getAmount();
                        player.getInventory().clear(index);
                        break;
                    case "§8纸质黑币 §610$":
                        sum += item.getAmount() * 10;
                        player.getInventory().clear(index);
                        break;
                    case "§8纸质黑币 §6100$":
                        sum += item.getAmount() * 100;
                        player.getInventory().clear(index);
                        break;
                    default:
                        break;
                }
            }
            index++;
        }

        Core.getEconomy().depositPlayer(player, sum);
//        int a1 = 0,a2=0,a3=0;
//
//        while (player.getInventory().iterator().hasNext()) {
//            ItemStack item = (ItemStack)player.getInventory().iterator().next();
//            if (item.getType().equals(Material.AIR)) {
//                continue;
//            }
//            int index = player.getInventory().iterator().nextIndex();
//            if (item.getItemMeta().getDisplayName().equals("§8纸质黑币 §61$")) {
//                player.getInventory().clear(index);
//                a1++;
//            } else if (item.getItemMeta().getDisplayName().equals("§8纸质黑币 §610$")) {
//                player.getInventory().clear(index);
//                a2++;
//            } else if (item.getItemMeta().getDisplayName().equals("§8纸质黑币 §6100$")) {
//                player.getInventory().clear(index);
//                a3++;
//            }
//        }
//        Integer result = a1*1 + a2*10 + a3*100;
        player.sendMessage("§8[ §6GTM §8] §7已将§8[ §6" + sum + " §8]§7黑币存入银行账户");
    }
}
