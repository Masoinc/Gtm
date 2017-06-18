package me.masonic.mc.Function;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import me.masonic.mc.Core;
import me.masonic.mc.Utility.GUI;
import me.masonic.mc.Utility.RunCmd;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.masonic.mc.Function.Taxi.TAXI_COOLDOWN;
import static me.masonic.mc.Utility.SQL.*;

/**
 * Mason Project
 * 2017-6-7-0007
 */
public class House implements Listener {

    private final Core plugin;

    public House(Core plugin) {
        this.plugin = plugin;
    }


    private static final String GTM_HOUSE_TITLE = "§8[ §6§l我的住宅 §8]";
    private static final String GTM_BUYHOUSE_TITLE = "§8[ §6§l购买住宅 §8]";
    private static final String GTM_BUYHOUSE_BUY = "§8[ §6购买 §8]";
    private static PlotAPI plotapi = new PlotAPI();
    //private static Map<String, String> worldmap = new HashMap<>();

    public static Inventory getHouseGUI(Player p) {

        Inventory gui = GUI.getGtmStyleGUI(GTM_HOUSE_TITLE);
        int index = 0;
        int slot;

        for (Plot plot : plotapi.getPlayerPlots(p)) {
            for (Houses houses : Houses.values()) {
                if (houses.getWorld().equalsIgnoreCase(plot.getArea().worldname)) {
                    slot = gui.firstEmpty();
                    index++;
                    gui.setItem(slot, getPlotIcon(index, plot));
                }
            }
        }

        ItemStack housestat = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemmeta = housestat.getItemMeta();
        itemmeta.setDisplayName("§8[ §3概览 §8]");

        try {
            List<String> lores = Collections.singletonList(
                    "§8-> §7住宅数量: §8[ §6" + index + "§8/§6" + getHouseLimit(p) + " §8]"
            );
            itemmeta.setLore(lores);
            housestat.setItemMeta(itemmeta);
            gui.setItem(49, housestat);
            return gui;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Inventory getBuyHouseGUI(Player p, String world) throws SQLException {
        Inventory gui = GUI.getGtmStyleGUI(GTM_BUYHOUSE_TITLE + Houses.getId(world));

        ItemStack info = new ItemStack(Material.BOOK_AND_QUILL);
        ItemMeta infometa = info.getItemMeta();
        infometa.setDisplayName("§8[ §3住宅信息 §8]");
        List<String> lores = Arrays.asList(
                "§8-> §7类型: §3普通",
                "",
                "§8-> §7箱子: §6 " + Houses.getChest(world) + " §7个",
                "§8-> §7价格: §a " + Houses.getPrice(world) + " §7黑币");
        infometa.setLore(lores);
        info.setItemMeta(infometa);
        gui.setItem(13, info);

        ItemStack buy = new ItemStack(Material.SLIME_BALL);
        ItemMeta buymeta = buy.getItemMeta();
        buymeta.setDisplayName(GTM_BUYHOUSE_BUY);

        int limitleft;
        if (House.getHouseAmount(p) >= House.getHouseLimit(p)) {
            limitleft = 0;
        } else {
            limitleft = House.getHouseLimit(p) - House.getHouseAmount(p);
        }

        List<String> buylores = Arrays.asList(
                "§8-> §7价格: §a" + Houses.getPrice(world) + " §7黑币",
                "",
                "§8-> §7账户余额: §a" + Core.getEconomy().getBalance(p) + " §7黑币",
                "§8-> §7你还可以购买 §a" + limitleft + " §7处住宅",
                "",
                "§6◇ 点击确认购买");
        buymeta.setLore(buylores);
        buy.setItemMeta(buymeta);
        gui.setItem(31, buy);

        return gui;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getTitle().contains(GTM_HOUSE_TITLE)) {
            if (e.getCurrentItem() != null &&
                    e.getCurrentItem().hasItemMeta() &&
                    e.getCurrentItem().getItemMeta().hasLore()) {

                e.setCancelled(true);

                String regEx = "(-)?\\d+;(-)?\\d+";
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(e.getCurrentItem().getItemMeta().getLore().get(0).replaceAll("§\\d", ""));
                while (matcher.find()) {
                    regEx = "\\w\\d+";
                    pattern = Pattern.compile(regEx);
                    Matcher matcher2 = pattern.matcher(e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§\\d", ""));
                    while (matcher2.find()) {
                        // 出租车

                        if (TAXI_COOLDOWN.get(p) != null && TAXI_COOLDOWN.get(p) > System.currentTimeMillis()) {
                            p.sendMessage("§8[ §6GTM §8] §7出租车服务冷却尚未结束, 剩余冷却时间§a " + String.valueOf(((TAXI_COOLDOWN.get(p) - System.currentTimeMillis()) / 1000)) + " §7秒");
                            return;
                        }
                        p.closeInventory();
                        p.sendMessage("§8[ §6GTM §8] §7你叫了一辆出租车");

                        TAXI_COOLDOWN.put(p, System.currentTimeMillis() + 30000);

                        new TaxiHouseTask(p, 10, matcher2.group(), matcher.group()).runTaskTimer(this.plugin, 5, 20);

                    }
                }
            }


        } else if (e.getInventory().getTitle().contains(GTM_BUYHOUSE_TITLE)) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null &&
                    e.getCurrentItem().hasItemMeta() &&
                    e.getCurrentItem().getItemMeta().hasDisplayName()) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(GTM_BUYHOUSE_BUY)) {

                    String regEx = "\\w\\d+";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(e.getInventory().getTitle().replaceAll("§\\d", ""));
                    Houses houses = null;
                    while (matcher.find()) {
                        houses = Houses.getHousesById(matcher.group());
                    }


                    //余额

                    int price = houses != null ? houses.getPrice() : 0;


                    if (Core.getEconomy().getBalance(p) < price) {
                        p.closeInventory();
                        p.sendMessage("§8[ §6GTM §8] §7黑币不足");
                        return;
                    }
                    if (House.getHouseAmount(p) > 0) {
                        p.closeInventory();
                        p.sendMessage("§8[ §6GTM §8] §7你的住宅数量已达到上限");
                        return;
                    }
                    if (plotapi.getPlayerPlots(Bukkit.getWorld(houses.getWorld()), p).size() > 0) {
                        p.closeInventory();
                        p.sendMessage("§8[ §6GTM §8] §7同种类型的住宅只可购买1栋，你已有此类型住宅");
                        return;
                    }


                    p.teleport(Bukkit.getWorld(houses.getWorld()).getSpawnLocation());
                    RunCmd.runOp(p, "plot auto");
                    Core.getEconomy().withdrawPlayer(p, houses.getPrice());
                    p.sendMessage("§8[ §6GTM §8] §7已购买住宅");
                    Iterator it = plotapi.getPlayerPlots(Bukkit.getWorld(houses.getWorld()), p).iterator();
                    while (it.hasNext()) {
                        Plot plot = (Plot) it.next();
                        plot.teleportPlayer(PlotPlayer.wrap(p));
                        break;
                    }
                }
            }
        }
    }


    private static int getHouseAmount(Player p) {
        int index = 0;
        for (Plot plot : plotapi.getPlayerPlots(p)) {
            for (Houses houses : Houses.values()) {
                if (houses.getWorld().equalsIgnoreCase(plot.getArea().worldname)) {
                    index++;
                }
            }
        }
        return index;
    }

    private static ItemStack getPlotIcon(int index, Plot plot) {
        String world = plot.getArea().worldname.toLowerCase();
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta itemmeta = (SkullMeta) item.getItemMeta();
        itemmeta.setDisplayName("§8[ §3" + String.valueOf(index) + "号住宅 §8] " + Houses.getId(world));
        List<String> lores = Arrays.asList(
                "§8-> §7编号: §3" + plot.getId().toString(),
                "",
                "§8-> §7箱子: §6" + Houses.getChest(world) + " §7个",
                "",
                "§6◇ 点击传送");

        itemmeta.setLore(lores);
        itemmeta.setOwner("MoulaTime");
        item.setItemMeta(itemmeta);
        return item;

    }


    public static int getHouseLimit(Player p) throws SQLException {
        if (getIfExist(p, "house")) {
            return getIntValue(p, "house", "limita");
        }
        return 0;
    }

    public static void addHouseLimit(Player p, int amount) throws SQLException {
        if (getIfExist(p, "house")) {
            uploadIntValue(p, "house", "limita", amount + getIntValue(p, "house", "limita"));
            return;
        }
        createColumn(p, "house");
        uploadIntValue(p, "house", "limita", amount);

    }

    public enum Houses {
        H1("H1", "gtm_house1", 2, 8000), H2("H2", "gtm_house2", 6, 15000);
        private String id;
        private String world;
        private int chest;
        private int price;

        Houses(String id, String world, int chest, int price) {
            this.world = world;
            this.id = id;
            this.chest = chest;
            this.price = price;
        }

        public static Houses getHousesById(String Id) {
            for (Houses houses : Houses.values()) {
                if (houses.getId().equalsIgnoreCase(Id)) {
                    return houses;
                }
            }
            return null;
        }

        public String getWorld() {
            return world;
        }

        public static String getWorld(String id) {
            for (Houses houses : Houses.values()) {
                if (houses.getId().equalsIgnoreCase(id)) {
                    return houses.world;
                }
            }
            return null;
        }

        public int getChest() {
            return this.chest;
        }

        public static int getChest(String world) {
            for (Houses houses : Houses.values()) {
                if (houses.getWorld().equalsIgnoreCase(world)) {
                    return houses.chest;
                }
            }
            return 0;
        }

        public int getPrice() {
            return this.price;
        }

        public static int getPrice(String world) {
            for (Houses houses : Houses.values()) {
                if (houses.getWorld().equalsIgnoreCase(world)) {
                    return houses.price;
                }
            }
            return 0;
        }

        public static int getPriceById(String Id) {
            for (Houses houses : Houses.values()) {
                if (houses.getId().equalsIgnoreCase(Id)) {
                    return houses.price;
                }
            }
            return 0;
        }

        public String getId() {
            return this.id;
        }

        public static String getId(String world) {
            for (Houses houses : Houses.values()) {
                if (houses.getWorld().equalsIgnoreCase(world)) {
                    return houses.id;
                }
            }
            return null;
        }
    }
}
