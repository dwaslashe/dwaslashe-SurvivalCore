package xyz.dwaslashe.survivalcore.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Getter @Setter
public class ChatBuffer implements Listener {

    private int handicapChars = 10;

    private int readSpeed = 500;

    private int maxBubbleHeight = 1;

    private int maxBubbleWidth = 50;

    private int bubblesInterval = 2;

    private Map<String, Queue<String>> chatQueue = new HashMap<>();

    public void receiveChat(Player player, String msg) {
        String prefix = Api.fixColor("&#4cf739&lGracz napisał:");
        if (msg.length() <= this.maxBubbleWidth) {
            this.queueChat(player, Api.fixColor(prefix + "\n" + msg + "\n" + "\n" + "\n&r"));
        } else {
            msg = msg + " ";
            String chat = "";
            int lineCount = 0;

            while(true) {
                while(msg.length() > 0) {
                    int delimPos = msg.lastIndexOf(32, this.maxBubbleWidth);
                    if (delimPos < 0) {
                        delimPos = msg.indexOf(32, this.maxBubbleWidth);
                    }

                    if (delimPos < 0) {
                        delimPos = msg.length();
                    }

                    chat = chat + msg.substring(0, delimPos);
                    msg = msg.substring(delimPos + 1);
                    ++lineCount;
                    if (lineCount % this.maxBubbleHeight != 0 && msg.length() != 0) {
                        chat = chat + "\n";
                    } else {
                        this.queueChat(player, chat + (msg.length() == 0 ? "\n" : "...\n"));
                        chat = "";
                    }
                }

                return;
            }
        }
    }

    public void queueChat(Player player, String chat) {
        String playerId = "" + player.getUniqueId();
        if (!this.chatQueue.containsKey(playerId)) {
            this.chatQueue.put(playerId, new LinkedList<>());
            scheduleMessageUpdate(player, playerId, 0);
        }
        ((Queue<String>)this.chatQueue.get(playerId)).add(chat);
    }

    private void scheduleMessageUpdate(final Player player, final String playerId, int timer) {
        (new BukkitRunnable() {
            public void run() {
                if (((Queue)ChatBuffer.this.chatQueue.get(playerId)).size() >= 1 && player.isOnline()) {
                    String chat = ((Queue<String>)ChatBuffer.this.chatQueue.get(playerId)).poll();
                    int bubbleDuration = receiveMessage(player, playerId, chat);
                    ChatBuffer.this.scheduleMessageUpdate(player, playerId, bubbleDuration + ChatBuffer.this.bubblesInterval);
                } else {
                    ChatBuffer.this.chatQueue.remove(playerId);
                }
            }
        }).runTaskLater(Main.getPlugin(), timer);
    }

    int receiveMessage(Player player, String playerId, String chat) {
        String[] chatLines = chat.split("\n");
        new ArrayList();
        int duration = (chat.length() + this.handicapChars * chatLines.length) * 1200 / this.readSpeed;
        Location spawnPoint = player.getLocation();
        spawnPoint.setY(-1.0D);
        Entity vehicle = player;

        for(int i = chatLines.length - 1; i >= 0; --i) {
            vehicle = this.spawnNameTag((Entity)vehicle, chatLines[i], spawnPoint, duration);
        }

        return duration;
    }

    private AreaEffectCloud spawnNameTag(Entity vehicle, String text, Location spawnPoint, int duration) {
        AreaEffectCloud nameTag = (AreaEffectCloud)spawnPoint.getWorld().spawnEntity(spawnPoint, EntityType.AREA_EFFECT_CLOUD);
        nameTag.setParticle(Particle.TOWN_AURA);
        nameTag.setRadius(0.0F);
        vehicle.addPassenger((Entity)nameTag);
        nameTag.setCustomName(text);
        nameTag.setCustomNameVisible(true);
        nameTag.setWaitTime(0);
        nameTag.setDuration(duration);
        return nameTag;
    }

    //@EventHandler(priority = EventPriority.HIGHEST)
    //public void onPlayerChat(AsyncPlayerChatEvent e) {
    //    receiveChat(e.getPlayer(), e.getMessage());
    //}
}
