package xyz.dwaslashe.survivalcore.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.saidora.api.notifications.NotificationBuilder;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.MarryCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.objects.Marry;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.*;

import static xyz.dwaslashe.survivalcore.Main.plugin;

public class MarryCommand extends Command {

    public MarryCommand() {
        super("marry", "/slub <teleport, sprawdz, pocalunek, prezent, zapros, akceptuj, rozwod, uderz, pvp> <gracz>", "", "slub");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("teleport", "sprawdz", "pocalunek", "prezent", "zapros", "akceptuj", "rozwod", "uderz", "pvp"), args[0]);
        else if (args.length == 2) return Collections.singletonList("[players]");
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Marry marry = MarryCache.getInstance().compute(player.getUniqueId());

        if (args.length == 0) {
            wrongUsage();
        } else if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("akceptuj") || args[0].equalsIgnoreCase("accept")) {
                boolean d = marry.getRightuuid().toString().contains(String.valueOf(player.getUniqueId()));
                boolean c = marry.getRightuuid().equals(player.getUniqueId());
                boolean b = marry.getRightuuid() != player.getUniqueId();
                boolean a = marry.getRightuuid() == player.getUniqueId();
                Main.getPlugin().getLogger().info("d: " + d);
                Main.getPlugin().getLogger().info("c: " + c);
                Main.getPlugin().getLogger().info("b: " + b);
                Main.getPlugin().getLogger().info("a: " + a);

                if (marry.getRightuuid() == null || marry.getRightuuid().equals(player.getUniqueId())) {
                    acceptMarriageRequest(player);
                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cJesteś już w małżeństwie! Aby rozwodzić się wpisz &e/slub rozwod");

            } else if (args[0].equalsIgnoreCase("zapros") || args[0].equalsIgnoreCase("invite")) {
                if (marry.getRightuuid() == null || marry.getRightuuid().equals(player.getUniqueId())) {
                    if (args.length == 1) {
                        wrongUsage();
                    } else {
                        Main.getPlugin().getLogger().info("2" + args[1] + ", " + args[0]);
                        String targetPlayerName = args[1];
                        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
                        if (targetPlayer == player) {
                            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz siebie zaprosić do ślubu!");
                        } else if (targetPlayer != null) {
                            if (!hasSentMarriageRequest(player, targetPlayer)) {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wysłałeś zaproszenie do małżeństwa graczowi &e" + targetPlayer.getName() + "&a. Gracz ma &#ffd56c60 sekund &#ffc942⌚ &aaby zaakceptować zaproszenie!");
                                sendMarriageRequest(player, targetPlayer);
                            }
                        } else offlinePlayer();
                    }
                } else
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cJesteś już w małżeństwie! Aby rozwodzić się wpisz &e/slub rozwod");
            } else if (args[0].equalsIgnoreCase("pvp") || args[0].equalsIgnoreCase("walka")) {
                if (marry.getRightuuid() != null && marry.getRightuuid() != player.getUniqueId()) {
                    Marry marryPartner = MarryCache.getInstance().compute(marry.getRightuuid());
                    if (marry.getPvp() == null || marry.getPvp().equals("NULL")) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wyłączyłeś walke między małżeństwem!");
                        marry.setPvp("NO");
                        marryPartner.setPvp("NO");
                    } else if (marry.getPvp().equalsIgnoreCase("YES")) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie wyłączyłeś walke między małżeństwem!");
                        marry.setPvp("NO");
                        marryPartner.setPvp("NO");
                    } else if (marry.getPvp().equalsIgnoreCase("NO")) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie włączyłeś walke między małżeństwem!");
                        marry.setPvp("YES");
                        marryPartner.setPvp("YES");
                    }
                } else
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie jesteś w żadnym małżeństwie!");
            } else if (args[0].equalsIgnoreCase("uderz") || args[0].equalsIgnoreCase("slap")) {
                if (isHusbandPlayer(player)) {
                    World world = player.getWorld();
                    Player husband = Bukkit.getPlayer(marry.getRightuuid());

                    if (Api.isNearby(player, husband, 3)) {
                        husband.setVelocity(husband.getLocation().toVector().subtract(husband.getLocation().getDirection()).multiply(-3));
                        world.spawnParticle(Particle.VILLAGER_ANGRY, player.getLocation().add(0.0D, 2.0D, 0.0D), 10);
                        world.spawnParticle(Particle.VILLAGER_ANGRY, husband.getLocation().add(0.0D, 2.0D, 0.0D), 10);
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie uderzyłeś małżonka!");
                        Api.sendMessage(husband, Main.pluginConfig.getMessages().getPrefix() + "&aTwój małżonek uderzył Cię!");
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz uderzyć małżonka bo jesteś za daleko od gracza!");
                }
            } else if (args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp")) {
                if (isHusbandPlayer(player)) {
                    Player husband = Bukkit.getPlayer(marry.getRightuuid());
                    player.teleport(husband);
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie przteleportowano się do małżonka!");
                    Api.sendMessage(husband, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie twój małżonek przeteleportował się do Ciebie!");
                }
            } else if (args[0].equalsIgnoreCase("sprawdz") || args[0].equalsIgnoreCase("check")) {
                if (marry.getRightuuid() != null || (!marry.getRightuuid().equals(player.getUniqueId()))) {
                    OfflinePlayer husband = Bukkit.getOfflinePlayer(marry.getRightuuid());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aTwoja druga połówka: &e" + husband.getName());
                } else
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie jesteś w żadnym małżeństwie!");
            } else if (args[0].equalsIgnoreCase("pocalunek") || args[0].equalsIgnoreCase("kiss")) {
                if (isHusbandPlayer(player)) {
                    Player husband = Bukkit.getPlayer(marry.getRightuuid());
                    World world = player.getWorld();
                    if (Api.isNearby(player, husband, 3)) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie pocałowałeś gracza &e" + husband.getName());
                        Api.sendMessage(husband, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zostałeś pocałowany przez gracza &e" + player.getName());
                        world.spawnParticle(Particle.HEART, player.getLocation().add(0.0D, 2.0D, 0.0D), 10);
                        world.spawnParticle(Particle.HEART, husband.getLocation().add(0.0D, 2.0D, 0.0D), 10);
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz pocałować bo jesteś za daleko od gracza!");
                }
            } else if (args[0].equalsIgnoreCase("prezent") || args[0].equalsIgnoreCase("gift")) {
                if (isHusbandPlayer(player)) {
                    Player husband = Bukkit.getPlayer(marry.getRightuuid());
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    if (itemStack.getType() == Material.AIR) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz dać prezent swojemu małżonkowi bo nie trzymasz żadnego przedmiotu!");
                        return;
                    }
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie dałeś prezent swojemu małżonkowi!");
                    Api.sendMessage(husband, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie dostałeś prezent od swojego małżonka!");
                    player.getInventory().remove(itemStack);
                    Api.giveOrDrop(husband, itemStack);
                }
            } else if (args[0].equalsIgnoreCase("rozwod") || args[0].equalsIgnoreCase("divorce")) {
                if (marry.getRightuuid() != null || !(marry.getRightuuid().equals(player.getUniqueId()))) {
                    OfflinePlayer husband = Bukkit.getOfflinePlayer(marry.getRightuuid());
                    openGui(0, player, husband);
                } else
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie jesteś w żadnym małżeństwie!");
            }
        }
    }

    private static Map<Player, Player> marriageRequests = new HashMap<>();

    public static void sendMarriageRequest(Player player, Player target) {
        marriageRequests.put(target, player);
        Api.sendMessage(target, "");
        Api.sendMessage(target, "        &#a503fc&lZAPROSZENIE DO MAŁŻEŃSTWA");
        Api.sendMessage(target, "");
        Api.sendMessage(target, "&8>> &#8dfa52Otrzymałeś zaproszenie od &#46b9f2" + player.getName());
        Api.sendMessage(target, "&8>> &#8dfa52Masz &#ffd56c60 sekund &#ffc942⌚ &#8dfa52na potwierdzenie zaproszenia do gry!");
        Api.sendMessage(target, "");
        NotificationBuilder.of(NotificationBuilder.NotificationType.CHAT,
                "            <hover:show_text:\"<white>Kliknij aby zaakceptować!\"><click:suggest_command:/marry akceptuj><#4BF72D>&l[AKCEPTUJ]</click></hover> <hover:show_text:\"<white>Kliknij aby odmówić!\"><click:suggest_command:/marry odmow><#F7442D>&l[ODMÓW]</click></hover>").send(target);
        Api.sendMessage(target, "");

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (marriageRequests.containsKey(target) && marriageRequests.get(target) == player) {
                marriageRequests.remove(target);
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cZaproszenie do małżeństwa dla gracza &e" + target.getName() + "&c wygasło");
                Api.sendMessage(target, Main.pluginConfig.getMessages().getPrefix() + "&cZaproszenie do małżeństwa od gracza &e" + player.getName() + "&c wygasło");
            }
        }, 60 * 20L);
    }

    public static boolean hasSentMarriageRequest(Player player, Player target) {
        return marriageRequests.containsKey(target) && marriageRequests.get(target) == player;
    }
    public static void acceptMarriageRequest(Player player) {
        if (marriageRequests.containsKey(player)) {
            Player partner = marriageRequests.get(player);
            Marry marryPartner = MarryCache.getInstance().compute(partner.getUniqueId());
            marryPartner.setRightuuid(player.getUniqueId());
            Marry marry = MarryCache.getInstance().compute(player.getUniqueId());
            marry.setRightuuid(partner.getUniqueId());
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie zaakceptowałeś zaproszenie do małżeństwa od gracza &e" + partner.getName());
            Api.sendMessage(partner, Main.pluginConfig.getMessages().getPrefix() + "&aGracz &e" + player.getName() + " &azaakceptował twoje zaproszenie do małżeństwa!");
            Api.sendBroadcast("\n        &#a503fc&lŚLUB\n \n&8>> &#8dfa52Gracz &#FFC42E" + player.getName() + " &#8dfa52poślubił się z &#f7482d" + partner.getName() + " &#8dfa52życzmy im udanej miłości! &#fc2c03&l❤ \n&8>> &#8dfa52Napisz nowemu małżeństwu coś miłego! :=)  \n ");
            for (Player all : Bukkit.getOnlinePlayers()) {
                World world = all.getWorld();
                world.spawnParticle(Particle.HEART, all.getLocation().add(0.0D, 2.0D, 0.0D), 10);
            }

            marriageRequests.remove(player);
        } else {
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie masz żadnych oczekujących zaproszeń do małżeństwa!");
        }
    }

    private void openGui(int guiID, Player player, OfflinePlayer secondPlayer) {
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Potwierdzenie rozwodu z " + secondPlayer.getName(), 3);

            ItemStack glass_black = inventoryHelper.prepareItemStack(Material.BLACK_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack yes = inventoryHelper.prepareItemStack(Material.LIME_WOOL, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#4ffa2dPotwierdzam rozwód z &#faa82d" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby pozwolić na rozwód!")));
                });
            });

            ItemStack no = inventoryHelper.prepareItemStack(Material.RED_WOOL, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#fa302dNie potwierdzam rozwodu z &#faa82d" + player.getName()));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby nie pozwolić na rozwód!")));
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 11) {
                    Marry marry = MarryCache.getInstance().compute(player.getUniqueId());
                    Marry marrypartner = MarryCache.getInstance().compute(secondPlayer.getUniqueId());
                    marry.setRightuuid(player.getUniqueId());
                    marry.setPvp("NULL");
                    marrypartner.setRightuuid(secondPlayer.getUniqueId());
                    marrypartner.setPvp("NULL");
                    Api.sendBroadcast("\n        &#a503fc&lROZWÓD\n \n&8>> &#8dfa52Gracz &#FFC42E" + player.getName() + " &#8dfa52zabrał rozwód z &#f7482d" + secondPlayer.getName() + "  \n ");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie potwierdziłeś rozwód z &e" + secondPlayer.getName());
                    if (secondPlayer.isOnline()) {
                        Api.sendMessage((Player) secondPlayer, Main.pluginConfig.getMessages().getPrefix() + "&cTwój małżonek rozwiódł się z tobą!");
                    }
                    player.closeInventory();
                } else if (e.getSlot() == 15) {
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&aPomyślnie odrzuciłeś rozwód z &e" + secondPlayer.getName());
                    player.closeInventory();
                }
            });

            inventoryHelper.setItemRange(0, 27, glass_black);

            inventoryHelper.setItem(11, yes);
            inventoryHelper.setItem(15, no);

            inventoryHelper.open(player);
        }
    }

    public boolean isHusbandPlayer (Player player) {
        Marry marry = MarryCache.getInstance().compute(player.getUniqueId());
        if (marry.getRightuuid() != null) {
            if (!(marry.getRightuuid().equals(player.getUniqueId()))) {
                if (Bukkit.getOfflinePlayer(marry.getRightuuid()).isOnline()) {
                    return true;
                } else {
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cTwój małżonek jest teraz &enieaktywny!");
                    return false;
                }
            } else {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie jesteś w żadnym małżeństwie!");
                return  false;
            }
        } else {
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie jesteś w żadnym małżeństwie!");
            return  false;
        }

    }

}