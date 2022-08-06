package cy.jdkdigital.blueflame.network;

import cy.jdkdigital.blueflame.BlueFlame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FirePacket
{
    private final CompoundTag nbt;
    private final int entityID;

    public FirePacket(int entityID, CompoundTag nbt)
    {
        this.nbt = nbt;
        this.entityID = entityID;
    }

    public static void encode(FirePacket msg, FriendlyByteBuf buf)
    {
        buf.writeInt(msg.entityID);
        buf.writeNbt(msg.nbt);
    }

    public static FirePacket decode(FriendlyByteBuf buf)
    {
        return new FirePacket(buf.readInt(), buf.readNbt());
    }

    public static class Handler
    {
        public static void handle(final FirePacket message, Supplier<NetworkEvent.Context> ctx)
        {
            ctx.get().enqueueWork(() ->
            {
                ClientLevel world = Minecraft.getInstance().level;

                if (world != null) {
                    Entity entity = world.getEntity(message.entityID);

                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.getCapability(BlueFlame.BLUE_FLAME_CAPABILITY).ifPresent(iBlueFlameProvider -> {
                            iBlueFlameProvider.deserializeNBT(message.nbt);
                        });
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
