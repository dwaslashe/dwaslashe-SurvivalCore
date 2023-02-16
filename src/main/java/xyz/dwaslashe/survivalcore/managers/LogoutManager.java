package xyz.dwaslashe.survivalcore.managers;

import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.objects.Logout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LogoutManager {
    public static List<Logout> logouts = new ArrayList();

    public LogoutManager() {
    }

    public static List<Logout> getLogouts() {
        return logouts;
    }

    public static boolean exists(Player player) {
        Iterator var1 = getLogouts().iterator();

        Logout logout;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            logout = (Logout)var1.next();
        } while(!logout.getPlayer().getName().equalsIgnoreCase(player.getName()));

        return true;
    }
}