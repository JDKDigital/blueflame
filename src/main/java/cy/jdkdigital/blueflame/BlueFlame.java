package cy.jdkdigital.blueflame;

import com.mojang.logging.LogUtils;
import cy.jdkdigital.blueflame.cap.BlueFlameHandler;
import cy.jdkdigital.blueflame.network.FirePacket;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;

import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BlueFlame.MODID)
public class BlueFlame
{
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "blueflame";

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MODID);
    public static final Supplier<AttachmentType<BlueFlameHandler>> BLUE_FLAME_HANDLER = ATTACHMENT_TYPES.register("blue_flame_handler", () -> AttachmentType.serializable(BlueFlameHandler::new).build());

    public BlueFlame(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::payloadHandler);

        ATTACHMENT_TYPES.register(modEventBus);
    }

    public void payloadHandler(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MODID).versioned("1").optional();
        registrar.playToClient(
                FirePacket.TYPE,
                FirePacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        FirePacket::clientHandle,
                        FirePacket::serverHandle
                )
        );
    }
}
