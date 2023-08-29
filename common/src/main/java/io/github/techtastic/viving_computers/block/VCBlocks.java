package io.github.techtastic.viving_computers.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.techtastic.viving_computers.PlatformUtils;
import io.github.techtastic.viving_computers.VivingComputersMod;
import io.github.techtastic.viving_computers.block.custom.VivecraftPlayerStandBlock;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class VCBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(VivingComputersMod.MOD_ID, Registry.BLOCK_REGISTRY);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(VivingComputersMod.MOD_ID, Registry.ITEM_REGISTRY);

    public static final RegistrySupplier<Block> PLAYER_STAND = BLOCKS.register("player_stand", () ->
            new VivecraftPlayerStandBlock(BlockBehaviour.Properties.of(Material.METAL)));

    public static void registerBlocks() {
        BLOCKS.register();

        for (RegistrySupplier<Block> supp : BLOCKS) {
            ITEMS.register(supp.getId(), () -> new BlockItem(supp.get(), new Item.Properties().tab(PlatformUtils.getComputerCraftTab())));
        }

        ITEMS.register();
    }
}
