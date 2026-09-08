# 🚀 Minecraft Core Plugin (ARCHIWUM)

Zaawansowany, rozbudowany plugin typu **Core / Essential** do serwera Minecraft (Spigot / Paper). Projekt integruje w sobie mechaniki rozgrywki, zaawansowane zarządzanie graczami, systemy ekonomii, customowe przedmioty oraz wsparcie dla zewnętrznych usług (np. Discord).

---

## 🌟 Główne Funkcje & Mechaniki

Na podstawie struktury projektu, plugin odpowiada za kompleksową obsługę serwera:

### ⚔️ Mechaniki Gry i Przedmioty
* **Customowe Przedmioty i Craftingi:** Obsługa własnych przedmiotów, receptur craftingu (`CustomItem`, `ItemCraftListener`, `CustomItemsListener`).
* **System Otchłani / Śmietnika (Abyss):** Automatyczne czyszczenie przedmiotów z ziemi i dedykowane menu otchłani (`AbyssTask`, `AbyssCommand`, `Abyss.java`).
* **Skrzynie / Case System:** System skrzyń niespodzianek wraz z przedmiotami dropu (`Case`, `CaseItem`).
* **Walka i Ochrona:** Wbudowany anty-logout / system walki oraz ochrona graczy/administracji (`PlayerCombatListener`, `LogoutManager`, `AdminProtectionCommand`).
* **Levelowanie Smoka:** Dedykowana mechanika poziomów smoka Ender (`DragonLevel`, `DragonLevelListener`, `DragonLevelCache`).
* **Vouchery i Różdżki:** System voucherów oraz różdżek do sprzedaży działek (`VoucherListener`, `PluginVouchers`, `PlotSellWandListener`).

### 👥 Systemy Społecznościowe i Gracza
* **System Małżeństw (Marry):** Możliwość wchodzenia w związki przez graczy (`Marry.java`, `MarryCache`).
* **Warpy Graczy (Player Warps):** Prywatne warpy tworzone i zarządzane przez graczy (`PlayerWarp`, `PlayerWarpCache`).
* **System Sprawdzania (Check/Admits):** Narzędzie dla administracji do sprawdzania graczy podejrzanych o chety (`CheckCommand`, `AdmitsCommand`).
* **Zarządzanie Czasem i Pogodą:** Prywatna zmiana czasu/pogody dla gracza oraz ustawianie jasności (`PlayerTime`, `GammaCommand`, `DayCommand`).

### ⚙️ Administracja i Logika
* **Zarządzanie Czatami:** Moduł kontroli czatu, wiadomości automatyczne oraz integracja z BossBarem (`ChatCommand`, `AutoMessageTask`, `AutoBossBarTask`).
* **Integracja z Discordem:** Dedykowany helper i komenda do powiadomień/synchronizacji z Discordem (`DiscordCommand`, `DiscordHelper`).
* **Wsparcie PlaceholderAPI:** Wbudowane własne zmienne i hooki do PlaceholderAPI (`PlaceholderHooks`).
* **Wydajny Caching:** System pamięci podręcznej (Cache) optymalizujący zapytania bazy danych (`UserCache`, `ItemCache`, `DragonLevelCache` i inne).

---

## 📂 Struktura Projektu (Pakiety)

```text
src/main/java/...
├── cache/         # Pamięć podręczna (User, Warps, DragonLevel, Marry, itp.)
├── commands/      # Komendy graczy i administracji (Abyss, Check, Fly, GodMod, itp.)
├── configs/       # Konfiguracje pluginu, rang, voucherów i serializatory
├── database/      # Obsługa połączenia i operacji na bazie danych
├── enums/         # Definicje stałych (kolory, typy rąk, znaki obrazów)
├── function/      # Funkcje pomocnicze
├── helpers/       # Helpery (Discord, Ekwipunek, Reflection, String, BiReplace)
├── listeners/     # Listenery zdarzeń Bukkit/Spigot (Chat, Combat, Join, BlockBreak)
├── managers/      # Managerowie (Cooldown, Logout, Teleport)
├── model/         # Modele i interfejsy (CustomItem)
├── objects/       # Obiekty domenowe (User, Case, Warp, Abyss, Protection)
├── parsers/       # Parser lokalizacji (LocationParser)
├── placeholder/   # Hooki do PlaceholderAPI
├── tasks/         # Zadania asynchroniczne i pętle (AbyssTask, AutoMessage, BossBar)
└── utils/         # Narzędzia API (BossBar, Chat, Picture, Region, License)
