package io.github.techtastic.viving_computers.block.entity;

import io.github.techtastic.viving_computers.PlatformUtils;
import io.github.techtastic.viving_computers.block.VCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.vivecraft.client.VRPlayersClient;
import org.vivecraft.server.ServerVRPlayers;

import java.util.UUID;

public class VivecraftPlayerStandBE extends BlockEntity {
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
                stand.setBoundPlayer(null);
        } else {
            if (!ServerVRPlayers.isVRPlayer((ServerPlayer) bound))
                stand.setBoundPlayer(null);
        }
    }

    public boolean hasBoundPlayer() {
        return this.boundPlayer != null;
    }

    public UUID getBoundPlayer() {
        return this.boundPlayer;
    }

    public void setBoundPlayer(UUID uuid) {
        this.boundPlayer = uuid;
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
