package io.github.techtastic.viving_computers.network;

import dev.architectury.networking.NetworkManager;
import io.github.techtastic.viving_computers.VivingComputersMod;
import io.github.techtastic.viving_computers.block.entity.VivecraftPlayerStandBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

public class VCNetworking {
    public static final ResourceLocation COMPUTER_ID_SYNC_PACKET_ID =
            new ResourceLocation(VivingComputersMod.MOD_ID, "bound_player_key_event_packet");

    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, COMPUTER_ID_SYNC_PACKET_ID, (buf, context) -> {
            BlockPos pos = buf.readBlockPos();
            int id = buf.readInt();
            ClientLevel level = Minecraft.getInstance().level;
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof VivecraftPlayerStandBE stand) {
                if (buf.readBoolean())
                    stand.addClientComputer(id);
                else
                    stand.removeClientComputer(id);
            }
        });
    }
}
