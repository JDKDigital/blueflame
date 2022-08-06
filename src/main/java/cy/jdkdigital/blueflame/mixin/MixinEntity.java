package cy.jdkdigital.blueflame.mixin;

import cy.jdkdigital.blueflame.BlueFlame;
import cy.jdkdigital.blueflame.cap.IBlueFlameProvider;
import cy.jdkdigital.blueflame.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(value = Entity.class)
public abstract class MixinEntity
{
    @Inject(at = {@At("HEAD")}, method = {"lavaHurt()V"})
    public void lavaHurt(CallbackInfo callbackInfo) {
        Entity entity = (Entity) (Object) this;
        if (!entity.level.isClientSide() && entity instanceof LivingEntity livingEntity) {
            AABB aabb = entity.getBoundingBox().deflate(0.001D);
            BlockPos.MutableBlockPos mutableBlockpos = new BlockPos.MutableBlockPos();
            for(int l1 = Mth.floor(aabb.minX); l1 < Mth.ceil(aabb.maxX); ++l1) {
                for(int i2 = Mth.floor(aabb.minY); i2 < Mth.ceil(aabb.maxY); ++i2) {
                    for(int j2 = Mth.floor(aabb.minZ); j2 < Mth.ceil(aabb.maxZ); ++j2) {
                        mutableBlockpos.set(l1, i2, j2);
                        FluidState fluidstate = entity.level.getFluidState(mutableBlockpos);
                        if (fluidstate.is(ModTags.SOUL_FLAME_SOURCE)) {
                            livingEntity.getCapability(BlueFlame.BLUE_FLAME_CAPABILITY).ifPresent(IBlueFlameProvider::setOnFire);
                        }
                    }
                }
            }
        }
    }

    @Inject(at = {@At("HEAD")}, method = {"setRemainingFireTicks(I)V"})
    public void setRemainingFireTicks(int remainingFireTicks, CallbackInfo callbackInfo) {
        Entity entity = (Entity) (Object) this;
        if (!entity.level.isClientSide() && remainingFireTicks <= 0 && entity instanceof LivingEntity livingEntity) {
            livingEntity.getCapability(BlueFlame.BLUE_FLAME_CAPABILITY).ifPresent(IBlueFlameProvider::unsetOnFire);
        }
    }
}
