package io.github.techtastic.viving_computers.mixin;

import io.github.techtastic.viving_computers.VivingComputersMod;
import io.github.techtastic.viving_computers.block.entity.VivecraftPlayerStandBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.vivecraft.client_vr.gui.PhysicalKeyboard.KeyButton;
import org.vivecraft.client_vr.provider.ControllerType;

@Mixin(KeyButton.class)
public class MixinKeyButton {
    @Shadow @Final public int id;
    @Unique
    private Minecraft mc;

    @Redirect(method = "press",
            at = @At(
                    target = "Lnet/minecraft/client/Minecraft;getSoundManager()Lnet/minecraft/client/sounds/SoundManager;",
                    value = "INVOKE"
            ))
    private SoundManager vc$grabMC(Minecraft instance) {
        mc = instance;

        return instance.getSoundManager();
    }

    @Inject(method = "press", at = @At("TAIL"))
    private void vc$throwKeyboardEvent (ControllerType controller,boolean isRepeat, CallbackInfo ci){
        ClientLevel level = mc.level;
        LocalPlayer player = mc.player;
        if (player != null && VivingComputersMod.BOUND_PLAYERS.containsKey(player.getUUID())) {
            BlockPos pos = VivingComputersMod.BOUND_PLAYERS.get(player.getUUID());
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof VivecraftPlayerStandBE stand)
                stand.fireKeyEventFromClient("vr_keyboard", id, isRepeat, controller.name());
        }
    }
}
