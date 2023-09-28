package io.github.techtastic.viving_computers.mixin;

import dan200.computercraft.shared.computer.inventory.ContainerComputerBase;
import dan200.computercraft.shared.network.container.ComputerContainerData;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.vivecraft.client_vr.VRState;
import org.vivecraft.client_vr.gameplay.screenhandlers.KeyboardHandler;

@Mixin(ContainerComputerBase.class)
public class MixinContainerComputerBase {
    @Inject(method = "<init>(Lnet/minecraft/world/inventory/MenuType;ILnet/minecraft/world/entity/player/Inventory;Ldan200/computercraft/shared/network/container/ComputerContainerData;)V", at = @At("TAIL"))
    public void VivingComputers$addVRKeyboard(MenuType<? extends ContainerComputerBase> type, int id, Inventory player, ComputerContainerData data, CallbackInfo ci) {
        if (VRState.vrRunning)
            KeyboardHandler.setOverlayShowing(true);
    }
}
