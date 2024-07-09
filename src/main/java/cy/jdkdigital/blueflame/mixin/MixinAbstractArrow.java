package cy.jdkdigital.blueflame.mixin;

import cy.jdkdigital.blueflame.BlueFlame;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//@Debug(export = true)
@Mixin(value = AbstractArrow.class)
public abstract class MixinAbstractArrow extends Projectile
{
    protected MixinAbstractArrow(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(
            at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;igniteForSeconds(F)V")},
            method = {"onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V"}
    )
    public void igniteTarget(EntityHitResult hitResult, CallbackInfo callbackInfo) {
        var arrowData = this.getData(BlueFlame.BLUE_FLAME_HANDLER);
        if (arrowData.isOnFire()) {
            hitResult.getEntity().getData(BlueFlame.BLUE_FLAME_HANDLER).setOnFire(hitResult.getEntity());
        }
    }
}
