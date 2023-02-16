package xyz.dwaslashe.survivalcore.commands.managers;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import xyz.dwaslashe.survivalcore.helpers.ReflectionHelper;

import java.lang.reflect.Field;

public class CommandManager {

    private static final Class<?> CraftServerClass = ReflectionHelper.getOcbClass("CraftServer");
    private static Field commandMapField;

    public static SimpleCommandMap commandMap;

    static {
        try {
            commandMapField = CraftServerClass.getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer());
            commandMapField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

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