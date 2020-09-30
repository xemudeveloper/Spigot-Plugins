/*
Plugin Developed & Maintained by Xemu
 */
package me.xemu.simplefly;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class SimpleFly extends JavaPlugin implements CommandExecutor
{

    private ArrayList<Player> flying = new ArrayList<>();

    public static String translate(String toTranslate)
    {
        return ChatColor.translateAlternateColorCodes('&', toTranslate);
    };

    public static void send(Player player, String message)
    {
        player.sendMessage(translate(message));
    };

    public void generateConfig()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override public void onEnable()
    {
        getLogger().info("Loading Plugin");

        this.getCommand("fly").setExecutor(this);
        generateConfig();

        getLogger().info("Plugin Enabled");
    };

    @Override public void onDisable() 
    {
        getLogger().info("Disabling Plugin");
        getLogger().info("Plugin Disabled");
    };

    @Override public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
    {

        if(!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "Only players may execute /fly");
            return true;
        };

        final Player player = (Player) sender;


        if(player.hasPermission(getConfig().getString("Settings.Permission")))
        {
            if(!(flying.contains(player)))
            {
                flying.add(player);
                player.sendMessage(translate(getConfig().getString("Enabled.Message")));
                //send(player, getConfig().getString("Enabled.Message"));
                player.setAllowFlight(true);
            };

            if(flying.contains(player)) {
                flying.remove(player);
                player.sendMessage(translate(getConfig().getString("Disabled.Message")));
                //send(player, getConfig().getString("Disabled.Message"));
                player.setAllowFlight(false);
            };
        } else {
            send(player, getConfig().getString("Settings.NoPermission"));
        };
        return true;
    };
};