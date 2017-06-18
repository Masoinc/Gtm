package me.masonic.mc.Function;

import com.shampaggon.crackshot.CSUtility;
import me.masonic.mc.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mason Project
 * 2017-6-10-0010
 */
public class Sell implements Listener {
    private final CSUtility cs = new CSUtility();
    private final Core plugin;

    public Sell(Core plugin) {
        this.plugin = plugin;
    }

    private final static ItemStack UNSELLABLE = new ItemStack(Material.STAINED_GLASS_PANE);
    private final static ItemStack SELLABLE = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
    private final static ItemStack SUM = new ItemStack(Material.EMPTY_MAP, 1);

    private static HashMap<String, Integer> OTHERPRICEMAP = new HashMap<>();

    private static HashMap<String, Integer> GUNPRICEMAP = new HashMap<>();

    static {
        GUNPRICEMAP.put("glock17_a", 80);
        GUNPRICEMAP.put("usp45_a", 80);
        GUNPRICEMAP.put("glock42_a", 140);
        GUNPRICEMAP.put("Deagle_a", 220);
        GUNPRICEMAP.put("M1911A1_a", 2200);

        GUNPRICEMAP.put("acr_a", 380);
        GUNPRICEMAP.put("ak74m_a", 380);
        GUNPRICEMAP.put("FNSCAR_a", 250);
        GUNPRICEMAP.put("qbz95_a", 240);
        GUNPRICEMAP.put("mp5_a", 180);
        GUNPRICEMAP.put("m4a4_a", 350);
        GUNPRICEMAP.put("bren_a", 600);

        GUNPRICEMAP.put("rpk_a", 600);
        GUNPRICEMAP.put("g36_a", 900);
        GUNPRICEMAP.put("m249_a", 1500);
        GUNPRICEMAP.put("m134_a", 1500);
        GUNPRICEMAP.put("mg_a", 2000);
        GUNPRICEMAP.put("gusenberg_a", 560);


        OTHERPRICEMAP.put("§2压缩饼干", 15);
        OTHERPRICEMAP.put("§2墨西哥卷饼", 20);
        OTHERPRICEMAP.put("§6烟熏鸡肉", 25);
        OTHERPRICEMAP.put("§6速食浓汤", 35);
        OTHERPRICEMAP.put("§6苹果派", 45);
        OTHERPRICEMAP.put("§5汉堡", 60);


        OTHERPRICEMAP.put("§6手枪弹匣§8[§37§8]", 75);
        OTHERPRICEMAP.put("§6手枪弹匣§8[§320§8]", 100);
        OTHERPRICEMAP.put("§6步枪弹匣§8[§330§8]", 120);
        OTHERPRICEMAP.put("§6狙击弹匣§8[§310§8]", 180);
        OTHERPRICEMAP.put("§6机枪弹鼓§8[§360§8]", 180);
        OTHERPRICEMAP.put("§6霰弹弹匣§8[§312§8]", 120);
        OTHERPRICEMAP.put("§6火箭弹§8[§31§8]", 200);


        OTHERPRICEMAP.put("§8CN §6制式高爆手榴弹§6", 20);
        OTHERPRICEMAP.put("§8Morotov §6燃烧瓶§6", 30);
        OTHERPRICEMAP.put("§8Pro.M §6感应地雷§6", 40);
        OTHERPRICEMAP.put("§8Gas Tear §6催泪瓦斯§6", 40);

        OTHERPRICEMAP.put("§8Joint §3大麻烟卷", 120);
        OTHERPRICEMAP.put("§8MDMA §2摇头丸", 200);
        OTHERPRICEMAP.put("§8Heroin §6海洛因", 280);
        OTHERPRICEMAP.put("§8Viper §6冰毒", 350);


        OTHERPRICEMAP.put("§8Binco §2棒球帽", 80);
        OTHERPRICEMAP.put("§8Tacticial §3战术头盔", 200);
        OTHERPRICEMAP.put("§8Titanium §6钛合金头盔", 1000);
        OTHERPRICEMAP.put("§8Kevlar §2纤维防弹衣", 300);
        OTHERPRICEMAP.put("§8Ceramic §3陶瓷防弹衣", 500);
        OTHERPRICEMAP.put("§8Titanium §6钛合金防弹衣", 1000);
        OTHERPRICEMAP.put("§8Thug Life §6黑框眼镜", 100);
        OTHERPRICEMAP.put("§8Binco §2基本款T恤", 100);
        OTHERPRICEMAP.put("§8JetPack §c喷气背包", 3000);


        OTHERPRICEMAP.put("§3警棍", 180);
        OTHERPRICEMAP.put("§2太刀", 75);
        OTHERPRICEMAP.put("§3棒球棍", 160);
        OTHERPRICEMAP.put("§2餐刀", 35);
        OTHERPRICEMAP.put("§6电锯", 350);


        ItemMeta meta = SUM.getItemMeta();
        List<String> lores = Collections.singletonList("§8-> §7点击出售");
        meta.setLore(lores);
        SUM.setItemMeta(meta);
    }


    private static HashMap<Player, Integer> SUMMAP = new HashMap<>();
    private static HashMap<Player, Boolean> CLOSEMAP = new HashMap<>();


