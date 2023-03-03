package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.helpers.InventoryHelper;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VanishCommand extends Command implements Listener {
    public VanishCommand(){
        super("vanish", "/vanish <on, off, check, panel> <nick>", "", "v");
        setPermission("core.command.vanish");
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player)sender;
        VanishObject vanishObject = VanishObject.get(player.getName());
        if (args.length == 0) {
            if (vanishObject.isEnable()) {
                vanishObject.setEnable(false);
                Api.sendActionBar(player, "&8>> &cVanish został wyłączony &8<<");
            } else {
                vanishObject.setEnable(true);
                Api.sendActionBar(player, "&8>> &aVanish został włączony &8<<");
            }
        } else if(args.length == 2){
            player = Bukkit.getPlayer(args[1]);
        }
        if(args.length >= 1){
            if(player == null && args.length == 1) {
                wrongUsage();
            } else {
                if(player != null){
                    if(args[0].equalsIgnoreCase("on")){
                        vanishObject.setEnable(true);
                        Api.sendActionBar(player, "&8>> &#39FF14Vanish został włączony &8<<");
                    } else if(args[0].equalsIgnoreCase("off")) {
                        vanishObject.setEnable(false);
                        Api.sendActionBar(player, "&8>> &#f00c0cVanish został wyłączony &8<<");
                    } if (!player.hasPermission("core.command.vanish.more")) {
                        player.sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &cNie posiadasz uprawnien &8(&ecore.command.vanish.more&8) &8<<"));
                        return;
                    } else if(args[0].equalsIgnoreCase("check")){
                        if(sender.getName().equalsIgnoreCase(player.getName())){
                            Api.sendActionBar(player, " &8>> &7Twój vanish jest " + (vanishObject.isEnable() ? "&awłączony" : "&cwyłączony" + " &8<<"));
                        } else {
                            Api.sendActionBar(player, " &8>> &7Vanish gracza &a" + player.getName() + " &7jest " + (vanishObject.isInteract() ? "&awłączony" : "&cwyłączony" + " &8<<"));
                        }
                    } else if(args[0].equalsIgnoreCase("panel") && sender instanceof Player){
                        openGui(0, player);
                    }
                } else offlinePlayer();
            }
        }
    }

    private void openGui(int guiID, Player player) {
        //0
        if (guiID == 0) {
            VanishObject vanishObject = VanishObject.get(player);
            InventoryHelper inventoryHelper = new InventoryHelper(player, "Panel", 3);

            ItemStack build = inventoryHelper.prepareItemStack(Material.GRASS_BLOCK, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFC42EBudowanie"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby włączyć/wyłączyć opcje!")));
                });
            });

            ItemStack pvp = inventoryHelper.prepareItemStack(Material.DIAMOND_SWORD, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFC42EPlayer VS Player"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby włączyć/wyłączyć opcje!")));
                });
            });

            ItemStack pve = inventoryHelper.prepareItemStack(Material.SKELETON_SKULL, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFC42EPlayer VS Entity"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby włączyć/wyłączyć opcje!")));
                });
            });

            ItemStack interact = inventoryHelper.prepareItemStack(Material.CHEST, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFC42EInterakcja z blokami"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby włączyć/wyłączyć opcje!")));
                });
            });

            ItemStack useful = inventoryHelper.prepareItemStack(Material.LAVA_BUCKET, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFC42ERozlewanie/Zabieranie"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby włączyć/wyłączyć opcje!")));
                });
            });

            ItemStack pickup = inventoryHelper.prepareItemStack(Material.HOPPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFC42EPodnoszenie przedmiotów"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby włączyć/wyłączyć opcje!")));
                });
            });

            ItemStack drop = inventoryHelper.prepareItemStack(Material.DROPPER, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFC42EWyrzucanie przedmiotów"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby włączyć/wyłączyć opcje!")));
                });
            });

            ItemStack shoot = inventoryHelper.prepareItemStack(Material.BOW, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(Api.fixColor("&#FFC42EStrzelanie"));
                    itemMeta.setLore(Api.fixColor(Arrays.asList("", " &#FBFD8C&nKliknij aby włączyć/wyłączyć opcje!")));
                });
            });

            ItemStack buildGlass = inventoryHelper.prepareItemStack(vanishObject.isBuild() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack pvpGlass = inventoryHelper.prepareItemStack(vanishObject.isPvp() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack pveGlass = inventoryHelper.prepareItemStack(vanishObject.isPve() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack interactGlass = inventoryHelper.prepareItemStack(vanishObject.isInteract() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack usefulGlass = inventoryHelper.prepareItemStack(vanishObject.isUseful() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack pickupGlass = inventoryHelper.prepareItemStack(vanishObject.isPickup() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack dropGlass = inventoryHelper.prepareItemStack(vanishObject.isDrop() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack shootGlass = inventoryHelper.prepareItemStack(vanishObject.isShoot() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            ItemStack cameraGlass = inventoryHelper.prepareItemStack(vanishObject.isCamera() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE, itemStack -> {
                inventoryHelper.editMetaForItemStack(itemStack, itemMeta -> {
                    itemMeta.setDisplayName(" ");
                });
            });

            inventoryHelper.click(e -> {
                e.setCancelled(true);
                if (e.getSlot() == 9) {
                    vanishObject.setBuild(!vanishObject.isBuild());
                    player.getOpenInventory().close();
                    openGui(0, player);
                } else if (e.getSlot() == 10) {
                    vanishObject.setPvp(!vanishObject.isPvp());
                    player.getOpenInventory().close();
                    openGui(0, player);
                } else if (e.getSlot() == 11) {
                    vanishObject.setPve(!vanishObject.isPve());
                    player.getOpenInventory().close();
                    openGui(0, player);
                } else if (e.getSlot() == 12) {
                    vanishObject.setInteract(!vanishObject.isInteract());
                    player.getOpenInventory().close();
                    openGui(0, player);
                } else if (e.getSlot() == 13) {
                    vanishObject.setUseful(!vanishObject.isUseful());
                    player.getOpenInventory().close();
                    openGui(0, player);
                } else if (e.getSlot() == 14) {
                    vanishObject.setPickup(!vanishObject.isPickup());
                    player.getOpenInventory().close();
                    openGui(0, player);
                } else if (e.getSlot() == 15) {
                    vanishObject.setDrop(!vanishObject.isDrop());
                    player.getOpenInventory().close();
                    openGui(0, player);
                } else if (e.getSlot() == 16) {
                    vanishObject.setShoot(!vanishObject.isShoot());
                    player.getOpenInventory().close();
                    openGui(0, player);
                } else if (e.getSlot() == 17) {
                    vanishObject.setCamera(!vanishObject.isCamera());
                    player.getOpenInventory().close();
                    openGui(0, player);
                }
            });

            inventoryHelper.setItem(0,buildGlass);
            inventoryHelper.setItem(1,pvpGlass);
            inventoryHelper.setItem(2,pveGlass);
            inventoryHelper.setItem(3,interactGlass);
            inventoryHelper.setItem(4,usefulGlass);
            inventoryHelper.setItem(5,pickupGlass);
            inventoryHelper.setItem(6,dropGlass);
            inventoryHelper.setItem(7,shootGlass);
            inventoryHelper.setItem(8,cameraGlass);
            inventoryHelper.setItem(9, build);
            inventoryHelper.setItem(10, pvp);
            inventoryHelper.setItem(11, pve);
            inventoryHelper.setItem(12, interact);
            inventoryHelper.setItem(13, useful);
            inventoryHelper.setItem(14, pickup);
            inventoryHelper.setItem(15, drop);
            inventoryHelper.setItem(16, shoot);
            inventoryHelper.setItem(18,buildGlass);
            inventoryHelper.setItem(19,pvpGlass);
            inventoryHelper.setItem(20,pveGlass);
            inventoryHelper.setItem(21,interactGlass);
            inventoryHelper.setItem(22,usefulGlass);
            inventoryHelper.setItem(23,pickupGlass);
            inventoryHelper.setItem(24,dropGlass);
            inventoryHelper.setItem(25,shootGlass);
            inventoryHelper.setItem(26,cameraGlass);

            inventoryHelper.open(player);
        }
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if(args.length == 2) return Collections.singletonList("[players]");
        else if(args.length == 1) return Api.startsWith(Arrays.asList("on", "off", "check", "panel"), args[0]);
        return null;
    }

    public static List<VanishObject> getVanishObjects(){
        return VanishObject.getVanishObjects();
    }

    public static class VanishRunnable extends BukkitRunnable {
        public VanishRunnable(){
            runTaskTimer(Main.getPlugin(), 0, 19);
        }

        @Override
        public void run( ) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                VanishObject vanishObject = VanishObject.get(player);
                if(vanishObject.isEnable()){
                    Api.sendActionBar(player, "&8>> &aVanish jest uruchomiony &8<<");

                }
            });
        }
    }

    private static class VanishObject {

        public static VanishObject get(String name){
            for (VanishObject vanishObject : vanishObjects) {
                if(vanishObject.getName().equals(name)){
                    return vanishObject;
                }
            }
            return new VanishObject(name);
        }
        public static VanishObject get(Player player){
            return get(player.getName());
        }

        private static final List<VanishObject> vanishObjects = new ArrayList<>();

        public static List<VanishObject> getVanishObjects( ) {
            return vanishObjects;
        }

        private final String name;
        private Player cameringPlayer;
        private boolean enable, build, pvp, pve, interact, useful, drop, pickup, shoot, camera = false;

        protected Player getPlayer(){
            return Bukkit.getPlayer(this.name);
        }

        protected boolean isOnline(){
            return getPlayer() != null && getPlayer().isOnline();
        }

        protected void hidePlayer() {
            if (isOnline()){
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if(!player.hasPermission("vanish.admin.tools")){
                        player.hidePlayer(getPlayer());
                    }
                });
            }
        }

        protected void showPlayer(){
            if (isOnline()){
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if(!player.hasPermission("vanish.admin.tools")){
                        player.showPlayer(getPlayer());
                    }
                });
            }
        }

        public boolean isEnable( ) {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
            if(enable){
                hidePlayer();
            } else showPlayer();
        }

        public VanishObject(String name){
            this.name = name;
            if(!vanishObjects.contains(this)) vanishObjects.add(this);
        }

        public String getName( ) {
            return name;
        }

        public boolean isBuild( ) {
            return build;
        }

        public void setBuild(boolean build) {
            this.build = build;
        }

        public boolean isPvp( ) {
            return pvp;
        }

        public void setPvp(boolean pvp) {
            this.pvp = pvp;
        }

        public boolean isPve( ) {
            return pve;
        }

        public void setPve(boolean pve) {
            this.pve = pve;
        }

        public boolean isInteract( ) {
            return interact;
        }

        public void setInteract(boolean interact) {
            this.interact = interact;
        }

        public boolean isUseful( ) {
            return useful;
        }

        public void setUseful(boolean useful) {
            this.useful = useful;
        }

        public boolean isPickup( ) {
            return pickup;
        }

        public void setPickup(boolean pickup) {
            this.pickup = pickup;
        }

        public boolean isDrop( ) {
            return drop;
        }

        public void setDrop(boolean drop) {
            this.drop = drop;
        }

        public boolean isShoot( ) {
            return shoot;
        }

        public void setShoot(boolean shoot) {
            this.shoot = shoot;
        }

        public boolean isCamera( ) {
            return camera;
        }

        public void setCamera(boolean camera) {
            this.camera = camera;
        }

        public Player getCameringPlayer() {
            return cameringPlayer;
        }

        public void setCameringPlayer(Player cameringPlayer) {
            this.cameringPlayer = cameringPlayer;
        }
    }

    public static class VanishEvent implements Listener {

        @EventHandler
        public void onDrop(PlayerDropItemEvent e) {
            VanishObject vanishObject = VanishObject.get(e.getPlayer());
            if (vanishObject.isEnable() && !vanishObject.isDrop()) e.setCancelled(true);
        }

        @EventHandler
        public void onPickup(PlayerPickupItemEvent e) {
            VanishObject vanishObject = VanishObject.get(e.getPlayer());
            if (vanishObject.isEnable() && !vanishObject.isPickup()) e.setCancelled(true);
        }

        @EventHandler
        public void onBreak(BlockBreakEvent e) {
            VanishObject vanishObject = VanishObject.get(e.getPlayer());
            if (vanishObject.isEnable() && !vanishObject.isBuild()) e.setCancelled(true);
        }

        @EventHandler
        public void onPlace(BlockPlaceEvent e) {
            VanishObject vanishObject = VanishObject.get(e.getPlayer());
            if (vanishObject.isEnable() && !vanishObject.isBuild()) e.setCancelled(true);
        }

        @EventHandler
        public void onBucketFill(PlayerBucketFillEvent e) {
            VanishObject vanishObject = VanishObject.get(e.getPlayer());
            if (vanishObject.isEnable() && !vanishObject.isUseful())
                e.setCancelled(true);

        }

        @EventHandler
        public void onBucketEmpty(PlayerBucketEmptyEvent e) {
            VanishObject vanishObject = VanishObject.get(e.getPlayer());
            if (vanishObject.isEnable() && !vanishObject.isUseful()) e.setCancelled(true);
        }

        @EventHandler
        public void onDamage(EntityDamageEvent e) {
            if (e.getEntity() instanceof Player) {
                VanishObject object = VanishObject.get((Player) e.getEntity());
                if (object.isEnable()) e.setCancelled(true);
            }
        }

        @EventHandler
        public void onEntityDamage(EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player) {
                VanishObject object = VanishObject.get((Player) e.getDamager());
                if (object.isEnable()) {
                    if (e.getEntity() instanceof Player && !object.isPvp()) {
                        e.setCancelled(true);
                    } else if (!object.isPve()) {
                        e.setCancelled(true);
                    }
                }
            }
        }

        @EventHandler
        public void onJoin(PlayerJoinEvent e) {
            for (VanishObject vanishObject : VanishObject.getVanishObjects()) {
                if (vanishObject.isEnable()) {
                    vanishObject.hidePlayer();
                }
            }
        }

        @EventHandler
        public void onFoodLevelChange(FoodLevelChangeEvent e) {
            VanishObject vanishObject = VanishObject.get((Player) e.getEntity());
            if (vanishObject.isEnable()) e.setCancelled(true);
        }

        @EventHandler
        public void onShoot(ProjectileLaunchEvent e) {
            if (e.getEntity().getShooter() != null && e.getEntity().getShooter() instanceof Player) {
                Player player = (Player) e.getEntity().getShooter();
                VanishObject object = VanishObject.get(player);
                if (object.isEnable() && !object.isShoot()) e.setCancelled(true);
            }
        }

        private static final List<Material> interactionsMaterials;

        static {
            interactionsMaterials = new ArrayList<>(Arrays.asList(Material.CHEST, Material.ENDER_CHEST, Material.FURNACE, Material.LEGACY_BURNING_FURNACE, Material.TRAPPED_CHEST, Material.TRIPWIRE_HOOK, Material.IRON_TRAPDOOR));
            for (Material value : Material.values()) {
                if (value.name().equalsIgnoreCase("door")) {
                    interactionsMaterials.add(value);
                }
            }
        }

        @EventHandler
        public void onInteract(PlayerInteractEvent event) {
            if (event.getClickedBlock() != null && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (interactionsMaterials.contains(event.getClickedBlock().getType())) {
                    VanishObject object = VanishObject.get(event.getPlayer());
                    if (object.isEnable() && !object.isInteract()) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}