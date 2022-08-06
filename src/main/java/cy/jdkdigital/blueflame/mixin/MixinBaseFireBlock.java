package cy.jdkdigital.blueflame.mixin;

import cy.jdkdigital.blueflame.BlueFlame;
import cy.jdkdigital.blueflame.cap.IBlueFlameProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(value = BaseFireBlock.class)
public class MixinBaseFireBlock
{
    @Inject(
            at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z")},
            method = {"entityInside(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)V"}
    )
    public void entityInside(BlockState blockState, Level level, BlockPos pos, Entity entity, CallbackInfo callbackInfo) {
        if (!entity.level.isClientSide() && blockState.getBlock() instanceof SoulFireBlock && entity instanceof LivingEntity livingEntity) {
            livingEntity.getCapability(BlueFlame.BLUE_FLAME_CAPABILITY).ifPresent(IBlueFlameProvider::setOnFire);
        }
    }
}
