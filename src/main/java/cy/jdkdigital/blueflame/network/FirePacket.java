package cy.jdkdigital.blueflame.network;

import cy.jdkdigital.blueflame.BlueFlame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record FirePacket(int entityID, CompoundTag data) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<FirePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(BlueFlame.MODID, "fire_packet"));

    public static final StreamCodec<FriendlyByteBuf, FirePacket> STREAM_CODEC = CustomPacketPayload.codec(FirePacket::write, FirePacket::new);

    private FirePacket(FriendlyByteBuf buffer) {
        this(buffer.readInt(), ByteBufCodecs.fromCodec(CompoundTag.CODEC).decode(buffer));
    }

    private void write(FriendlyByteBuf buffer) {
        buffer.writeInt(this.entityID);
        ByteBufCodecs.fromCodec(CompoundTag.CODEC).encode(buffer, data);
    }

    public static void clientHandle(final FirePacket data, final IPayloadContext context) {
        ClientLevel level = Minecraft.getInstance().level;

        if (level != null) {
            Entity entity = level.getEntity(data.entityID);

            if (entity != null) {
                entity.getData(BlueFlame.BLUE_FLAME_HANDLER).deserializeNBT(level.registryAccess(), data.data());
            }
        }
    }

    public static void serverHandle(final FirePacket data, final IPayloadContext context) {
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
