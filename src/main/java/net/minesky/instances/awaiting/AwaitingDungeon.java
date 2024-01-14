package net.minesky.instances.awaiting;

import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minesky.MineSkyDungeons;
import net.minesky.api.MineSkyPlayer;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Instant;
import java.util.List;

public class AwaitingDungeon {

    private final long dungeonAvailability;
    private final Location portalLocation;
    private final Location cameraLocation;
    private final String id;
    private final AwaitingDungeonSession session;

    public AwaitingDungeon(String id, ConfigurationSection sec) {
        this.id = id;
        this.dungeonAvailability = sec.getLong("next-dungeon");
        this.portalLocation = sec.getLocation("location.dungeon");
        this.cameraLocation = sec.getLocation("location.starting");

        session = new AwaitingDungeonSession(this);
    }

    public AwaitingDungeonSession getSession() {
        return this.session;
    }

    public String getId() {
        return id;
    }

    public boolean isDungeonAvailableNow() {
        return !Instant.ofEpochSecond(dungeonAvailability).isAfter(Instant.now());
    }

    public long getDungeonAvailability() {
        return dungeonAvailability;
    }

    public Location getCameraLocation() {
        return cameraLocation;
    }

    public Location getPortalLocation() {
        return portalLocation;
    }
}
