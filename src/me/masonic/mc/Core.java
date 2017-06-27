package me.masonic.mc;

import com.intellectualcrafters.plot.api.PlotAPI;
import me.masonic.mc.Cmd.*;
import me.masonic.mc.Function.*;
import me.masonic.mc.Hook.HookGangPlus;
import me.masonic.mc.Hook.HookPapi;
import me.masonic.mc.Hook.HookPlotSquared;
import me.masonic.mc.Listener.PlayerDeath;
import me.masonic.mc.LoreStat.StatPvdmg;
import me.masonic.mc.Utility.EquipSpecialAccessory;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Core extends JavaPlugin {

    private static Economy economy = null;
    private static org.bukkit.permissions.Permission permission = null;
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/gtm";
    private static final String UNAME = "mc";
    private static final String UPASSWORD = "492357816";
    private static Connection connection;

    // 插件首次启用时，触发此事件
    public static Connection getConnection() {
        return connection;
    }

    public static Economy getEconomy() {
        return economy;
    }

    private static void initializeEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        economy = rsp.getProvider();
    }

    @Override
    public void onEnable() {

        new HookPapi(this).hook(); //Hook Papi

        getServer().getPluginManager().registerEvents(new InvIcon(), this);
        getServer().getPluginManager().registerEvents(new Drug(), this);
        getServer().getPluginManager().registerEvents(new StatPvdmg(), this);
        getServer().getPluginManager().registerEvents(new Atm(), this);
        getServer().getPluginManager().registerEvents(new House(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
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
        this.getCommand("gtmatm").setExecutor(new GtmAtm());
        this.getCommand("gtmkit").setExecutor(new GtmKit());
        this.getCommand("gtmrank").setExecutor(new GtmRank());
        this.getCommand("gtmdr").setExecutor(new GtmDailyReward());
        this.getCommand("gtmcp").setExecutor(new GtmCoinParticle());
        this.getCommand("gtmsell").setExecutor(new GtmSell());
        this.getCommand("gtmhouse").setExecutor(new GtmHouse());
        this.getCommand("gtmtaxi").setExecutor(new GtmTaxi(this));
        this.getCommand("gtmvip").setExecutor(new GtmVip());
        initializeEconomy(); //Hook EcoSystem


        PluginManager manager = Bukkit.getServer().getPluginManager();
        final Plugin plotsquared = manager.getPlugin("PlotSquared");

        PlotAPI api = new PlotAPI();

        new Undead(this).checkTime();
        new Sidebar(this).sendSchedulely();
        new Bossbar(this).sendSchedulely();


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

    // 插件停用时，触发此事件

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

    public static void main(String[] args) {

    }
}
