package xyz.dwaslashe.survivalcore.commands;

import me.realized.duels.DuelsPlugin;
import net.saidora.api.notifications.NotificationBuilder;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.cache.MarryCache;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.objects.Logout;
import xyz.dwaslashe.survivalcore.objects.Marry;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.RegionApi;

import java.util.*;

import static xyz.dwaslashe.survivalcore.Main.plugin;

public class MarryCommand extends Command {

    public MarryCommand() {
        super("marry", "/slub <teleport, sprawdz, pocalunek, prezent, zapros, akceptuj, rozwod, pvp> <gracz>", "", "slub");
        setPermission("core.command.marry");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Api.startsWith(Arrays.asList("teleport", "sprawdz", "pocalunek", "prezent", "zapros", "akceptuj", "rozwod", "pvp"), args[0]);
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
                if (marry.getRightuuid() == null || marry.getRightuuid().equals(player.getUniqueId())) {
                    if (RegionApi.isInRegion(player.getLocation(), "kosciol")) {
                        acceptMarriageRequest(player);
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Aby zakceptować zaproszenie do małżeństwa musisz to zrobić w kościele! &#fcb419/warp kosciol");
                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Jesteś już w małżeństwie! Aby rozwodzić się wpisz &#fcb419/slub rozwod");
            } else if (args[0].equalsIgnoreCase("zapros") || args[0].equalsIgnoreCase("invite")) {
                if (marry.getRightuuid() == null || marry.getRightuuid().equals(player.getUniqueId())) {
                    if (RegionApi.isInRegion(player.getLocation(), "kosciol")) {
                        if (args.length == 1) {
                            wrongUsage();
                        } else {
                            Main.getPlugin().getLogger().info("2" + args[1] + ", " + args[0]);
                            String targetPlayerName = args[1];
                            Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
                            if (targetPlayer == player) {
                                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz siebie zaprosić do ślubu!");
                            } else if (targetPlayer != null) {
                                if (!hasSentMarriageRequest(player, targetPlayer)) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wysłałeś zaproszenie do małżeństwa graczowi &#fcb419" + targetPlayer.getName() + "&#4cf739. Gracz ma &#ffd56c60 sekund &fᎠ &#4cf739aby zaakceptować zaproszenie!");
                                    sendMarriageRequest(player, targetPlayer);
                                }
                            } else offlinePlayer();
                        }
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Aby zaprosić kogoś do małżeństwa musisz to zrobić w kościele! &#fcb419/warp kosciol");
                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Jesteś już w małżeństwie! Aby rozwodzić się wpisz &#fcb419/slub rozwod");
            } else if (args[0].equalsIgnoreCase("pvp") || args[0].equalsIgnoreCase("walka")) {
                if (marry.getRightuuid() != null && marry.getRightuuid() != player.getUniqueId()) {
                    Marry marryPartner = MarryCache.getInstance().compute(marry.getRightuuid());
                    if (marry.getPvp() == null || marry.getPvp().equals("NULL")) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączyłeś walke między małżeństwem!");
                        marry.setPvp("NO");
                        marryPartner.setPvp("NO");
                    } else if (marry.getPvp().equalsIgnoreCase("YES")) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie wyłączyłeś walke między małżeństwem!");
                        marry.setPvp("NO");
                        marryPartner.setPvp("NO");
                    } else if (marry.getPvp().equalsIgnoreCase("NO")) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie włączyłeś walke między małżeństwem!");
                        marry.setPvp("YES");
                        marryPartner.setPvp("YES");
                    }
                } else
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie jesteś w żadnym małżeństwie!");
            //} else if (args[0].equalsIgnoreCase("uderz") || args[0].equalsIgnoreCase("slap")) {
            //    if (isHusbandPlayer(player)) {
            //        World world = player.getWorld();
            //        Player husband = Bukkit.getPlayer(marry.getRightuuid());
            //        if (Api.isNearby(player, husband, 3)) {
            //            husband.setVelocity(husband.getLocation().toVector().subtract(husband.getLocation().getDirection()).multiply(-3));
            //            world.spawnParticle(Particle.VILLAGER_ANGRY, player.getLocation().add(0.0D, 2.0D, 0.0D), 10);
            //            world.spawnParticle(Particle.VILLAGER_ANGRY, husband.getLocation().add(0.0D, 2.0D, 0.0D), 10);
            //            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie uderzyłeś małżonka!");
            //            Api.sendMessage(husband, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Twój małżonek uderzył Cię!");
            //        } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz uderzyć małżonka bo jesteś za daleko od gracza!");
            //    }
            } else if (args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp")) {
                if (isHusbandPlayer(player)) {
                    Player husband = Bukkit.getPlayer(marry.getRightuuid());
                    player.teleport(husband);
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie przteleportowano się do małżonka!");
                    Api.sendMessage(husband, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie twój małżonek przeteleportował się do Ciebie!");
                }
            } else if (args[0].equalsIgnoreCase("sprawdz") || args[0].equalsIgnoreCase("check")) {
                if (marry.getRightuuid() != null || (!marry.getRightuuid().equals(player.getUniqueId()))) {
                    OfflinePlayer husband = Bukkit.getOfflinePlayer(marry.getRightuuid());
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Twoja druga połówka: &#fcb419" + husband.getName());
                } else
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie jesteś w żadnym małżeństwie!");
            } else if (args[0].equalsIgnoreCase("pocalunek") || args[0].equalsIgnoreCase("kiss")) {
                if (isHusbandPlayer(player)) {
                    Player husband = Bukkit.getPlayer(marry.getRightuuid());
                    World world = player.getWorld();
                    if (Api.isNearby(player, husband, 3)) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie pocałowałeś gracza &#fcb419" + husband.getName());
                        Api.sendMessage(husband, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zostałeś pocałowany przez gracza &#fcb419" + player.getName());
                        world.spawnParticle(Particle.HEART, player.getLocation().add(0.0D, 2.0D, 0.0D), 10);
                        world.spawnParticle(Particle.HEART, husband.getLocation().add(0.0D, 2.0D, 0.0D), 10);
                    } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz pocałować bo jesteś za daleko od gracza!");
                }
            } else if (args[0].equalsIgnoreCase("prezent") || args[0].equalsIgnoreCase("gift")) {
                if (isHusbandPlayer(player)) {
                    Player husband = Bukkit.getPlayer(marry.getRightuuid());
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    if (itemStack.getType() == Material.AIR) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz dać prezent swojemu małżonkowi bo nie trzymasz żadnego przedmiotu!");
                        return;
                    }
                    DuelsPlugin duelsPlugin = new DuelsPlugin();
                    if (duelsPlugin.getArenaManager().isInMatch(husband)) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz dać prezent swojemu małżonkowi bo jest w trakcie walki!");
                        return;
                    }
                    Logout logout = Logout.get(husband);
                    if (logout.getTime() > System.currentTimeMillis()) {
                        Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie możesz dać prezent swojemu małżonkowi bo jest w trakcie walki!");
                        return;
                    }

                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie dałeś prezent swojemu małżonkowi!");
                    Api.sendMessage(husband, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie dostałeś prezent od swojego małżonka!");
                    player.getInventory().remove(itemStack);
                    Api.giveOrDrop(husband, itemStack);
                }
            } else if (args[0].equalsIgnoreCase("rozwod") || args[0].equalsIgnoreCase("divorce")) {
                if (marry.getRightuuid() != null && (!marry.getRightuuid().equals(player.getUniqueId()))) {
                    OfflinePlayer husband = Bukkit.getOfflinePlayer(marry.getRightuuid());
                    openGui(0, player, husband);
                } else
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie jesteś w żadnym małżeństwie!");
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
        Api.sendMessage(target, "&8>> &#8dfa52Masz &#ffd56c60 sekund &fᎠ &#8dfa52na potwierdzenie zaproszenia do gry!");
        Api.sendMessage(target, "");
        NotificationBuilder.of(NotificationBuilder.NotificationType.CHAT,
                "            <hover:show_text:\"<white>Kliknij aby zaakceptować!\"><click:suggest_command:/marry akceptuj><#4BF72D>&l[AKCEPTUJ]</click></hover> <hover:show_text:\"<white>Kliknij aby odmówić!\"><click:suggest_command:/marry odmow><#F7442D>&l[ODMÓW]</click></hover>").send(target);
        Api.sendMessage(target, "");

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (marriageRequests.containsKey(target) && marriageRequests.get(target) == player) {
                marriageRequests.remove(target);
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Zaproszenie do małżeństwa dla gracza &#fcb419" + target.getName() + "&#fc2419 wygasło");
                Api.sendMessage(target, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Zaproszenie do małżeństwa od gracza &#fcb419" + player.getName() + "&#fc2419 wygasło");
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
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie zaakceptowałeś zaproszenie do małżeństwa od gracza &#fcb419" + partner.getName());
            Api.sendMessage(partner, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Gracz &#fcb419" + player.getName() + " &#4cf739zaakceptował twoje zaproszenie do małżeństwa!");
            Api.sendBroadcast("\n        &#a503fc&lŚLUB\n \n&8>> &#8dfa52Gracz &#FFC42E" + player.getName() + " &#8dfa52poślubił się z &#f7482d" + partner.getName() + " &#8dfa52życzmy im udanej miłości! &#fc2c03&l❤ \n&8>> &#8dfa52Napisz nowemu małżeństwu coś miłego! =)  \n ");
            for (Player all : Bukkit.getOnlinePlayers()) {
                World world = all.getWorld();
                world.spawnParticle(Particle.HEART, all.getLocation().add(0.0D, 2.0D, 0.0D), 10);
            }

            marriageRequests.remove(player);
        } else {
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie masz żadnych oczekujących zaproszeń do małżeństwa!");
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
                    Marry marryPartner = MarryCache.getInstance().compute(secondPlayer.getUniqueId());
                    marry.setRightuuid(player.getUniqueId());
                    marry.setPvp("NULL");
                    marryPartner.setRightuuid(secondPlayer.getUniqueId());
                    marryPartner.setPvp("NULL");
                    Api.sendBroadcast("\n        &#a503fc&lROZWÓD\n \n&8>> &#8dfa52Gracz &#FFC42E" + player.getName() + " &#8dfa52zabrał rozwód z &#f7482d" + secondPlayer.getName() + "  \n ");
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie potwierdziłeś rozwód z &#fcb419" + secondPlayer.getName());
                    if (secondPlayer.isOnline()) {
                        Api.sendMessage((Player) secondPlayer, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Twój małżonek rozwiódł się z tobą!");
                    }
                    player.closeInventory();
                } else if (e.getSlot() == 15) {
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie odrzuciłeś rozwód z &#fcb419" + secondPlayer.getName());
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
                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Twój małżonek jest teraz &#fcb419nieaktywny!");
                    return false;
                }
            } else {
                Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie jesteś w żadnym małżeństwie!");
                return  false;
            }
        } else {
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie jesteś w żadnym małżeństwie!");
            return  false;
        }

    }

}