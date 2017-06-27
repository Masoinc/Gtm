package me.masonic.mc.Function;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

/**
 * Mason Project
 * 2017-6-27-0027
 */
public class HoverMsg {
//    void sendHoverMsg(Player p) {
//        p.spigot().sendMessage(sms(p, "sss", "Sss"));
//    }

    public TextComponent getHoverMsg(String text, String hover) {
        TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', text));
        if (hover != null) {
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', hover))).create()));
        }

        message.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "/g create "));

        return message;

    }
}
