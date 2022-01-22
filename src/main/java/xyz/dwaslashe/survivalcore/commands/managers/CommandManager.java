package xyz.dwaslashe.survivalcore.commands.managers;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;

public class CommandManager {
    public static SimpleCommandMap commandMap = ((CraftServer) Bukkit.getServer()).getCommandMap();

    public static void register(xyz.dwaslashe.survivalcore.commands.managers.Command command, boolean enable){
        if (enable == true) {
            if(command.isRegistered()){
            } else commandMap.register(command.getName(), command);
        } else unregister(command);
    }
    public static void unregister(xyz.dwaslashe.survivalcore.commands.managers.Command command){
        if(!command.isRegistered()){
        } else command.unregister(commandMap);
    }
}