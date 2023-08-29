package io.github.techtastic.viving_computers.fabric;

import io.github.techtastic.viving_computers.VivingComputersMod;
import net.fabricmc.api.ModInitializer;

public class VivingComputersModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VivingComputersMod.init();
    }
}
