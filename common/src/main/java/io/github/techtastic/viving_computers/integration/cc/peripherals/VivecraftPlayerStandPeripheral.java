package io.github.techtastic.viving_computers.integration.cc.peripherals;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import io.github.techtastic.viving_computers.block.entity.VivecraftPlayerStandBE;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.vivecraft.common.utils.math.Quaternion;
import org.vivecraft.server.ServerVRPlayers;
import org.vivecraft.server.ServerVivePlayer;

import java.util.Map;

public class VivecraftPlayerStandPeripheral implements IPeripheral {
    public final VivecraftPlayerStandBE stand;

    public VivecraftPlayerStandPeripheral(VivecraftPlayerStandBE stand) {
        this.stand = stand;
    }

    @Override
    public @NotNull String getType() {
        return "stand";
    }

    @Override
    public boolean equals(IPeripheral iPeripheral) {
        return iPeripheral instanceof VivecraftPlayerStandPeripheral test &&
                test.stand.equals(this.stand);
    }

    @LuaFunction
    public String getBoundPlayer() throws LuaException {
        return getBoundVRPlayer().player.getUUID().toString();
    }

    @LuaFunction
    public Map<String, Double> getHeadPosition() throws LuaException {
        ServerVivePlayer player = getBoundVRPlayer();
        if (player.vrPlayerState == null)
            throw new LuaException("Missing VRPlayerState");
        Vec3 pos = player.vrPlayerState.hmd().position();
        return Map.of(
                "x", pos.x,
                "y", pos.y,
                "z", pos.z
        );
    }

    @LuaFunction
    public Map<String, Double> getHeadOrientation() throws LuaException {
        ServerVivePlayer player = getBoundVRPlayer();
        if (player.vrPlayerState == null)
            throw new LuaException("Missing VRPlayerState");
        Quaternion rot = player.vrPlayerState.hmd().orientation();
        return Map.of(
                "x", (double) rot.x,
                "y", (double) rot.y,
                "z", (double) rot.z,
                "w", (double) rot.w
        );
    }

    @LuaFunction
    public Map<String, Double> getFirstControllerPosition() throws LuaException {
        ServerVivePlayer player = getBoundVRPlayer();
        if (player.vrPlayerState == null)
            throw new LuaException("Missing VRPlayerState");
        Vec3 pos = player.vrPlayerState.controller0().position();
        return Map.of(
                "x", pos.x,
                "y", pos.y,
                "z", pos.z
        );
    }

    @LuaFunction
    public Map<String, Double> getFirstControllerOrientation() throws LuaException {
        ServerVivePlayer player = getBoundVRPlayer();
        if (player.vrPlayerState == null)
            throw new LuaException("Missing VRPlayerState");
        Quaternion rot = player.vrPlayerState.controller0().orientation();
        return Map.of(
                "x", (double) rot.x,
                "y", (double) rot.y,
                "z", (double) rot.z,
                "w", (double) rot.w
        );
    }

    @LuaFunction
    public Map<String, Double> getSecondControllerPosition() throws LuaException {
        ServerVivePlayer player = getBoundVRPlayer();
        if (player.vrPlayerState == null)
            throw new LuaException("Missing VRPlayerState");
        Vec3 pos = player.vrPlayerState.controller1().position();
        return Map.of(
                "x", pos.x,
                "y", pos.y,
                "z", pos.z
        );
    }

    @LuaFunction
    public Map<String, Double> getSecondControllerOrientation() throws LuaException {
        ServerVivePlayer player = getBoundVRPlayer();
        if (player.vrPlayerState == null)
            throw new LuaException("Missing VRPlayerState");
        Quaternion rot = player.vrPlayerState.controller1().orientation();
        return Map.of(
                "x", (double) rot.x,
                "y", (double) rot.y,
                "z", (double) rot.z,
                "w", (double) rot.w
        );
    }

    @LuaFunction
    public boolean areHandsReversed() throws LuaException {
        ServerVivePlayer player = getBoundVRPlayer();
        if (player.vrPlayerState == null)
            throw new LuaException("Missing VRPlayerState");
        return player.vrPlayerState.reverseHands();
    }

    @LuaFunction
    public boolean isSeated() throws LuaException {
        ServerVivePlayer player = getBoundVRPlayer();
        if (player.vrPlayerState == null)
            throw new LuaException("Missing VRPlayerState");
        return player.vrPlayerState.seated();
    }

    @LuaFunction
    public boolean isCrawling() throws LuaException {
        ServerVivePlayer player = getBoundVRPlayer();
        return player.crawling;
    }

    @LuaFunction
    public double getDraw() throws LuaException {
        ServerVivePlayer player = getBoundVRPlayer();
        return player.draw;
    }

    @LuaFunction
    public double getWorldScale() throws LuaException {
        ServerVivePlayer player = getBoundVRPlayer();
        return player.worldScale;
    }

    @LuaFunction
    public double getHeightScale() throws LuaException {
        ServerVivePlayer player = getBoundVRPlayer();
        return player.heightScale;
    }

    @LuaFunction
    public Map<String, Double> getOffset() throws LuaException {
        ServerVivePlayer player = getBoundVRPlayer();
        return Map.of(
                "x", player.offset.x,
                "y", player.offset.y,
                "z", player.offset.z
        );
    }
    
    public ServerVivePlayer getBoundVRPlayer() throws LuaException {
        Level level = this.stand.getLevel();
        if (level == null || level.isClientSide)
            throw new LuaException("Level is Null or Clientside");
        if (this.stand.hasBoundPlayer())
            throw new LuaException("Has no Bound Player");
        ServerPlayer player = (ServerPlayer) level.getPlayerByUUID(this.stand.getBoundPlayer());
        if (player == null)
            throw new LuaException("Player not Online");
        if (!ServerVRPlayers.isVRPlayer(player))
            throw new LuaException("Player Is In Non-VR");
        return ServerVRPlayers.getVivePlayer(player);
    }
}
