package net.labymod.addons.truesight.v1_8_9.handlers;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.labymod.addons.truesight.core.listener.CleanView;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CleanViewHandlers implements CleanView {

    private final Map<UUID, Boolean> modified = new HashMap<>();
    private WeakReference<EntityLivingBase> ref;

    @Override
    public void onCleanView() {
        boolean enabled = false;

        if (TrueSightAddon.addon != null) {
            enabled = TrueSightAddon.addon.configuration().getCleanView().get().booleanValue();
        }

        EntityLivingBase ent = (enabled && Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityLivingBase livingEntity) ? livingEntity : null;
        EntityLivingBase prevEnt = (this.ref != null) ? this.ref.get() : null;

        if (prevEnt != ent) {
            if (prevEnt != null && Boolean.TRUE.equals(this.modified.get(prevEnt.getUniqueID()))) {
                Collection<PotionEffect> effects = prevEnt.getActivePotionEffects();

                if (!effects.isEmpty()) {
                    prevEnt.getDataWatcher().updateObject(7, PotionHelper.calcPotionLiquidColor(effects));
                }

                this.modified.remove(prevEnt.getUniqueID());
            }

            this.ref = (ent != null) ? new WeakReference<>(ent) : null;
        }

        if (ent != null) {
            ent.getDataWatcher().updateObject(7, 0);

            if (!Boolean.TRUE.equals(this.modified.get(ent.getUniqueID()))) {
                this.modified.put(ent.getUniqueID(), true);
            }
        }
    }
}
