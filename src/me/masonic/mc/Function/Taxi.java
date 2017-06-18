package me.masonic.mc.Function;

import me.masonic.mc.Cmd.GtmVip;
import me.masonic.mc.Core;
import me.masonic.mc.Utility.GUI;
import me.masonic.mc.Utility.RunCmd;
import net.aufdemrand.denizen.nms.NMSHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.masonic.mc.Function.Taxi.Warp.SPAWN;

public class Taxi implements Listener {
    private final Core plugin;

    public Taxi(Core plugin) {
        this.plugin = plugin;
    }

    private final int TAXI_WARP_COST = 200;
    static HashMap<Player, Long> TAXI_COOLDOWN = new HashMap<>();

    public void runRandomTaxi(Player p) {

        if (TAXI_COOLDOWN.get(p) != null && TAXI_COOLDOWN.get(p) > System.currentTimeMillis()) {
            p.sendMessage("§8[ §6GTM §8] §7出租车服务冷却尚未结束, 剩余冷却时间§a " + String.valueOf(((TAXI_COOLDOWN.get(p) - System.currentTimeMillis()) / 1000)) + " §7秒");
            return;
        }

        BukkitTask task = new TaxiRandomTask(p, GtmVip.getVipRank$RandomTaxi(p)).runTaskTimer(this.plugin, 5, 20);
        TAXI_COOLDOWN.put(p, System.currentTimeMillis() + 30000);
    }

    public void runSpawnTaxi(Player p) {

        if (TAXI_COOLDOWN.get(p) != null && TAXI_COOLDOWN.get(p) > System.currentTimeMillis()) {
            p.sendMessage("§8[ §6GTM §8] §7出租车服务冷却尚未结束, 剩余冷却时间§a " + String.valueOf(((TAXI_COOLDOWN.get(p) - System.currentTimeMillis()) / 1000)) + " §7秒");
            return;
        }

        BukkitTask task = new TaxiWarpTask(p, 15, SPAWN).runTaskTimer(this.plugin, 5, 20);
        TAXI_COOLDOWN.put(p, System.currentTimeMillis() + 30000);
    }

    private static String WARP_GUI_TITLE = "§8[ §3前往传送点 §8]";


    public static Inventory getWarpTaxiGUI() {
        Inventory gui = GUI.getGtmStyleGUI(WARP_GUI_TITLE);
        ItemStack back = new ItemStack(Material.REDSTONE);
        ItemMeta backmeta = back.getItemMeta();
        backmeta.setDisplayName("§8[ §c返回 §8]");
        back.setItemMeta(backmeta);
        gui.setItem(47, back);


        for (Warp warp : Warp.values()) {
            ItemStack item = new ItemStack(Material.ENDER_PEARL);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§8[ §a前往-" + warp.getName() + " §8]");
            List<String> lores = Collections.singletonList(
                    "§8-> §7价格: §3200 §7黑币");
            meta.setLore(lores);
            item.setItemMeta(meta);
            gui.setItem(gui.firstEmpty(), item);
        }
        return gui;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!e.getInventory().getTitle().equals(WARP_GUI_TITLE)) {
            return;
        }
        if (e.getWhoClicked() instanceof Player) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();

