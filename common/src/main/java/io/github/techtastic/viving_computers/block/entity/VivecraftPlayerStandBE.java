package io.github.techtastic.viving_computers.block.entity;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.shared.network.NetworkHandler;
import dan200.computercraft.shared.network.server.QueueEventServerMessage;
import dev.architectury.networking.NetworkManager;
import io.github.techtastic.viving_computers.PlatformUtils;
import io.github.techtastic.viving_computers.VivingComputersMod;
import io.github.techtastic.viving_computers.block.VCBlockEntities;
import io.github.techtastic.viving_computers.network.VCNetworking;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.vivecraft.client.VRPlayersClient;
import org.vivecraft.server.ServerVRPlayers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VivecraftPlayerStandBE extends BlockEntity {
    private final List<IComputerAccess> computers = new ArrayList<>();
    private final List<Integer> clientComputerIds = new ArrayList<>();
    private UUID boundPlayer = null;
    private Integer color = null;

    public VivecraftPlayerStandBE(BlockPos blockPos, BlockState blockState) {
        super(VCBlockEntities.PLAYER_STAND.get(), blockPos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, VivecraftPlayerStandBE stand) {
        if (!stand.hasBoundPlayer())
            return;

        Player bound = level.getPlayerByUUID(stand.getBoundPlayer());
        if (level.isClientSide) {
            if (!VRPlayersClient.getInstance().isVRPlayer(bound))
                stand.clearBoundPlayer();
        } else {
            if (!ServerVRPlayers.isVRPlayer((ServerPlayer) bound))
                stand.clearBoundPlayer();
        }
    }

    public boolean hasBoundPlayer() {
        return this.boundPlayer != null;
    }

    public UUID getBoundPlayer() {
        return this.boundPlayer;
    }

    public void setBoundPlayer(UUID uuid) {
        this.fireEvent("connect", uuid, this.boundPlayer);
        if (VivingComputersMod.BOUND_PLAYERS.containsKey(this.boundPlayer)) {
            BlockEntity be = level.getBlockEntity(VivingComputersMod.BOUND_PLAYERS.get(this.boundPlayer));
            if (be instanceof VivecraftPlayerStandBE stand)
                stand.clearBoundPlayer();
            else
                VivingComputersMod.BOUND_PLAYERS.remove(this.boundPlayer);
        }
        VivingComputersMod.BOUND_PLAYERS.put(this.boundPlayer, this.worldPosition);
        this.boundPlayer = uuid;
        this.setChanged();
    }

    public void clearBoundPlayer() {
        this.fireEvent("disconnect", this.boundPlayer);
        VivingComputersMod.BOUND_PLAYERS.remove(this.boundPlayer);
        this.boundPlayer = null;
        this.setChanged();
    }

    public boolean hasColor() {
        return this.color != null;
    }

    public Integer getColor() {
        return this.color;
    }

    public void setColor(Integer color) {
        this.color = color;
        this.setChanged();
    }

    public void attachComputer(IComputerAccess computer) {
        this.computers.add(computer);

        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeBlockPos(this.worldPosition);
        buf.writeInt(computer.getID());
        buf.writeBoolean(true);

        assert level != null;
        ((ServerLevel) level).getServer().getPlayerList().getPlayers()
                .forEach(player -> NetworkManager.sendToPlayer(player, VCNetworking.COMPUTER_ID_SYNC_PACKET_ID, buf));
    }

    public void detachComputer(IComputerAccess computer) {
        this.computers.remove(computer);

        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeBlockPos(this.worldPosition);
        buf.writeInt(computer.getID());
        buf.writeBoolean(false);

        assert level != null;
        ((ServerLevel) level).getServer().getPlayerList().getPlayers()
                .forEach(player -> NetworkManager.sendToPlayer(player, VCNetworking.COMPUTER_ID_SYNC_PACKET_ID, buf));
    }

    public void fireEvent(String event, Object... objects) {
        this.computers.forEach(computer -> computer.queueEvent(event, objects));
    }

    public void addClientComputer(int id) {
        this.clientComputerIds.add(id);
    }

    public void removeClientComputer(int id) {
        this.clientComputerIds.remove(id);
    }

    public void fireKeyEventFromClient(String event, Object... objects) {
        this.clientComputerIds.forEach(computer -> NetworkHandler
                .sendToServer(new QueueEventServerMessage(computer, event, objects)));
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        if (this.boundPlayer != null)
            compoundTag.putUUID("vc$boundPlayer", this.boundPlayer);
        if (this.color != null)
            compoundTag.putInt("vc$color", this.color);

        super.saveAdditional(compoundTag);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);

        if (compoundTag.contains("vc$boundPlayer"))
            this.boundPlayer = compoundTag.getUUID("vc$boundPlayer");
        if (compoundTag.contains("vc$color"))
            this.color = compoundTag.getInt("vc$color");
    }
}
