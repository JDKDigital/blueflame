package cy.jdkdigital.blueflame.cap;

import cy.jdkdigital.blueflame.BlueFlame;
import cy.jdkdigital.blueflame.network.FirePacket;
import cy.jdkdigital.blueflame.network.PacketHandler;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;

public class BlueFlameImpl
{
    private static class DefaultImpl implements IBlueFlameProvider {
        private final LivingEntity livingEntity;

        private boolean isOnFire = false;

        private DefaultImpl(LivingEntity livingEntity)
        {
            this.livingEntity = livingEntity;
        }

        @Override
        public boolean isOnFire() {
            return isOnFire;
        }

        @Override
        public void sync(LivingEntity livingEntity) {
            PacketHandler.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> livingEntity), new FirePacket(livingEntity.getId(), serializeNBT()));
        }

        @Override
        public void setOnFire() {
            isOnFire = true;
            sync(livingEntity);
        }

        @Override
        public void unsetOnFire() {
            isOnFire = false;
            sync(livingEntity);
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean("isOnFire", isOnFire);
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag tag) {
            isOnFire = tag.getBoolean("isOnFire");
        }
    }

    public static class Provider implements ICapabilitySerializable<CompoundTag>
    {
        public static final ResourceLocation NAME = new ResourceLocation(BlueFlame.MODID, "blue_flame_on");

        private final DefaultImpl impl;
        private final LazyOptional<IBlueFlameProvider> cap;

        public Provider(LivingEntity livingEntity)
        {
            impl = new DefaultImpl(livingEntity);
            cap = LazyOptional.of(() -> impl);
        }

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing)
        {
            if (capability == BlueFlame.BLUE_FLAME_CAPABILITY)
            {
                return cap.cast();
            }
            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT()
        {
            return impl.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt)
        {
            impl.deserializeNBT(nbt);
        }
    }
}
