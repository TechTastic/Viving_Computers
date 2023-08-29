package io.github.techtastic.viving_computers.forge.integration.cc;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import io.github.techtastic.viving_computers.block.entity.VivecraftPlayerStandBE;
import io.github.techtastic.viving_computers.integration.cc.peripherals.VivecraftPlayerStandPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class VivingComputersPeripheralProvider implements IPeripheralProvider {
    @NotNull
    @Override
    public LazyOptional<IPeripheral> getPeripheral(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull Direction direction) {
        BlockEntity be = level.getBlockEntity(blockPos);
        if (be instanceof VivecraftPlayerStandBE stand)
            return LazyOptional.of(() -> new VivecraftPlayerStandPeripheral(stand));
        return LazyOptional.empty();
    }
}
