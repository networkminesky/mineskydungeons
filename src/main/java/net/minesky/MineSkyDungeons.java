package net.minesky;

import net.minesky.instances.awaiting.AwaitingDungeon;
import net.minesky.instances.awaiting.AwaitingDungeonHandler;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class MineSkyDungeons extends JavaPlugin {

    public static MineSkyDungeons instance;
    public static Logger l;

    public static YamlConfiguration dungeonsList;
    public static YamlConfiguration randomBosses;

    public static MineSkyDungeons getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        l.info("---------------------------------------");
        l.info("   _____  .__                ___________           ________                                                   \n" +
                "  /     \\ |__| ____   ____  /   _____/  | _____.__.\\______ \\  __ __  ____    ____   ____  ____   ____   ______\n" +
                " /  \\ /  \\|  |/    \\_/ __ \\ \\_____  \\|  |/ <   |  | |    |  \\|  |  \\/    \\  / ___\\_/ __ \\/  _ \\ /    \\ /  ___/\n" +
                "/    Y    \\  |   |  \\  ___/ /        \\    < \\___  | |    `   \\  |  /   |  \\/ /_/  >  ___(  <_> )   |  \\\\___ \\ \n" +
                "\\____|__  /__|___|  /\\___  >_______  /__|_ \\/ ____|/_______  /____/|___|  /\\___  / \\___  >____/|___|  /____  >\n" +
                "        \\/        \\/     \\/        \\/     \\/\\/             \\/           \\//_____/      \\/           \\/     \\/ ");
        l.info("---------------------------------------");
        l.info("Plugin criado por Drawn e feito exclusivamente para o MineSky Network.");

        for(String s : dungeonsList.getKeys(false)) {
            AwaitingDungeon aD = new AwaitingDungeon(s, dungeonsList.getConfigurationSection(s));
            AwaitingDungeonHandler.awaitingDungeonList.add(aD);
        }

        File randomBossesFile = new File(this.getDataFolder(), "randombosses.yml");
        randomBosses = YamlConfiguration.loadConfiguration(randomBossesFile);
        if (!randomBossesFile.exists()) {
            try {
                l.info("Arquivo randombosses.yml criado");
                randomBosses.save(randomBossesFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for(Location l : AwaitingDungeonHandler.cachedPortalLocations) {
                    AwaitingDungeon dungeon = null;
                    for(Player pe : l.getNearbyEntitiesByType(Player.class, 3, 3)) {
                        if(dungeon == null)
                            dungeon = AwaitingDungeonHandler.getDungeonByLocation(l);

                        if(dungeon != null)
                            dungeon.getSession().addWaitingPlayer(pe);
                    }
                }
            }
        }.runTaskTimer(this, 20, 10);

    }

}