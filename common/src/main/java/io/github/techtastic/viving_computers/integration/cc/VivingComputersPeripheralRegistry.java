package io.github.techtastic.viving_computers.integration.cc;

import dan200.computercraft.api.ComputerCraftAPI;
import io.github.techtastic.viving_computers.PlatformUtils;

public class VivingComputersPeripheralRegistry {
    public static void registerPeripherals() {
        ComputerCraftAPI.registerPeripheralProvider(PlatformUtils.getPeripheralProvider());
    }
}