            if (e.getCurrentItem() != null &&
                    e.getCurrentItem().hasItemMeta() &&
                    e.getCurrentItem().getItemMeta().hasDisplayName()) {

                if (e.getSlot() == 47) {
                    RunCmd.runOp(p, "bs gtmtaxi");
                    return;
                }


                Pattern pattern = Pattern.compile("[^-][\\u4e00-\\u9fa5]+[$\\s]");
                Matcher matcher = pattern.matcher(e.getCurrentItem().getItemMeta().getDisplayName());
                if (!matcher.find()) {
                    return;
                }

                for (Warp warp : Warp.values()) {
                    if (matcher.group().replace(" ", "").equals(warp.getName())) {
                        if (TAXI_COOLDOWN.get(p) != null && TAXI_COOLDOWN.get(p) > System.currentTimeMillis()) {
                            p.sendMessage("§8[ §6GTM §8] §7出租车服务冷却尚未结束, 剩余冷却时间§a " + String.valueOf(((TAXI_COOLDOWN.get(p) - System.currentTimeMillis()) / 1000)) + " §7秒");
                            return;
                        }

                        if (Core.getEconomy().getBalance(p) < TAXI_WARP_COST) {
                            p.sendMessage("§8[ §6GTM §8] §7黑币不足");
                            return;
                        } else {
                            Core.getEconomy().withdrawPlayer(p, 200);
                        }

                        p.closeInventory();
                        p.sendMessage("§8[ §6GTM §8] §7你叫了一辆出租车");

                        TAXI_COOLDOWN.put(p, System.currentTimeMillis() + 30000);

                        BukkitTask task = new TaxiWarpTask(p, GtmVip.getVipRank$Taxi(p), warp).runTaskTimer(this.plugin, 5, 20);
                    }
                }
            }
        }
    }


    enum Warp {
        SPAWN("安全区", new Location(Bukkit.getWorld("GTM_lobby"), -335, 26, 206, 0, 0)),
        RANDOM1("城中密林", new Location(Bukkit.getWorld("GTM_city"), -246, 82, -54, 0, 0)),
        RANDOM2("荒郊", new Location(Bukkit.getWorld("GTM_city"), 183, 86, -144, 0, 180)),
        RANDOM3("好麦坞山", new Location(Bukkit.getWorld("GTM_city"), 202, 74, 282, 0, -90)),
        RANDOM4("瑞士银行", new Location(Bukkit.getWorld("GTM_city"), -202, 74, 282, 0, -180)),
        RANDOM5("重工业区", new Location(Bukkit.getWorld("GTM_city"), -270, 74, 380, 0, -90)),
        RANDOM6("41号大道", new Location(Bukkit.getWorld("GTM_city"), -65, 74, 267, 0, 0)),
        RANDOM7("寂静孤岛", new Location(Bukkit.getWorld("GTM_city"), 242, 67, 391, 0, 180)),
        RANDOM8("中央花园", new Location(Bukkit.getWorld("GTM_city"), 61, 69, 265, 0, 90)),
        RANDOM9("地下隧道", new Location(Bukkit.getWorld("GTM_city"), 214, 83, 190, 0, -270)),
        RANDOM10("足球场", new Location(Bukkit.getWorld("GTM_city"), -723, 66, -192, 0, -180)),
        RANDOM11("十字路口", new Location(Bukkit.getWorld("GTM_city"), -121, 74, 158, 90, 0)),
        RANDOM12("沙滩海岸", new Location(Bukkit.getWorld("GTM_city"), 46, 64, 402, -90, 0)),
        RANDOM13("吊装码头", new Location(Bukkit.getWorld("GTM_city"), -358, 73, 350, -90, 0)),
        RANDOM14("露营地", new Location(Bukkit.getWorld("GTM_city"), 368, 73, -266, 180, 0));

        public String getName() {
            return name;
        }


        private String name;

        public Location getLocation() {
            return location;
        }

        private Location location;

        Warp(String name, Location loc) {
            this.name = name;
            this.location = loc;
        }

    }
}

class TaxiWarpTask extends BukkitRunnable {
    TaxiWarpTask(Player player, int times, Taxi.Warp warp) {
        this.times = times;
        this.player = player;
        this.warp = warp;
    }

    private int times;
    private Player player;
    private Taxi.Warp warp;

    @Override
    public void run() {
        if (times == 10 || times == 15) {
            player.sendMessage("§8[ §6GTM §8] §7你的出租车将在 §a" + times-- + " §7秒后到达");
        } else if (times <= 0) {
            player.teleport(warp.getLocation());
            NMSHandler.getInstance().getPacketHelper().showTitle(player, "", "§8[ §7已到达-§a" + warp.getName() + " §8]", 10, 40, 10);
            this.cancel();
        } else if (times <= 5) {
            player.sendMessage("§8[ §6GTM §8] §7你的出租车将在 §a" + times-- + " §7秒后到达");
        } else {
            times--;
        }
    }
}

class TaxiHouseTask extends BukkitRunnable {
    TaxiHouseTask(Player player, int times, String world, String plot) {
        this.times = times;
        this.player = player;
        this.world = world;
        this.plot = plot;
    }

    private int times;
    private Player player;
    private String world;
    private String plot;

    @Override
    public void run() {
        if (times == 10 || times == 15) {
            player.sendMessage("§8[ §6GTM §8] §7你的出租车将在 §a" + times-- + " §7秒后到达");
        } else if (times <= 0) {
            player.teleport(Bukkit.getWorld(House.Houses.getWorld(world)).getSpawnLocation());
            RunCmd.runOp(player, "plot visit " + plot);
            this.cancel();
        } else if (times <= 5) {
            player.sendMessage("§8[ §6GTM §8] §7你的出租车将在 §a" + times-- + " §7秒后到达");
        } else {
            times--;
        }
    }
}


class TaxiRandomTask extends BukkitRunnable {
    public TaxiRandomTask(Player player, int times) {
        this.times = times;
        this.player = player;
    }

    private int times;
    private Player player;

    @Override
    public void run() {
        if (times == 10 || times == 15) {
            player.sendMessage("§8[ §6GTM §8] §7你的出租车将在 §a" + times-- + " §7秒后到达");
        } else if (times <= 0) {
            int random = (int) (Math.random() * 14);
            int count = 0;
            for (Taxi.Warp warp : Taxi.Warp.values()) {
                if (random == count) {
                    player.teleport(warp.getLocation());
                }
                count++;
            }
            this.cancel();
        } else if (times <= 5) {
            player.sendMessage("§8[ §6GTM §8] §7你的出租车将在 §a" + times-- + " §7秒后到达");
        } else {
            times--;
        }
    }
}