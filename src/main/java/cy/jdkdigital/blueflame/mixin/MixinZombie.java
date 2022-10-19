package cy.jdkdigital.blueflame.mixin;

import cy.jdkdigital.blueflame.BlueFlame;
import cy.jdkdigital.blueflame.cap.IBlueFlameProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(value = Zombie.class)
public abstract class MixinZombie
{
    @Inject(at = {@At("RETURN")}, method = {"doHurtTarget"})
    public void doHurtTarget(Entity target, CallbackInfoReturnable<Boolean> cir) {
        Entity zombie = (Entity) (Object) this;
        if (!zombie.level.isClientSide() && target.isOnFire()) {
            if (zombie.getCapability(BlueFlame.BLUE_FLAME_CAPABILITY).map(IBlueFlameProvider::isOnFire).orElse(false)) {
                target.getCapability(BlueFlame.BLUE_FLAME_CAPABILITY).ifPresent(IBlueFlameProvider::setOnFire);
            }
        }
    }
}
