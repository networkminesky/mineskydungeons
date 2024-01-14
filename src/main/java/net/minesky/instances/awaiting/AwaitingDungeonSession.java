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
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class AwaitingDungeonSession {

    private final AwaitingDungeon dungeon;

    private boolean isAwaitingPlayers = false;
    private List<Player> playersWaiting;

    public AwaitingDungeonSession(AwaitingDungeon dungeon) {
        this.dungeon = dungeon;
        this.playersWaiting = new ArrayList<>();
    }

    public List<Player> getPlayersThatAreWaiting() {
        return playersWaiting;
    }

    public void addWaitingPlayer(Player p) {
        if(getPlayersThatAreWaiting().contains(p))
            return;

        MineSkyPlayer.from(p).titleFade(30, 15, 15);

        new BukkitRunnable() {
            @Override
            public void run() {
                Location cameraLocation = dungeon.getCameraLocation();

                // PARTE NMS
                ServerLevel level = ((CraftWorld)cameraLocation.getWorld()).getHandle();
                ServerPlayer sP = ((CraftPlayer)p).getHandle();

                ArmorStand fakeEntity = new ArmorStand(level, cameraLocation.getX(), cameraLocation.getY(), cameraLocation.getZ());
                fakeEntity.setInvisible(true);
                fakeEntity.setNoGravity(true);
                //fakeEntity.setMarker(true);

                ClientboundAddEntityPacket addEntityPacket = new ClientboundAddEntityPacket(fakeEntity);
                ClientboundSetEntityDataPacket entityDataPacket = new ClientboundSetEntityDataPacket(fakeEntity.getId(),
                        fakeEntity.getEntityData().getNonDefaultValues());
                ClientboundSetCameraPacket camera = new ClientboundSetCameraPacket(fakeEntity);

                sP.connection.send(addEntityPacket);
                sP.connection.send(entityDataPacket);
                sP.connection.send(camera);

            }
        }.runTaskLater(MineSkyDungeons.getInstance(), 35);

        getPlayersThatAreWaiting().add(p);
    }

    public void removeWaitingPlayer(Player p) {
        if(!getPlayersThatAreWaiting().contains(p))
            return;

        MineSkyPlayer.from(p).titleFade(15, 15, 30);

        new BukkitRunnable() {
            @Override
            public void run() {
                ServerPlayer sP = ((CraftPlayer)p).getHandle();
                ClientboundSetCameraPacket camera = new ClientboundSetCameraPacket(sP);
                sP.connection.send(camera);
            }
        }.runTaskLater(MineSkyDungeons.getInstance(), 35);

        getPlayersThatAreWaiting().remove(p);
    }
}
