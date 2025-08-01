package net.labymod.addons.truesight.v1_8_9.mixin.mixins;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.labymod.addons.truesight.v1_8_9.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.labymod.addons.truesight.v1_8_9.EntityUtils;
import net.labymod.addons.truesight.core.TNTChina;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static net.minecraft.client.renderer.GlStateManager.*;
import static org.lwjgl.opengl.GL11.*;

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
        boolean semiVisible = (!visible && (!entitylivingbaseIn.isInvisibleToPlayer((EntityPlayer) (Minecraft.getMinecraft()).thePlayer) || (TNTChina.TRUESIGHT.getState() && enabled)));

        if (visible || semiVisible) {
            if (!bindEntityTexture((Entity) entitylivingbaseIn)) {
                return;
            }

            if (semiVisible) {
                pushMatrix();
                color(1f, 1f, 1f, 0.15F);
                depthMask(false);
                glEnable(GL_BLEND);
                blendFunc(770, 771);
                alphaFunc(516, 0.003921569F);
            }

            if (TNTChina.ESP.getState() && enabled && EntityUtils.isSelected((Entity) entitylivingbaseIn, false)) {
                Minecraft mc = Minecraft.getMinecraft();
                boolean fancyGraphics = mc.gameSettings.fancyGraphics;
                mc.gameSettings.fancyGraphics = false;
                float gamma = mc.gameSettings.gammaSetting;
                mc.gameSettings.gammaSetting = 100000.0F;

                glPushMatrix();
                glPushAttrib(GL_ALL_ATTRIB_BITS);
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                glDisable(GL_TEXTURE_2D);
                glDisable(GL_LIGHTING);
                glDisable(GL_DEPTH_TEST);
                glEnable(GL_LINE_SMOOTH);
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                RenderUtils.glColor(EntityUtils.getColor(entitylivingbaseIn));
                glLineWidth(1.0F);

                this.mainModel.render((Entity) entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                glPopAttrib();
                glPopMatrix();

                mc.gameSettings.fancyGraphics = fancyGraphics;
                mc.gameSettings.gammaSetting = gamma;
            }

            this.mainModel.render((Entity) entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

            if (semiVisible) {
                disableBlend();
                alphaFunc(516, 0.1F);
                popMatrix();
                depthMask(true);
            }
        }
    }
}
