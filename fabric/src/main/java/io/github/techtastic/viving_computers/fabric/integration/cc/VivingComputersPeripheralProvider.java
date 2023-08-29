package io.github.techtastic.viving_computers.fabric.integration.cc;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import io.github.techtastic.viving_computers.block.entity.VivecraftPlayerStandBE;
import io.github.techtastic.viving_computers.integration.cc.peripherals.VivecraftPlayerStandPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class VivingComputersPeripheralProvider implements IPeripheralProvider {
    @Override
    public IPeripheral getPeripheral(Level level, BlockPos blockPos, Direction direction) {
        BlockEntity be = level.getBlockEntity(blockPos);
        if (be instanceof VivecraftPlayerStandBE stand)
            return new VivecraftPlayerStandPeripheral(stand);
        return null;
    }
}
