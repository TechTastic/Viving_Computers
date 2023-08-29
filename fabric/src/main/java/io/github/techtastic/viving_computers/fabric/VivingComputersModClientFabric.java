package io.github.techtastic.viving_computers.fabric;

import io.github.techtastic.viving_computers.VCPartials;
import io.github.techtastic.viving_computers.VivingComputersMod;
import net.fabricmc.api.ClientModInitializer;

public class VivingComputersModClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VCPartials.init();
        VivingComputersMod.initClient();
    }
}
