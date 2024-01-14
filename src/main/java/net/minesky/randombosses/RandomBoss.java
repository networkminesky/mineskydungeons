package net.minesky.randombosses;

import net.minesky.MineSkyDungeons;
import org.bukkit.scheduler.BukkitRunnable;

public class RandomBoss {

    public static void spawn() {



    }

    public static void task() {
        new BukkitRunnable() {
            @Override
            public void run() {



                new BukkitRunnable() {
                    @Override
                    public void run() {

                    }
                }.runTaskLater(MineSkyDungeons.getInstance(), )

            }
        }.runTaskTimer(MineSkyDungeons.getInstance(), 40, 12000);


    }

}
