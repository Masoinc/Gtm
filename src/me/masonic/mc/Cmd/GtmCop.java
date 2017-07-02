package me.masonic.mc.Cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Mason Project
 * 2017-7-1-0001
 */
public class GtmCop implements CommandExecutor{


    @Override
    public boolean onCommand(CommandSender c, Command cmd, String s, String[] args) {

        Player p= (Player)c;

//        switch(args.length) {
//            case 1:
//                switch (args[0]) {
//                    case "test1":
//                        HookMythicMobs.spawnCop(p,1);
//                        return true;
//                    case "test2":
//                        HookMythicMobs.spawnCop(p,1,10);
//                        return true;
//                }
//        }

        return true;
    }
}
