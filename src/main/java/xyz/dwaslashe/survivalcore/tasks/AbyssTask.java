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
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("        &#FFD700&lDZIURA DO OTCHŁANI");
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("&f楹 &#fc2419Przedmioty zostaną przeniesione do otchłani za &#ffd56c15min &fᎠ");
            Api.sendAbyssNotify("");
        } else if (getTime() == 600) {
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("        &#FFD700&lDZIURA DO OTCHŁANI");
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("&f楹 &#fc2419Przedmioty zostaną przeniesione do otchłani za &#ffd56c10min &fᎠ");
            Api.sendAbyssNotify("");
        } else if (getTime() == 300) {
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("        &#FFD700&lDZIURA DO OTCHŁANI");
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("&f楹 &#fc2419Przedmioty zostaną przeniesione do otchłani za &#ffd56c5min &fᎠ");
            Api.sendAbyssNotify("");
        } else if (getTime() == 120) {
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("        &#FFD700&lDZIURA DO OTCHŁANI");
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("&f楹 &#fc2419Przedmioty zostaną przeniesione do otchłani za &#ffd56c2min &fᎠ");
            Api.sendAbyssNotify("");
        } else if (getTime() == 15) {
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("        &#FFD700&lDZIURA DO OTCHŁANI");
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("&f楹 &#fc2419Przedmioty zostaną przeniesione do otchłani za &#ffd56c15sek &fᎠ");
            Api.sendAbyssNotify("");
        } else if (getTime() == 3) {
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("        &#FFD700&lDZIURA DO OTCHŁANI");
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("&f楹 &#fc2419Przedmioty zostaną przeniesione do otchłani za &#ffd56c3sek &fᎠ");
            Api.sendAbyssNotify("");
        } else if (getTime() == 2) {
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("        &#FFD700&lDZIURA DO OTCHŁANI");
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("&f楹 &#fc2419Przedmioty zostaną przeniesione do otchłani za &#ffd56c2sek &fᎠ");
            Api.sendAbyssNotify("");
        } else if (getTime() == 1) {
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("        &#FFD700&lDZIURA DO OTCHŁANI");
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("&f楹 &#fc2419Przedmioty zostaną przeniesione do otchłani za &#ffd56c1sek &fᎠ");
            Api.sendAbyssNotify("");
        }
        if (getTime() == 0) {
            abyssList.clear();

            var ref = new Object() {
                int moved = 0;
            };

            Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
                if (entity instanceof Item) {
                    Item item = (Item) entity;
                    ItemStack itemStack = item.getItemStack();
                    ref.moved += itemStack.getAmount();
                    Abyss.createFirst().addItem(itemStack);
                    entity.remove();
                }
            }));
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("        &#FFD700&lDZIURA DO OTCHŁANI");
            Api.sendAbyssNotify("");
            Api.sendAbyssNotify("&f楸 &#4cf739Przeniesiono &#fcb419" + ref.moved + "x &#4cf739przedmiotów do otchłani! Otchłań zostanie otwarta za &#ffd56c5sek &fᎠ");
            Api.sendAbyssNotify("");
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                AbyssTask.opened = true;
                Api.sendAbyssNotify("");
                Api.sendAbyssNotify("        &#FFD700&lDZIURA DO OTCHŁANI");
                Api.sendAbyssNotify("");
                Api.sendAbyssNotify("&f楸 &#4cf739Otchłań została otwarta &#fcb419/otchlan");
                Api.sendAbyssNotify("");
            }, 100L);
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                AbyssTask.opened = false;
                AbyssTask.setTime(320);
                Api.sendAbyssNotify("");
                Api.sendAbyssNotify("        &#FFD700&lDZIURA DO OTCHŁANI");
                Api.sendAbyssNotify("");
                Api.sendAbyssNotify("&f楹 &#fc2419Otchłań została zamknięta!");
                Api.sendAbyssNotify("");
            }, 1200L);

        }
    }
}
