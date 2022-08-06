package cy.jdkdigital.blueflame.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import cy.jdkdigital.blueflame.BlueFlame;
import cy.jdkdigital.blueflame.cap.IBlueFlameProvider;
import cy.jdkdigital.blueflame.client.Bakery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Debug(export = true)
@Mixin(value = ScreenEffectRenderer.class)
public class MixinScreenEffectRenderer
{
    @ModifyVariable(method = "renderFire", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;"))
    private static TextureAtlasSprite blueFlameVar(TextureAtlasSprite value, Minecraft minecraft, PoseStack poseStack) {
        return minecraft.player.getCapability(BlueFlame.BLUE_FLAME_CAPABILITY).filter(IBlueFlameProvider::isOnFire).map(h -> Bakery.soulFireSprite1).orElse(value);
    }
}
