package xyz.dwaslashe.survivalcore.utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class ChatApi {

    public static String getPrefix(Player player) {
        String prefix = ChatApi.playerMeta(player).getPrefix();
        return prefix != null ? prefix : "";
    }
    public static String getSuffix(Player player) {
        String suffix = ChatApi.playerMeta(player).getSuffix();
        return suffix != null ? suffix : "";
    }

    private static CachedMetaData playerMeta(Player player) {
        return ChatApi.loadUser(player).getCachedData().getMetaData(ChatApi.getApi().getContextManager().getQueryOptions(player));
    }

    private static User loadUser(Player player) {
        if (!player.isOnline()) {
            throw new IllegalStateException("Grasz jest offfline!");
        } else {
            return ChatApi.getApi().getUserManager().getUser(player.getUniqueId());
        }
    }


    private static LuckPerms getApi() {
        final RegisteredServiceProvider<LuckPerms> provider = getServer().getServicesManager().getRegistration(LuckPerms.class);
        Validate.notNull(provider);
        return provider.getProvider();
    }
}

