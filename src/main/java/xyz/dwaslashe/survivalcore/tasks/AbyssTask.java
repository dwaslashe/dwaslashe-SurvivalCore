package xyz.dwaslashe.survivalcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import xyz.dwaslashe.survivalcore.objects.Abyss;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.HashMap;
import java.util.Map;

public class AbyssTask extends BukkitRunnable {

    private static int time = 300;

    private static boolean opened = false;

    public static Map<Integer, Abyss> abyssList = new HashMap<>();

    private Main plugin;

    public AbyssTask(Main plugin) {
        this.plugin = plugin;
        runTaskTimer(plugin, 0L, 20L);
    }

    public static boolean isOpened() {
        return opened;
    }

    public static int getTime() {
        return time;
    }

    public static void setTime(int time) {
        AbyssTask.time = time;
    }

    @Override
    public void run() {
        setTime(getTime() - 1);
        if (getTime() == 900) {
            Api.sendAbyssNotify("&#FFD700&lOTCHŁAŃ &8>> &#7DE95FPrzedmioty zostaną przeniesione do otchłani za &#FFF67915 minut");
        } else if (getTime() == 600) {
            Api.sendAbyssNotify("&#FFD700&lOTCHŁAŃ &8>> &#7DE95FPrzedmioty zostaną przeniesione do otchłani za &#FFF67910 minut");
        } else if (getTime() == 300) {
            Api.sendAbyssNotify("&#FFD700&lOTCHŁAŃ &8>> &#7DE95FPrzedmioty zostaną przeniesione do otchłani za &#FFF6795 minut");
        } else if (getTime() == 120) {
            Api.sendAbyssNotify("&#FFD700&lOTCHŁAŃ &8>> &#7DE95FPrzedmioty zostaną przeniesione do otchłani za &#FFF6792 minuty");
        } else if (getTime() == 15) {
            Api.sendAbyssNotify("&#FFD700&lOTCHŁAŃ &8>> &#7DE95FPrzedmioty zostaną przeniesione do otchłani za &#FFF67915 sekund");
        } else if (getTime() == 3) {
            Api.sendAbyssNotify("&#FFD700&lOTCHŁAŃ &8>> &#7DE95FPrzedmioty zostaną przeniesione do otchłani za &#FFF6793 sekundy");
        } else if (getTime() == 2) {
            Api.sendAbyssNotify("&#FFD700&lOTCHŁAŃ &8>> &#7DE95FPrzedmioty zostaną przeniesione do otchłani za &#FFF6792 sekundy");
        } else if (getTime() == 1) {
            Api.sendAbyssNotify("&#FFD700&lOTCHŁAŃ &8>> &#7DE95FPrzedmioty zostaną przeniesione do otchłani za &#FFF6791 sekunde");
        } else if (getTime() == 0) {
            abyssList.clear();

            var ref = new Object(){
                int moved = 0;
            };

            Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
                if(entity instanceof Item){
                    Item item = (Item) entity;
                    ItemStack itemStack = item.getItemStack();
                    ref.moved += itemStack.getAmount();
                    Abyss.createFirst().addItem(itemStack);
                    entity.remove();
                }
            }));

            Api.sendAbyssNotify("&#FFD700&lOTCHŁAŃ &8>> &#7DE95FPrzeniesiono &#FFF679" + ref.moved + " &#7DE95Fprzedmiotów do otchłani! Otchłan zostanie otwarta za &#FFF6795 sekund!");
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                AbyssTask.opened = true;
                Api.sendAbyssNotify("&#FFD700&lOTCHŁAŃ &8>> &#7DE95FOtchlan została otwarta &#FFF679/otchlan");
            },  100L);
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                AbyssTask.opened = false;
                AbyssTask.setTime(320);
                Api.sendAbyssNotify("&#FFD700&lOTCHŁAŃ &8>> &#FF301FOtchlan zostala zamknieta!");
            },  1200L);
        }
    }
}
