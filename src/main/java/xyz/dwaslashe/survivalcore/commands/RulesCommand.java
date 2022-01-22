package xyz.dwaslashe.survivalcore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.survivalcore.commands.managers.Command;
import xyz.dwaslashe.survivalcore.utils.Api;
import xyz.upperlevel.spigot.book.BookUtil;
import java.util.List;

public class RulesCommand extends Command {
    public RulesCommand() {
        super("rules", "/regulamin", "", "regulamin");
    }

    @Override
    public List<String> tabCompleteExecute(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void commandExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        ItemStack book = BookUtil.writtenBook()
                .author("WywrotkaMC")
                .title("Regulamin")
                .pages(
                        new BookUtil.PageBuilder()
                                .add(Api.fixColor("     &a&lREGULAMIN"))
                                .newLine().newLine()
                                .add(Api.fixColor("&2* &a1. &8Zakazany jest spam, flood oraz pisanie wielkimi literami (pisanie tzw. Caps Lockiem jest to karane mutem czasowym)"))
                                .build(),
                        new BookUtil.PageBuilder()
                                .add(Api.fixColor("&2* &a2. &8Obrażanie i wyzywanie graczy, ekipy oraz administracji jest zakazane."))
                                .newLine().newLine()
                                .add(Api.fixColor("&2* &a3. &8Wymiany, które nie są za pomocą komendy /trade, nie odpowiada za to serwer/administracja"))
                                .build(),
                        new BookUtil.PageBuilder()
                                .add(Api.fixColor("&2* &a4. &8Kanał YouTube można reklamować tylko podczas posiadania rangi YouTuber (nie ma wyjątków)"))
                                .newLine().newLine()
                                .add(Api.fixColor("&2* &a5. &8Administracja/serwer nie odpowiada za stracone/skradzione itemki."))
                                .build(),
                        new BookUtil.PageBuilder()
                                .add(Api.fixColor("&2* &a6. &8Zakaz podszywania się pod Administrację/gracza na naszej sieci."))
                                .newLine().newLine()
                                .add(Api.fixColor("&2* &a7. &8Tworzenie multikont w celu ominięcia kary czy też zdobycia korzyści na serwerze jest surowo karane."))
                                .build(),
                        new BookUtil.PageBuilder()
                                .add(Api.fixColor("&2* &a8. &8Administracja ma zawsze rację jeżeli myślisz że administrator źle przystąpił do sprawy proszę zgłosić to do administracji na discordzie w celu naprawienia błędu innego administratora.\n"))
                                .build(),
                        new BookUtil.PageBuilder()
                                .add(Api.fixColor("&2* &a9. &8"))
                                .newLine().newLine()
                                .add(Api.fixColor("&2* &a10. &8"))
                                .build(),
                        new BookUtil.PageBuilder()
                                .add(Api.fixColor("&2* &a11. &8"))
                                .newLine().newLine()
                                .add(Api.fixColor("&2* &a12. &8"))
                                .build(),
                        new BookUtil.PageBuilder()
                                .add(Api.fixColor("&2* &a13. &8"))
                                .newLine().newLine()
                                .add(Api.fixColor("&2* &a14. &8"))
                                .build()
                )
                .build();

        BookUtil.openPlayer(p, book);
    }

}
