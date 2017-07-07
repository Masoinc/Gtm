package me.masonic.mc;

import com.intellectualcrafters.plot.api.PlotAPI;
import me.masonic.mc.Cmd.*;
import me.masonic.mc.Function.*;
import me.masonic.mc.Hook.HookGangPlus;
import me.masonic.mc.Hook.HookPapi;
import me.masonic.mc.Hook.HookPlotSquared;
import me.masonic.mc.LoreStat.StatPvdmg;
import me.masonic.mc.Utility.EquipSpecialAccessory;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Core extends JavaPlugin {

    private static Core plugin;

    private static Economy economy = null;

    private static Connection connection = null;



    public static final String Version = "§8α v1.4";

    public static String getVersion() {
        return Version;
    }

    public static Connection getConnection() {
        return connection;
    }

    private Logger logger;

    // 插件首次启用时，触发此事件

    public static Economy getEconomy() {
        return economy;
    }

    public static Core getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.logger = this.getLogger();

        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }

        loadFiles();

        new HookPapi(this).hook(); //Hook Papi

        registerEvents();
        registerCommands();
        registerEconomy(); //Hook EcoSystem

        registerSQL();

        PluginManager manager = Bukkit.getServer().getPluginManager();
        final Plugin plotsquared = manager.getPlugin("PlotSquared");

        PlotAPI api = new PlotAPI();

        new Undead(this).checkTime();
        new Sidebar(this).sendSchedulely();
        new Bossbar(this).sendSchedulely();
        new Hostility(this).clearHostility();


    }

    @Override
    public void onDisable() {
        try { //using a try catch to catch connection errors (like wrong sql password...)
            if (connection != null && !connection.isClosed()) { //checking if connection isn't null to
                //avoid receiving a nullpointer
                connection.close(); //closing the connection field variable.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void loadFiles() {
        File config = new File(this.getDataFolder(), "config.yml");
        this.getConfig().options().copyDefaults(true);
        if (!config.exists()) {
            this.logger.info("创建配置文件中...");
            this.saveResource("config.yml", false);
        }
    }


    private void registerSQL() {
        String URL = this.getConfig().getString("SQL.URL");
        String UNAME = this.getConfig().getString("SQL.UNAME");
        String UPASSWORD = this.getConfig().getString("SQL.UPASSWORD");

        try { //初始化驱动
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("jdbc driver unavailable!");
            return;
        }
        try { //初始化数据库, catch exceptions
            connection = DriverManager.getConnection(URL, UNAME, UPASSWORD); //启动链接，链接名 conc

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void registerEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        economy = rsp.getProvider();
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new InvIcon(), this);
        getServer().getPluginManager().registerEvents(new Drug(), this);
        getServer().getPluginManager().registerEvents(new StatPvdmg(), this);
        getServer().getPluginManager().registerEvents(new Atm(), this);
        getServer().getPluginManager().registerEvents(new House(this), this);
        getServer().getPluginManager().registerEvents(new EquipSpecialAccessory(), this);
        getServer().getPluginManager().registerEvents(new HookPlotSquared(), this);
        getServer().getPluginManager().registerEvents(new HookGangPlus(), this);
        getServer().getPluginManager().registerEvents(new Taxi(this), this);
        getServer().getPluginManager().registerEvents(new Frame(this), this);
        getServer().getPluginManager().registerEvents(new Sell(this), this);
        getServer().getPluginManager().registerEvents(new Undead(this), this);
        getServer().getPluginManager().registerEvents(new Stack(), this);
        getServer().getPluginManager().registerEvents(new RefreshLore(), this);
        getServer().getPluginManager().registerEvents(new Message(), this);
        getServer().getPluginManager().registerEvents(new Sidebar(this), this);
        getServer().getPluginManager().registerEvents(new Bossbar(this), this);
        getServer().getPluginManager().registerEvents(new GtmRank(), this);
        getServer().getPluginManager().registerEvents(new Hostility(this), this);
        getServer().getPluginManager().registerEvents(new Secure(), this);
        getServer().getPluginManager().registerEvents(new GPS(), this);
    }

    private void registerCommands() {
        this.getCommand("gtmatm").setExecutor(new GtmAtm());
        this.getCommand("gtmkit").setExecutor(new GtmKit());
        this.getCommand("gtmrank").setExecutor(new GtmRank());
        this.getCommand("gtmdr").setExecutor(new GtmDailyReward());
        this.getCommand("gtmcp").setExecutor(new GtmCoinParticle());
        this.getCommand("gtmsell").setExecutor(new GtmSell());
        this.getCommand("gtmhouse").setExecutor(new GtmHouse());
        this.getCommand("gtmtaxi").setExecutor(new GtmTaxi(this));
        this.getCommand("gtmvip").setExecutor(new GtmVip());
        this.getCommand("gtmcop").setExecutor(new GtmCop());
        this.getCommand("gtmpro").setExecutor(new GtmPro());
    }

    public static void main(String[] args) {

    }
}
