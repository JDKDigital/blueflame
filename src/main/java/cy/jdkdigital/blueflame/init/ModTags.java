package cy.jdkdigital.blueflame.init;

import cy.jdkdigital.blueflame.BlueFlame;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class ModTags
{
    public static final TagKey<Fluid> SOUL_FLAME_SOURCE = FluidTags.create(new ResourceLocation(BlueFlame.MODID, "soul_flame_source"));
}
