package io.github.techtastic.viving_computers.forge;

import dan200.computercraft.api.peripheral.IPeripheralProvider;
import dan200.computercraft.shared.Registry;
import io.github.techtastic.viving_computers.PlatformUtils;
import io.github.techtastic.viving_computers.forge.integration.cc.VivingComputersPeripheralProvider;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

public class PlatformUtilsImpl {
    /**
     * This is our actual method to {@link PlatformUtils#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static IPeripheralProvider getPeripheralProvider() {
        return new VivingComputersPeripheralProvider();
    }

    public static CreativeModeTab getComputerCraftTab() {
        Optional<CreativeModeTab> possibleTab = Arrays.stream(CreativeModeTab.TABS)
                .filter(tab -> tab.getIconItem().is(Registry.ModBlocks.COMPUTER_NORMAL.get().asItem())).findFirst();

        return possibleTab.orElse(CreativeModeTab.TAB_MISC);
    }
}
