package xyz.dwaslashe.survivalcore.listeners;

import dev.norska.dsw.api.DeluxeSellwandPreSellEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.minecodes.plots.api.plot.PlotApi;
import pl.minecodes.plots.api.plot.PlotServiceApi;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.utils.Api;

public class PlotSellWandListener implements Listener {

    private PlotServiceApi apiPlotService;

    public PlotSellWandListener(PlotServiceApi apiPlotService) {
        this.apiPlotService = apiPlotService;
    }

    @EventHandler
    public void onSellPlot(DeluxeSellwandPreSellEvent event) {
        Player player = event.getPlayer();
        PlotApi plot = this.apiPlotService.getPlot(player.getLocation());

        if (plot == null) return;
        if (!plot.hasAccess(player)) {
            event.setCancelled(true);
            Api.sendMessage(player, Main.pluginConfig.getMessages().getPrefix() + "&cNie możesz użyć różdzki na nie swojej działce!");
        }
    }
}
