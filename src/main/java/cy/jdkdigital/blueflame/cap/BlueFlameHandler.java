package cy.jdkdigital.blueflame.cap;

import cy.jdkdigital.blueflame.network.FirePacket;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.UnknownNullability;

public class BlueFlameHandler implements INBTSerializable<CompoundTag>
{
    private boolean isOnFire = false;

    public BlueFlameHandler() {
    }

    public void sync(Entity entity) {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new FirePacket(entity.getId(), serializeNBT(entity.level().registryAccess())));
    }

    public boolean isOnFire() {
        return isOnFire;
    }

    public void setOnFire(Entity entity) {
        isOnFire = true;
        sync(entity);
    }

    public void unsetOnFire(Entity entity) {
        isOnFire = false;
        sync(entity);
    }

    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        var tag = new CompoundTag();
        tag.putBoolean("isOnFire", isOnFire);
        return tag;
    }

    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        isOnFire = tag.getBoolean("isOnFire");
    }
}
