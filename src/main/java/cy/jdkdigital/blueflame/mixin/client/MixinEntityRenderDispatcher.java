package cy.jdkdigital.blueflame.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import cy.jdkdigital.blueflame.BlueFlame;
import cy.jdkdigital.blueflame.cap.IBlueFlameProvider;
import cy.jdkdigital.blueflame.client.Bakery;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

//@Debug(export = true)
@Mixin(value = EntityRenderDispatcher.class)
public class MixinEntityRenderDispatcher {

    @ModifyVariable(method = "renderFlame", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 0), ordinal = 0)
    private TextureAtlasSprite blueFlameVar0(TextureAtlasSprite value, PoseStack poseStack, MultiBufferSource bufferSource, Entity entity) {
        return entity.getCapability(BlueFlame.BLUE_FLAME_CAPABILITY).filter(IBlueFlameProvider::isOnFire).map(h -> Bakery.soulFireSprite0.sprite()).orElse(value);
    }

    @ModifyVariable(method = "renderFlame", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 1), ordinal = 1)
    private TextureAtlasSprite blueFlameVar1(TextureAtlasSprite value, PoseStack poseStack, MultiBufferSource bufferSource, Entity entity) {
        return entity.getCapability(BlueFlame.BLUE_FLAME_CAPABILITY).filter(IBlueFlameProvider::isOnFire).map(h -> Bakery.soulFireSprite1.sprite()).orElse(value);
    }
}
