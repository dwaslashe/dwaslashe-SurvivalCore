package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dwaslashe.survivalcore.Main;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TexturpackCommand extends Command {
    private static final URL RESOURCE_PACK_URL = createResourcePackUrl();
    public TexturpackCommand() {
        super("texturpack", "/texturpack", "", "txt");
        setPermission("core.command.texturpack");
        setOnlyPlayer(true);
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player player = (Player)sender;
        Api.sendMessage(sender, Main.pluginConfig.getMessages().getPrefixSuccess() + "&#4cf739Pomyślnie włączyłeś TexturPack serwerowy");
        player.setResourcePack(RESOURCE_PACK_URL.toString());
    }

    private static URL createResourcePackUrl() {
        try {
            return new URL("https://www.dropbox.com/s/oum17na2k1dijez/%C2%A7aWywrotka%C2%A72MC.rar?dl=1");
        } catch (MalformedURLException var1) {
            return null;
        }
    }

}
