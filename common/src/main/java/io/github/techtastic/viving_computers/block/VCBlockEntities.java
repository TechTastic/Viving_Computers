package io.github.techtastic.viving_computers.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.techtastic.viving_computers.VivingComputersMod;
import io.github.techtastic.viving_computers.block.entity.VivecraftPlayerStandBE;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class VCBlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(VivingComputersMod.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<BlockEntityType<VivecraftPlayerStandBE>> PLAYER_STAND =
            BLOCK_ENTITIES.register("player_stand", () -> BlockEntityType.Builder.of(
                    VivecraftPlayerStandBE::new,
                    VCBlocks.PLAYER_STAND.get()
            ).build(null));

    public static void registerBlockEntities() {
        BLOCK_ENTITIES.register();
    }
}
