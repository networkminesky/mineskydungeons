package net.minesky.instances.awaiting;

import net.minesky.instances.awaiting.AwaitingDungeon;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AwaitingDungeonHandler {

    public static List<AwaitingDungeon> awaitingDungeonList = new ArrayList<>();
    public static List<Location> cachedPortalLocations = new ArrayList<>();

    @Nullable
    public static AwaitingDungeon getAwaitingDungeonById(String id) {
        for(AwaitingDungeon d : awaitingDungeonList) {
            if(id.equals(d.getId()))
                return d;
        }
        return null;
    }

    public static AwaitingDungeon getDungeonByLocation(Location l) {
        for(AwaitingDungeon d : awaitingDungeonList) {
            if(l.equals(d.getCameraLocation()) || l.equals(d.getPortalLocation()))
                return d;
        }
        return null;
    }

    public static void enterDungeon(Player player, AwaitingDungeon dungeon) {



    }

}
