package net.labymod.addons.truesight.v1_8_9.mixin.mixins;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.labymod.addons.truesight.v1_8_9.ClientUtils;
import net.labymod.addons.truesight.v1_8_9.EntityUtils;
import net.labymod.addons.truesight.v1_8_9.OutlineUtils;
import net.labymod.addons.truesight.v1_8_9.TNTChina;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin({RendererLivingEntity.class})
public abstract class MixinRendererLivingEntity extends MixinRender {

    @Shadow
    protected ModelBase mainModel;


    /**
     * @author TNTChina
     * @reason Render model
     */
    @Overwrite
    protected <T extends net.minecraft.entity.EntityLivingBase> void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float scaleFactor) {
        boolean enabled = false;

        if (TrueSightAddon.addon != null) {
            enabled = TrueSightAddon.addon.configuration().enabled().get().booleanValue();
        }

        boolean visible = !entitylivingbaseIn.isInvisible();
        boolean semiVisible = (!visible && (!entitylivingbaseIn.isInvisibleToPlayer((EntityPlayer) (Minecraft.getMinecraft()).thePlayer) || (TNTChina.getModule("TrueSight").getState() && enabled)));

        if (visible || semiVisible) {
            if (!bindEntityTexture((Entity) entitylivingbaseIn)) {
                return;
            }

            if (semiVisible) {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
                GlStateManager.depthMask(false);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
                GlStateManager.alphaFunc(516, 0.003921569F);
            }

            if (TNTChina.getModule("ESP").getState() && enabled && EntityUtils.isSelected((Entity) entitylivingbaseIn, false)) {
                Minecraft mc = Minecraft.getMinecraft();
                boolean fancyGraphics = mc.gameSettings.fancyGraphics;
                mc.gameSettings.fancyGraphics = false;
                float gamma = mc.gameSettings.gammaSetting;
                mc.gameSettings.gammaSetting = 100000.0F;
                ClientUtils.disableFastRender();
                GlStateManager.resetColor();
                Color color = TNTChina.getColor((Entity) entitylivingbaseIn);
                OutlineUtils.setColor(color);
                OutlineUtils.renderOne(3.0F);
                this.mainModel.render((Entity) entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                OutlineUtils.setColor(color);
                OutlineUtils.renderTwo();
                this.mainModel.render((Entity) entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                OutlineUtils.setColor(color);
                OutlineUtils.renderThree();
                this.mainModel.render((Entity) entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                OutlineUtils.setColor(color);
                OutlineUtils.renderFour(color);
                this.mainModel.render((Entity) entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                OutlineUtils.setColor(color);
                OutlineUtils.renderFive();
                OutlineUtils.setColor(Color.WHITE);
                mc.gameSettings.fancyGraphics = fancyGraphics;
                mc.gameSettings.gammaSetting = gamma;
            }

            this.mainModel.render((Entity) entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

            if (semiVisible) {
                GlStateManager.disableBlend();
                GlStateManager.alphaFunc(516, 0.1F);
                GlStateManager.popMatrix();
                GlStateManager.depthMask(true);
            }
        }
    }
}
