package cy.jdkdigital.blueflame.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import cy.jdkdigital.blueflame.BlueFlame;
import cy.jdkdigital.blueflame.client.Bakery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

//@Debug(export = true)
@Mixin(value = ScreenEffectRenderer.class)
public class MixinScreenEffectRenderer
{
    @ModifyVariable(method = "renderFire", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;"))
    private static TextureAtlasSprite blueFlameVar(TextureAtlasSprite value, Minecraft minecraft, PoseStack poseStack) {
        return minecraft.player.getData(BlueFlame.BLUE_FLAME_HANDLER).isOnFire() ? Bakery.soulFireSprite1.sprite() : value;
    }
}
