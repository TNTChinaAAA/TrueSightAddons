package net.labymod.addons.truesight.v1_8_9.mixin.mixins;

import java.awt.Color;
import net.labymod.addons.truesight.core.TrueSightAddon;
import net.labymod.addons.truesight.core.module.esp.EnumESPMode;
import net.labymod.addons.truesight.core.module.truesight.TrueSightSubSetting;
import net.labymod.addons.truesight.v1_8_9.ClientUtils;
import net.labymod.addons.truesight.v1_8_9.OutlineUtils;
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
        TrueSightAddon addon = TrueSightAddon.addon;

        if (addon != null) {
            enabled = addon.configuration().enabled().get().booleanValue();
        }

        TrueSightSubSetting trueSightSubSetting = addon.configuration().getTrueSight();
        boolean visible = !entitylivingbaseIn.isInvisible();
        boolean truesight_onlyPlayer = (!trueSightSubSetting.getOnlyPlayer().get().booleanValue()) || entitylivingbaseIn instanceof EntityPlayer;
        boolean truesight_targetSpectators = trueSightSubSetting.getTargetSpectator().get().booleanValue() || !(entitylivingbaseIn instanceof EntityPlayer tpl1 && tpl1.isSpectator());
        boolean semiVisible = (!visible && (!entitylivingbaseIn.isInvisibleToPlayer((EntityPlayer) (Minecraft.getMinecraft()).thePlayer) || (TNTChina.TRUESIGHT.getState() && enabled && truesight_onlyPlayer && truesight_targetSpectators)));
        boolean attackCheck = entitylivingbaseIn instanceof EntityPlayer pl1 && Minecraft.getMinecraft().thePlayer.canAttackPlayer(pl1);

        //entitylivingbaseIn.

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

            if (TNTChina.ESP.getState() && enabled && addon.configuration().getEsp().enabled().get().booleanValue() && EntityUtils.isSelected((Entity) entitylivingbaseIn, attackCheck)) {
                // WireFrame
                /*if (entitylivingbaseIn instanceof EntityPlayer al) {
                    TrueSightAddon.addon.logger().info(al.getDisplayName().getFormattedText());
                }*/
                Minecraft mc = Minecraft.getMinecraft();
                boolean fancyGraphics = mc.gameSettings.fancyGraphics;
                mc.gameSettings.fancyGraphics = false;
                float gamma = mc.gameSettings.gammaSetting;
                mc.gameSettings.gammaSetting = 100000.0F;

                switch (addon.configuration().getEsp().getEspMode()) {
                    case WIREFRAME:
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
                      glLineWidth(addon.configuration().getEsp().getLineWidth().get().floatValue());
                      this.mainModel.render((Entity) entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                      glPopAttrib();
                      glPopMatrix();
                      break;
                  case OUTLINE:
                      ClientUtils.disableFastRender();
                      resetColor();

                      final Color color = EntityUtils.getColor(entitylivingbaseIn);
                      OutlineUtils.setColor(color);
                      OutlineUtils.renderOne(addon.configuration().getEsp().getLineWidth().get().floatValue());
                      this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                      OutlineUtils.setColor(color);
                      OutlineUtils.renderTwo();
                      this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                      OutlineUtils.setColor(color);
                      OutlineUtils.renderThree();
                      this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                      OutlineUtils.setColor(color);
                      OutlineUtils.renderFour(color);
                      this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                      OutlineUtils.setColor(color);
                      OutlineUtils.renderFive();
                      OutlineUtils.setColor(Color.WHITE);
                }

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
