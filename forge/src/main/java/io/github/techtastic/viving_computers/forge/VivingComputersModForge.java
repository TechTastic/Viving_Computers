package io.github.techtastic.viving_computers.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.techtastic.viving_computers.VCPartials;
import io.github.techtastic.viving_computers.VivingComputersMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(VivingComputersMod.MOD_ID)
public class VivingComputersModForge {

    public VivingComputersModForge() {
        // Submit our event bus to let architectury register our content on the right time
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(VivingComputersMod.MOD_ID, eventBus);
        eventBus.addListener(this::clientSetup);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> VCPartials::init);

        VivingComputersMod.init();
    }

    void clientSetup(FMLClientSetupEvent event) {
        VivingComputersMod.initClient();
    }
}
