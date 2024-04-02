package xyz.dwaslashe.survivalcore.commands;

import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.RandomApi;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.util.*;

public class ZielarzCommand extends Command {
    public ZielarzCommand() {
        super("zielarz", "/zielarz", "");
        setPermission("core.command.zielarz");
        setOnlyPlayer(true);
    }

    private Map<UUID, CombinationTask> tasks = new HashMap<>();

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();
        List<String> randomCombinationNames = Arrays.asList("pierwsza", "druga", "trzecia", "czwarta", "piata");
        String randomCombinationName = RandomApi.randomElementList(randomCombinationNames);
        Location locationNpc = new Location(Bukkit.getWorld("spawn"), -1874, 97, 962);

        if (Api.isNearby(player.getLocation(), locationNpc, 5)) {
            if (!tasks.containsKey(playerUUID)) {
                tasks.put(playerUUID, new CombinationTask(randomCombinationName, playerUUID));
                CombinationTask task = tasks.get(playerUUID);
                player.sendTitle(Api.fixColor("#6df03a&lNOWE ZADANIE"), Api.fixColor("&8>> &#4cf739Rozpoczęto nowe zadanie! &8<<"));
                Api.sendMessage(player, "&7&oWidze, że potrzebujesz kupić różne ciekawe sadzonki ale potrzebuje pewnych przedmiotów abyś mógł je zakupić ponieważ chce mieć ubezpieczenie ze względu na ryzyko.");
                Api.sendMessage(player, "&#f74c39Wymagane przedmioty:");
                for (Map.Entry<Material, Integer> entry : task.getRequiredItems().entrySet()) {
                    Api.sendMessage(player, "#f0c86c- " + entry.getValue() + "x " + entry.getKey().name());
                }
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Rozpocząłeś nową kombinację, masz &#ffd56c30 minut &fᎠ &#4cf739na zebranie itemów inaczej zmieni Ci się kombinacja!");
            } else {
                CombinationTask task = tasks.get(playerUUID);
                if (task.checkCombination(player.getInventory())) {
                    task.removeItems(player.getInventory());
                    tasks.remove(playerUUID);
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
                    Api.sendMessage(player, "&7&oO właśnie takich przedmiotów potrzebowałem bardzo Ci dziękuje, teraz możesz kupić jakie chcesz sadzonki. Pamiętaj, że nie zawsze tutaj jestem i zmieniam często miejsca ze względu na ryzyko.");
                    Api.sendMessage(player, "&7&oPodziele się z tobą ważna informacją bo wydajesz się ogarnięty, między innymi uważaj na grupę FBI w każdej chwili mogą Ci zajrzeć na działke w celu sprawdzeniu czy hodujesz nielegalne rośliny również mam kontakt z osobą, która chętnie od Ciebie kupi narkotyki ale trudno do niego dotrzeć. Kryje się gdzieś tutaj na wyspie ale dam Ci wskazówke szukaj na obrzeżach wyspy w środku kempingowym pojeździe.");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "shop " + player.getName() + " apteka");
                } else {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    Api.sendMessage(player, "&7&oPogrywasz ze mną? To nie są przedmioty jakie chciałem, następnym razem nie przychodź bez tych przedmiotów. Przypominam tylko, że pozostało Ci &#ffd56c" + TimerApi.getDurationBreakdownShort(task.getTimeLeft()) + " &fᎠ");
                    Api.sendMessage(player, "&#f74c39Wymagane przedmioty:");
                    for (Map.Entry<Material, Integer> entry : task.getRequiredItems().entrySet()) {
                        Api.sendMessage(player, "#f0c86c- " + entry.getValue() + "x " + entry.getKey().name());
                    }
                }
            }
        }
    }

    private class CombinationTask {
        private final String combinationName;
        private final Map<Material, Integer> requiredItems = new HashMap<>();
        private final UUID playerUUID;
        private final long startTime;

        public CombinationTask(String combinationName, UUID playerUUID) {
            this.combinationName = combinationName;
            this.playerUUID = playerUUID;
            if (combinationName.equals("pierwsza")) {
                requiredItems.put(Material.OAK_PLANKS, 32);
                requiredItems.put(Material.COBBLESTONE, 128);
                requiredItems.put(Material.BREAD, 4);
                requiredItems.put(Material.IRON_INGOT, 16);
                requiredItems.put(Material.COPPER_INGOT, 12);
                requiredItems.put(Material.COAL, 64);
                requiredItems.put(Material.CHARCOAL, 64);
            } else if (combinationName.equals("druga")) {
                requiredItems.put(Material.DIAMOND, 1);
                requiredItems.put(Material.EMERALD, 2);
                requiredItems.put(Material.GOLD_INGOT, 6);
                requiredItems.put(Material.IRON_INGOT, 12);
                requiredItems.put(Material.COAL, 64);
                requiredItems.put(Material.CARROT, 24);
            } else if (combinationName.equals("trzecia")) {
                requiredItems.put(Material.PAPER, 16);
                requiredItems.put(Material.FLINT_AND_STEEL, 1);
                //requiredItems.put(Material.AXOLOTL_BUCKET, 1);
                requiredItems.put(Material.CAKE, 1);
                requiredItems.put(Material.COOKIE, 16);
            } else if (combinationName.equals("czwarta")) {
                requiredItems.put(Material.ENCHANTING_TABLE, 1);
                requiredItems.put(Material.ENDER_PEARL, 8);
                requiredItems.put(Material.TNT, 8);
                requiredItems.put(Material.SPYGLASS, 1);
                //requiredItems.put(Material.BRUSH, 1);
                //requiredItems.put(Material.PUFFERFISH_BUCKET, 1);
            } else if (combinationName.equals("piata")) {
                requiredItems.put(Material.SLIME_BALL, 16);
                requiredItems.put(Material.SADDLE, 1);
                requiredItems.put(Material.OAK_PLANKS, 32);
                requiredItems.put(Material.DRIED_KELP_BLOCK, 16);
                //requiredItems.put(Material.POWDER_SNOW_BUCKET, 1);
                //requiredItems.put(Material.TROPICAL_FISH_BUCKET, 1);
                //requiredItems.put(Material.TOTEM_OF_UNDYING, 1);
                //requiredItems.put(Material.BRUSH, 1);
            }

            startTime = System.currentTimeMillis();

            startTimer();
        }

        public String getCombinationName() {
            return combinationName;
        }

        public Map<Material, Integer> getRequiredItems() {
            return requiredItems;
        }

        public boolean checkCombination(PlayerInventory inventory) {
            for (Map.Entry<Material, Integer> entry : requiredItems.entrySet()) {
                Material material = entry.getKey();
                int requiredAmount = entry.getValue();

                int foundAmount = 0;
                ItemStack[] contents = inventory.getContents();

                for (ItemStack itemStack : contents) {
                    if (itemStack != null && itemStack.getType() == material) {
                        foundAmount += itemStack.getAmount();
                    }
                }

                if (foundAmount < requiredAmount) {
                    return false;
                }
            }
            return true;
        }
        public void removeItems(PlayerInventory inventory) {
            for (Map.Entry<Material, Integer> entry : requiredItems.entrySet()) {
                ItemStack item = new ItemStack(entry.getKey(), entry.getValue());
                inventory.removeItem(item);
            }
        }

        public long getTimeLeft() {
            long currentTime = System.currentTimeMillis();
            long longTime = currentTime - startTime;
            return longTime;
        }

        private void startTimer() {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!tasks.containsKey(playerUUID)) {
                        return;
                    }
                    tasks.remove(playerUUID);
                }
            }.runTaskLater(Main.getPlugin(), 30 * 60 * 20);
        }
    }
}
