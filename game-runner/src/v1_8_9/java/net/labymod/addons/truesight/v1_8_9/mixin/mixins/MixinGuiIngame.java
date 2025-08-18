package net.labymod.addons.truesight.v1_8_9.mixin.mixins;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.labymod.addons.truesight.v1_8_9.GuiUtil;
import net.labymod.addons.truesight.core.Module;
import net.labymod.addons.truesight.core.TNTChina;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame extends Gui {

    @Inject(method = "renderGameOverlay", at = @At("RETURN"))
    public void onRenderGameOverlay(float partialTicks, CallbackInfo callbackInfo) {
        //LoadedAddon addon = Laby.labyAPI().addonService().getAddon("true-sight").get();

        //ClientUtils.getLogger().info(addon.getClass().getName());

        if (TrueSightAddon.addon != null) {
            if (TrueSightAddon.addon.configuration().enabled().get().booleanValue()) {
                ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
                int width = sr.getScaledWidth();
                int height = sr.getScaledHeight();
                GlStateManager.pushMatrix();
                AtomicInteger index = new AtomicInteger();
                int i = height - 2 - TNTChina.RENDER_MODULES_MAP.size() * 12;

                for (Entry<Module, ConfigProperty<Boolean>> entry : TNTChina.RENDER_MODULES_MAP.entrySet()) {
                    int color = TNTChina.rainbow(index.get() * 320);
                    int x = 2;
                    String str_1 = entry.getKey().getName() + ": " + (entry.getKey().getState() ? "Enabled" : "Disabled");
                    String str_2 = entry.getKey().getName() + ": Disabled";
                    String str = entry.getValue().get().booleanValue() ? str_1 : str_2;

                    GuiUtil.drawRect((x + 2), (i + 1), (Minecraft.getMinecraft().fontRendererObj.getStringWidth(str) + 1), 10.0D, 1073741824);
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(str, (x + 3), (i + 1), color);
                    i += 12;
                    index.getAndIncrement();
                }

                GlStateManager.popMatrix();
            }
        }
    }
}
