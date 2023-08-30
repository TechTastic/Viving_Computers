package io.github.techtastic.viving_computers;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import io.github.techtastic.viving_computers.block.VCBlockEntities;
import io.github.techtastic.viving_computers.block.VCBlocks;
import io.github.techtastic.viving_computers.block.entity.renderer.VivecraftPlayerStandBER;
import io.github.techtastic.viving_computers.integration.cc.VivingComputersPeripheralRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.UUID;

public class VivingComputersMod {
    public static final String MOD_ID = "viving_computers";
    public static final HashMap<UUID, BlockPos> BOUND_PLAYERS = new HashMap<>();

    public static void init() {
        VCBlocks.registerBlocks();
        VCBlockEntities.registerBlockEntities();

        VivingComputersPeripheralRegistry.registerPeripherals();
    }

    public static void initClient() {
        RenderTypeRegistry.register(RenderType.cutout(), VCBlocks.PLAYER_STAND.get());

        BlockEntityRendererRegistry.register(VCBlockEntities.PLAYER_STAND.get(), VivecraftPlayerStandBER::new);
    }
}
