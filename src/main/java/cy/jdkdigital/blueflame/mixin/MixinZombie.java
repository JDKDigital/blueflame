package cy.jdkdigital.blueflame.mixin;

import cy.jdkdigital.blueflame.BlueFlame;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//@Debug(export = true)
@Mixin(value = Zombie.class)
public abstract class MixinZombie
{
    @Inject(at = {@At("RETURN")}, method = {"doHurtTarget"})
    public void doHurtTarget(Entity target, CallbackInfoReturnable<Boolean> cir) {
        Entity zombie = (Entity) (Object) this;
        if (!zombie.level().isClientSide() && target.isOnFire()) {
            var zombieData = zombie.getData(BlueFlame.BLUE_FLAME_HANDLER);
            if (zombieData.isOnFire()) {
                target.getData(BlueFlame.BLUE_FLAME_HANDLER).setOnFire(target);
            }
        }
    }
}
