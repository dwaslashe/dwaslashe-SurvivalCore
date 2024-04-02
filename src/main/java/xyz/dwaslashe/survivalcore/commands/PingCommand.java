package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PingCommand extends Command {
    List<PingObject> pingObjects = new ArrayList();
    public PingCommand() {
        super("ping", "/ping <gracz>", "");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        if (args.length == 1) return Collections.singletonList("[players]");
        return null;
    }

    public List<PingObject> getPingObjects() {
        return pingObjects;
    }

    public void setPingObjects(List<PingObject> pingObjects) {
        this.pingObjects = pingObjects;
    }
    public boolean exists(Player user){
        for(PingObject object : pingObjects){
            if(object.getUser().getName().equalsIgnoreCase(user.getName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void commandExecute(CommandSender s, String[] args) {
        if(args.length == 0){
            if(s instanceof Player){
                if(!exists(((Player) s).getPlayer())) {
                    PingObject newpingobject = new PingObject();
                    newpingobject.setTimer(0);
                    newpingobject.setUser(((Player) s).getPlayer());
                    pingObjects.add(newpingobject);
                    (new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (newpingobject.getTimer() <= 20){
                                ((Player) s).getPlayer().sendTitle(Api.fixColor("&#FFC42E" + Api.getPing((Player) s) + "ms. &8(&#FFC42E" + newpingobject.getTimer() + "&8)") , Api.fixColor("&8>> &#4cf739Twój aktualny ping &8<<"), 0, 20, 5);
                                newpingobject.setTimer(newpingobject.getTimer() + 1);
                            } else {
                                this.cancel();
                                pingObjects.remove(newpingobject);
                            }
                        }
                    }).runTaskTimer(Main.getPlugin(), 0, 5);
                } else {
                    Api.sendMessage(s, " &8>> &#fc2419Już sprawdzasz swój ping!");
                }
            } else {
                wrongUsage();
            }
        } else if(args.length == 1){
            if (!((Player) s).getPlayer().hasPermission("core.command.admin")) {
                ((Player) s).getPlayer().sendTitle(Api.fixColor(Main.pluginConfig.getMessages().getIp()), Api.fixColor(" &8>> &#fc2419Nie posiadasz uprawnień &8(&#fcb419core.command.admin&8) &8<<"));
                return;
            }
            if(Bukkit.getPlayer(args[0]) != null){
                Player p = Bukkit.getPlayer(args[0]);
                Api.sendMessage(s, " &8>> &#4cf739Ping gracza &#fcb419" + p.getName() + " &#4cf739wynosi &#fcb419" + Api.getPing(p) + "ms.");
            } else {
                offlinePlayer();
            }
        } else {
            wrongUsage();
        }
        return;
    }
}

class PingObject {
    private Player user;
    private int timer;

    public void setUser(Player user) {
        this.user = user;
    }

    public Player getUser() {
        return user;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}

