package cy.jdkdigital.blueflame.cap;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.INBTSerializable;

public interface IBlueFlameProvider extends INBTSerializable<CompoundTag>
{
    boolean isOnFire();

    void sync(Entity entity);

    void setOnFire();

    void unsetOnFire();
}
