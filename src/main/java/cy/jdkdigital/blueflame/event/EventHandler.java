package cy.jdkdigital.blueflame.event;

import cy.jdkdigital.blueflame.BlueFlame;
import cy.jdkdigital.blueflame.cap.BlueFlameImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BlueFlame.MODID)
public class EventHandler
{
    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<Entity> evt)
    {
        if(evt.getObject() instanceof LivingEntity)
        {
            evt.addCapability(BlueFlameImpl.Provider.NAME, new BlueFlameImpl.Provider((LivingEntity) evt.getObject()));
        }
    }
}
