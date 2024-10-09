package xyz.dwaslashe.survivalcore.commands;

import net.saidora.economy.manager.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.Arrays;
import java.util.List;

public class EmoteCommand extends Command {
    public EmoteCommand() {
        super("emotka", "/emotka", "");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        openGui(0, (Player) sender);
    }

    private void openGui(int guiID, Player player) {
        //0
        if (guiID == 0) {
            InventoryHelper inventoryHelper = new InventoryHelper(player, "&fᵩǜ", 6);

            ItemStack back = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setCustomModelData(11196);
                    itemMeta.setDisplayName(Api.fixColor("&#FF3131Zamknij"));
                });
            });

            ItemStack emoteYes = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(1);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.yes") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbTak"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.yes") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteNo = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(2);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.no") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbNo"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.no") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteFortinayt = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(3);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.fortinayt") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbFortnite taniec"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.fortinayt") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteClap = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(4);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.clap") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbKlaskanie"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.clap") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteWave = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(5);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.wave") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbMachanie"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.wave") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteLaughing = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(6);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.laughing") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbŚmiech"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.laughing") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteClubPenguin = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(7);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.clubpenguin") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbKlub pingwina"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.clubpenguin") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteTPose = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(8);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.tpose") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbpoza T"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.tpose") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteDab = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(9);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.dab") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbDab"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.dab") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteHeadScratch = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(10);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.headscratch") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbDrapanie głowy"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.headscratch") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteFacePalm = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(11);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.facepalm") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbFacepalm"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.facepalm") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteShrug = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(12);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.shrug") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbWzruszenie ramion"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.shrug") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteFloss = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(13);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.floss") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbFloss"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.floss") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteBackFlip = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(14);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.backflip") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbSalto do tyłu"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.backflip") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteBreakDance = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(15);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.backflip") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbBreakdance"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.backflip") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteSpiderMan = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(16);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.spiderman") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbSpiderman"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.spiderman") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F50,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteThinking = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(17);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.thinking") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbMyślenie"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.thinking") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteWaveJump = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(18);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.wavejump") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbSkok z machaniem"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.wavejump") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteShowItem = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(19);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.showitem") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbPokaz przedmiotu"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.showitem") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteMeditate = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(20);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.meditate") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbMedytacja"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.meditate") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });

            ItemStack emoteDeath = inventoryHelper.prepareItemStack(Material.PAPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemStack.setAmount(21);
                    itemMeta.setCustomModelData(player.hasPermission("core.emote.death1") ? 11323 : 11324);
                    itemMeta.setDisplayName(Api.fixColor("&#d2d8dbŚmierc"));
                    itemMeta.setLore(Api.fixColor(player.hasPermission("core.emote.death1") ? Arrays.asList("", " &#E7E7E7Dostęp: &#39FF14Tak", "", " &f᎘ &#FBFD8CAby użyć emotki") : Arrays.asList("", " &#FBA632Koszt: &#FFF88F5,000 &f\uE094", "", " &f᎘ #FBFD8CAby kupić")));
                });
            });


            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 10) {
                    if (player.hasPermission("core.emote.yes")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote yes " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.yes");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 11) {
                    if (player.hasPermission("core.emote.no")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote no " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.no");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 12) {
                    if (player.hasPermission("core.emote.fortinayt")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote fortinayt " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.fortinayt");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 13) {
                    if (player.hasPermission("core.emote.clap")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote clap " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.clap");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 14) {
                    if (player.hasPermission("core.emote.wave")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote wave " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.wave");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 15) {
                    if (player.hasPermission("core.emote.laughing")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote laughing " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.laughing");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 16) {
                    if (player.hasPermission("core.emote.clubpenguin")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote clubpenguin " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.clubpenguin");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 19) {
                    if (player.hasPermission("core.emote.tpose")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote tpose " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.tpose");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 20) {
                    if (player.hasPermission("core.emote.dab")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote dab " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.dab");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 21) {
                    if (player.hasPermission("core.emote.headscratch")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote headscratch " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.headscratch");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 22) {
                    if (player.hasPermission("core.emote.facepalm")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote facepalm " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.facepalm");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 23) {
                    if (player.hasPermission("core.emote.shrug")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote shrug " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.shrug");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 24) {
                    if (player.hasPermission("core.emote.floss")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote floss " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.floss");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 25) {
                    if (player.hasPermission("core.emote.backflip")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote backflip " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.backflip");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 28) {
                    if (player.hasPermission("core.emote.breakdance")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote breakdance " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.breakdance");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 29) {
                    if (player.hasPermission("core.emote.spiderman")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote spiderman " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 50000) {
                                if (balance - 50000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(50000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.spiderman");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 30) {
                    if (player.hasPermission("core.emote.thinking")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote thinking " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.thinking");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 31) {
                    if (player.hasPermission("core.emote.wavejump")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote wave_jump " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.wavejump");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 32) {
                    if (player.hasPermission("core.emote.showitem")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote show_item " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.showitem");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 33) {
                    if (player.hasPermission("core.emote.meditate")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote meditate " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.meditate");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 34) {
                    if (player.hasPermission("core.emote.death1")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emote death_1 " + player.getName());
                        player.closeInventory();
                    } else {
                        UserManager.getInstance().getUser(player).ifPresent(user -> {
                            double balance = user.balance();
                            if (balance >= 5000) {
                                if (balance - 5000 >= 0) {
                                    Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie kupiono emotke!");
                                    user.withdraw(5000);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set core.emote.death1");
                                    openGui(0, (Player) sender);
                                } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                            } else Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefixFail() + "&#fc2419Nie posiadasz wystarczającą kwotę do zakupu tej emotki!");
                        });
                    }
                } else if (e.getSlot() == 49) {
                    player.closeInventory();
                }
            });

            inventoryHelper.setItem(10, emoteYes);
            inventoryHelper.setItem(11, emoteNo);
            inventoryHelper.setItem(12, emoteFortinayt);
            inventoryHelper.setItem(13, emoteClap);
            inventoryHelper.setItem(14, emoteWave);
            inventoryHelper.setItem(15, emoteLaughing);
            inventoryHelper.setItem(16, emoteClubPenguin);

            inventoryHelper.setItem(19, emoteTPose);
            inventoryHelper.setItem(20, emoteDab);
            inventoryHelper.setItem(21, emoteHeadScratch);
            inventoryHelper.setItem(22, emoteFacePalm);
            inventoryHelper.setItem(23, emoteShrug);
            inventoryHelper.setItem(24, emoteFloss);
            inventoryHelper.setItem(25, emoteBackFlip);

            inventoryHelper.setItem(28, emoteBreakDance);
            inventoryHelper.setItem(29, emoteSpiderMan);
            inventoryHelper.setItem(30, emoteThinking);
            inventoryHelper.setItem(31, emoteWaveJump);
            inventoryHelper.setItem(32, emoteShowItem);
            inventoryHelper.setItem(33, emoteMeditate);
            inventoryHelper.setItem(34, emoteDeath);

            inventoryHelper.setItem(49, back);


            inventoryHelper.open(player);
        }
    }
}
