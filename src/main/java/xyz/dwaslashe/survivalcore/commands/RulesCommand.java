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
                                .add(Api.fixColor("&2* &a9. &8Wszystkie wspomagacze są zabronione! Będzie to surowo karane przez administracje serwera."))
                                .newLine().newLine()
                                .add(Api.fixColor("&2* &a10. &8Zakaz używania wspomagaczy np. Minimapka podczas eventów lub czegokolwiek co ułatwiło by rozgrywkę podczas eventu."))
                                .build(),
                        new BookUtil.PageBuilder()
                                .add(Api.fixColor("&2* &a11. &8Proszę o nie spamienie administracji we wiadomości lub też na dc... Administracja ma też inne sprawy do załatwienia lepiej jest napisać cała wiadomość jednym razem niż zaczynać konwersacje \"hej, mam sprawę\" bo administracja nie ma czasu odpowiadać 'hej, na czym ta sprawa polega' ułatwiło by nam to odpisywanie na ważne sprawy by pomóc też większej ilości niż wchodzić w zbędna konwersacje."))
                                .build(),
                        new BookUtil.PageBuilder()
                                .add(Api.fixColor("&8sprawa polega' ułatwiło by nam to odpisywanie na ważne sprawy by pomóc też większej ilości niż wchodzić w zbędna konwersacje."))
                                .newLine()
                                .add(Api.fixColor("&2* &a12. &8Administracja to nie taxi"))
                                .build(),
                        new BookUtil.PageBuilder()
                                .add(Api.fixColor("&2* &a13. &8Zakaz prowokowania administracji oszukiwania oraz przeszkadzania administracji podczas prowadzonych czynności lub interakcji z graczem."))
                                .newLine().newLine()
                                .add(Api.fixColor("&2* &a14. &8Maszyny powodujące lagi na całym serwisie są surowo zabronione i karane banem nie potrzeba nam osób które chcą niszczyć rozgrywkę.."))
                                .build(),
                        new BookUtil.PageBuilder()
                                .add(Api.fixColor("&2* &a15. &8Zakazane jest proszenie o rangi lub próba ich wyłudzania... Przyjdzie czas na eventy w których jest możliwość wygrania rangi czy też kluczy."))
                                .newLine().newLine()
                                .add(Api.fixColor("&2* &a16. &8Oszukiwanie na /ah grozi banem czasowym nadanym przez administratora serwera za wprowadzanie ludzi w błąd i czerpania korzyści z tego."))
                                .build(),
                        new BookUtil.PageBuilder()
                                .add(Api.fixColor("&2* &a17. &8Zakazane jest griefowania terenu wokół działek innych graczy na serwerze. Jeżeli ktoś griefuje ci teren wokół działki zgłoś to administracji wraz z dowodami."))
                                .newLine().newLine()
                                .add(Api.fixColor("&2* &a18. &8Wykorzystanie błędów jest surowo zabronione oraz karane banem jeżeli widzisz błąd zgłoś go do wyższej administracji."))
                                .build(),
                        new BookUtil.PageBuilder()
                                .add(Api.fixColor("&2* &a19. &8Każdy zakup w sklepie serwera jest dobrowolny nikt nie może narzucić Ci zakupu jakiegoś przedmiotu/rangi "))
                                .newLine().newLine()
                                .add(Api.fixColor("&2* &a20. &8Zakaz jest sprzedawania przedmiotów które posiadasz na koncie za 'wszystkie możliwe środki płatności' nie liczy się waluta serwerowa!"))
                                .build()
                )
                .build();

        BookUtil.openPlayer(p, book);
    }

}