    private static final String SELL_TITLE = "§8[ §6出售 §8]";
    private static int[] barray = {9, 10, 11, 12, 13, 14, 15, 16, 17,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            45, 46, 47, 48, 49, 50, 51, 52, 53,};


    private static ItemStack getSumPriceIcon(Player p, int price) {
        SUMMAP.compute(p, (k, v) -> v == null ? price : v + price);
        ItemMeta meta = SUM.getItemMeta();
        meta.setDisplayName("§8[ §7物品总价: §a" + SUMMAP.get(p) + " §7黑币 §8]");
        SUM.setItemMeta(meta);
        return SUM;
    }

    public static Inventory getSellGUI() {
        Inventory gui = Bukkit.createInventory(null, 54, SELL_TITLE);

        HashMap<Integer, ItemStack> items = new HashMap<>();
        ItemMeta meta = UNSELLABLE.getItemMeta();
        meta.setDisplayName("§8[ §7收购价格: §70 §7黑币 §8]");
        UNSELLABLE.setItemMeta(meta);
        for (int index : barray) {
            items.put(index, UNSELLABLE);
        }
        for (Map.Entry<Integer, ItemStack> ItemEntry : items.entrySet()) {
            Integer index = ItemEntry.getKey();
            ItemStack item = ItemEntry.getValue();
            gui.setItem(index, item);
        }
        ItemMeta summeta = SUM.getItemMeta();
        summeta.setDisplayName("§8[ §7物品总价: §a0 §7黑币 §8]");
        gui.setItem(53, SUM);

        return gui;

    }

    @EventHandler
    public void onClick(final InventoryClickEvent e) {
        if ((e.getWhoClicked() instanceof Player) && (e.getInventory().getTitle().equals(SELL_TITLE))) {

            if (e.getRawSlot() < 0 ||
                    e.getRawSlot() < 53 ||
                    !e.getCurrentItem().hasItemMeta() ||
                    e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) { //目标物品为玻璃，事件取消
                e.setCancelled(true);
            } else if (e.getRawSlot() == 53) {
                e.setCancelled(true);
                Player p = (Player) e.getWhoClicked();
                double sum = SUMMAP.getOrDefault(p, 0);
                Core.getEconomy().depositPlayer(p, sum);
                p.sendMessage("§8[ §6GTM §8] §7已出售提交的物品§8[ §7黑币 §3+" + String.valueOf(sum) + " §8]");
                CLOSEMAP.put(p, true);
                p.closeInventory();

            } else if (e.getRawSlot() > 53) {
                ItemStack item = e.getCurrentItem();
                if (e.getRawSlot() == 62 || e.getRawSlot() == 71 || e.getRawSlot() == 89) {
                    e.setCancelled(true);
                    return;
                }
                int price;
                if (item.getItemMeta().getDisplayName().contains("«")) {
                    price = GUNPRICEMAP.getOrDefault(cs.getWeaponTitle(item), 0);
                } else {
                    price = OTHERPRICEMAP.getOrDefault(item.getItemMeta().getDisplayName(), 0) * item.getAmount();
                }
                if (price == 0) {
                    e.getWhoClicked().sendMessage("§8[ §6GTM §8] §7 此物品暂不可出售");
                    e.setCancelled(true);
                    return;
                }

                e.getWhoClicked().getInventory().setItem(e.getSlot(), null);
                e.getWhoClicked().setItemOnCursor(null);
                ItemMeta meta = SELLABLE.getItemMeta();

                meta.setDisplayName("§8[ §7收购价格: §a" + price + " §7黑币 §8]");
                SELLABLE.setItemMeta(meta);
                int slot = e.getInventory().firstEmpty();
                e.setCancelled(false);

                e.getInventory().setItem(slot + 9, SELLABLE);
                e.getInventory().setItem(slot, item);
                e.getInventory().setItem(53, getSumPriceIcon((Player) e.getWhoClicked(), price));
                ((Player) e.getWhoClicked()).updateInventory();
            }
        }
    }


    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if ((e.getWhoClicked() instanceof Player) && (e.getInventory().getTitle().equals(SELL_TITLE))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(final InventoryCloseEvent e) {
        if (e.getInventory().getTitle().equals(SELL_TITLE)) {
            Player p = (Player) e.getPlayer();
            SUMMAP.remove(p);
            if (CLOSEMAP.getOrDefault(p, false)) {
                CLOSEMAP.remove(p);
                return;
            }

            for (ItemStack item : e.getInventory().getContents()) {
                if (item == null ||
                        !item.hasItemMeta() ||
                        !item.getItemMeta().hasDisplayName() ||
                        item.getType().equals(Material.STAINED_GLASS_PANE) ||
                        item.getType().equals(Material.EMPTY_MAP)) {
                    continue;
                }
                e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().firstEmpty(), item);
            }
        }
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {

        if ((e.hasBlock() &&
                e.getAction() == Action.RIGHT_CLICK_BLOCK &&
                e.getClickedBlock().getType().equals(Material.DROPPER) &&
                e.getClickedBlock().getWorld().equals(Bukkit.getWorld("GTM_lobby")))) {
            e.setCancelled(true);
            e.getPlayer().openInventory(Sell.getSellGUI());
        }
    }
}
