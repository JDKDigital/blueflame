package cy.jdkdigital.blueflame.network;

import cy.jdkdigital.blueflame.BlueFlame;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PacketHandler
{
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(BlueFlame.MODID, "fire_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    private static int index;

    public static void register()
    {
        registerMessage(FirePacket.class, FirePacket::encode, FirePacket::decode, FirePacket.Handler::handle);
    }

    private static <MSG> void registerMessage(Class<MSG> type, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> consumer)
    {
        HANDLER.registerMessage(index++, type, encoder, decoder, consumer);
    }

    public static void send(PacketDistributor.PacketTarget target, FirePacket message)
    {
        HANDLER.send(target, message);
    }
}
