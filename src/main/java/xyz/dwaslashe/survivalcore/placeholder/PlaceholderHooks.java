package xyz.dwaslashe.survivalcore.placeholder;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pl.minecodes.plots.api.plot.PlotApi;
import pl.minecodes.plots.api.plot.PlotServiceApi;
import xyz.dwaslashe.survivalcore.cache.MarryCache;
import xyz.dwaslashe.survivalcore.cache.MoneyTargetCache;
import xyz.dwaslashe.survivalcore.cache.UserCache;
import xyz.dwaslashe.survivalcore.listeners.PlayerInteractListener;
import xyz.dwaslashe.survivalcore.listeners.RegionListener;
import xyz.dwaslashe.survivalcore.objects.*;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.dwaslashe.survivalcore.utils.TimerApi;

import java.text.DecimalFormat;

public class PlaceholderHooks extends PlaceholderExpansion {
    private static final DecimalFormat format = new DecimalFormat("#,###.##");

    public String getAuthor() {
        return "dwaslashe";
    }

    public String getIdentifier() {
        return "survivalcore";
    }

    public String getVersion() {
        return "1.0";
    }

    public boolean persist() {
        return true;
    }
    
    public String onRequest(OfflinePlayer player, String params) {
        User user = UserCache.getInstance().compute(player.getUniqueId());
        MoneyTarget moneyTarget = MoneyTargetCache.getInstance().compute(1);
        Marry marry = MarryCache.getInstance().compute(player.getUniqueId());
        if (params.equals("marry_husband")) {
            if (marry.getRightuuid() == null) {
                return "<#fa3b1e>Brak";
            } else if (marry.getRightuuid().equals(player.getUniqueId())) {
                return "<#fa3b1e>Brak";
            } else {
                OfflinePlayer husband = Bukkit.getOfflinePlayer(marry.getRightuuid());
                return Api.fixColor(husband.getName());
            }
        }
        if (params.equals("marry_hearth_chat")) {
            if (marry.getRightuuid() == null) {
                return "";
            } else if (marry.getRightuuid().equals(player.getUniqueId())) {
                return "";
            } else return "<#ff0c0c>❤ &r";
        }
        if (params.equals("marry_hearth_another")) {
            if (marry.getRightuuid() == null) {
                return "";
            } else if (marry.getRightuuid().equals(player.getUniqueId())) {
                return "";
            } else return "&x&#fcb419&b&#fcb419&#fc2419&f&0❤ &r";
        }
        if (params.equals("rate")) {
            return PlayerInteractListener.colorAverage(user.getRates());
        }
        if (params.equals("afk")) {
            if (RegionListener.afk.contains(player.getUniqueId()) && !player.getPlayer().isInsideVehicle()) {
                return Api.fixColor(" &#F23D07ᴀꜰᴋ");
            } else return "";
        }
        if (params.equals("afkchat")) {
            if (RegionListener.afk.contains(player.getUniqueId()) && !player.getPlayer().isInsideVehicle()) {
                return "<hover:show_text:\" <#f3501f>Strefa <#F23D07>AFK\n\n <#E7E7E7>Gracz aktualnie przebywa w strefie afk.\n <#E7E7E7>Kliknij, aby przeteleportować się do\n <#E7E7E7>strefy afk! Każda minuta w\n <#E7E7E7>strefie to <#FFF88F>2 <#FFC42E>$<#E7E7E7> więcej!\n\"><click:suggest_command:/warp afk><#F23D07>ᴀꜰᴋ </click></hover>";
            } else return "";
        }
        if (params.equals("afk_timespend")) {
            if (user.getTimeAfk() == 0) {
                return "<#fa3b1e>Brak";
            } else return ("<#ffd56c>" + TimerApi.getDurationBreakdownShort(user.getTimeAfk()) + " <#ffc942>⌚");
        }
        if (params.equals("moneytarget_money")) {
            return format.format(moneyTarget.getMoney());
        }
        if (params.equals("moneytarget_limitmoney")) {
            return format.format(moneyTarget.getLimitMoney());
        }
        if (params.equals("location")) {
            String regionName = PlaceholderAPI.setPlaceholders(player, "%worldguard_region_name%");
            String regionFinalText = toUpperFirstCharacter(regionName).replace("_", " ");
            String plotName = PlaceholderAPI.setPlaceholders(player, "%mineplots_name%");
            String finalText;
            if (plotName.equals("Brak")) {
                if (regionFinalText.equals("-")) {
                    String worldName = PlaceholderAPI.setPlaceholders(player, "%multiverse_world_name%");
                    if (worldName.equals("world")) {
                        finalText = "Świat";
                    } else if (worldName.equals("world_nether")) {
                        finalText = "Piekło";
                    } else if (worldName.equals("world_the_end")) {
                        finalText = "Świat Kresu";
                    } else finalText = "";
                } else finalText = regionFinalText;
            } else finalText = plotName + PlaceholderAPI.setPlaceholders(player, " - <#f47e07>@%mineplots_owner%</#f47e07>");
            return finalText;
        }
        if (params.equals("moneytarget_title")) {
            return moneyTarget.getTitle();
        }
        if (params.equals("belowname")) {
            if (Protection.getProtectionMap().containsKey(player.getUniqueId())) {
                Protection protection = Protection.getProtectionMap().get(player.getUniqueId());
                return "&#037bfc&lOCHRONA: &#ffd56c" + TimerApi.secondsToString(protection.getProtection()) + " &fᎠ ";
            } else return "%health% #FF3131❤";
        }
        if (params.equals("sessiontime")) {
            PlayerTime playerTime = PlayerTime.getPlayer((Player) player);
            long time = System.currentTimeMillis();
            long playTime = time - playerTime.getTime();
            return TimerApi.getDurationBreakdownShort(playTime);
        }
        if (params.equals("team")) {

            if (PlaceholderAPI.setPlaceholders(player, "%mineteams_team_ranking%").contains("&#fc2419Brak!")) {
                return "Punkty: &#4eed6e%mineteams_profile_ranking%pkt &#fcee83#%ajlb_position_mineteams_profile_ranking_alltime%";
            } else {
                return "Drużyna: &#4eed6e%mineteams_team_tag%&#4eed6e%mineteams_team_ranking%pkt";
            }
        }
        return null;
    }

    private String toUpperFirstCharacter(String s){
        if(s.isEmpty()) return "-";
        char c = s.charAt(0);
        return s.replaceFirst(String.valueOf(c), String.valueOf(c).toUpperCase());
    }
}
