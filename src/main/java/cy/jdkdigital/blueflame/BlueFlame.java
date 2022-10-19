package cy.jdkdigital.blueflame;

import com.mojang.logging.LogUtils;
import cy.jdkdigital.blueflame.cap.IBlueFlameProvider;
import cy.jdkdigital.blueflame.network.PacketHandler;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BlueFlame.MODID)
public class BlueFlame
{
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "blueflame";

    public static final Capability<IBlueFlameProvider> BLUE_FLAME_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    public BlueFlame()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::commonSetup);
    }

    @SubscribeEvent
    public static void registerCap(RegisterCapabilitiesEvent event)
    {
        event.register(IBlueFlameProvider.class);
    }

    private void commonSetup(FMLCommonSetupEvent event)
    {
        PacketHandler.register();
    }
}
