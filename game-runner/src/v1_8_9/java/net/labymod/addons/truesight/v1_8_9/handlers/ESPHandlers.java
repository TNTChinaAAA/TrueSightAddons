package net.labymod.addons.truesight.v1_8_9.handlers;

import net.labymod.addons.truesight.core.TNTChina;
import net.labymod.addons.truesight.core.TrueSightAddon;
import net.labymod.addons.truesight.core.event.EventListener;
import net.labymod.addons.truesight.core.event.EventTarget;
import net.labymod.addons.truesight.core.module.esp.ESPSubSetting;
import net.labymod.addons.truesight.core.module.esp.EnumESPMode;
import net.labymod.addons.truesight.core.module.truesight.TrueSightSubSetting;
import net.labymod.addons.truesight.v1_8_9.EntityUtils;
import net.labymod.addons.truesight.v1_8_9.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.*;
import java.awt.Color;
import java.nio.FloatBuffer;
import static net.minecraft.client.renderer.GlStateManager.*;
import static org.lwjgl.opengl.GL11.*;

public class ESPHandlers implements EventListener {

    @EventTarget
    public void onRender3D(float partialTicks) {
      Minecraft mc = Minecraft.getMinecraft();
      TrueSightAddon addon = TrueSightAddon.addon;

      if (addon != null) {
          ESPSubSetting espSubSetting = addon.configuration().getEsp();

          if (addon.configuration().enabled().get().booleanValue() && espSubSetting.enabled().get().booleanValue() && TNTChina.ESP.getState()) {
            boolean real2d = espSubSetting.getEspMode() == EnumESPMode.REAL2D;
            Matrix4f mvMatrix = this.getMatrix(GL_MODELVIEW_MATRIX);
            Matrix4f projectionMatrix = this.getMatrix(GL_PROJECTION_MATRIX);

            if (real2d) {
              glPushAttrib(GL_ENABLE_BIT);
              glEnable(GL_BLEND);
              glDisable(GL_TEXTURE_2D);
              glDisable(GL_DEPTH_TEST);
              glMatrixMode(GL_PROJECTION);
              glPushMatrix();
              glLoadIdentity();
              glOrtho(0.0, mc.displayWidth, mc.displayHeight, 0.0, -1.0, 1.0);
              glMatrixMode(GL_MODELVIEW);
              glPushMatrix();
              glLoadIdentity();
              glDisable(GL_DEPTH_TEST);
              glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
              enableTexture2D();
              glDepthMask(true);
              glLineWidth(1f);
            }

            for (Entity entity : mc.theWorld.loadedEntityList) {
              if (!(entity instanceof EntityLivingBase)) continue;
              EntityLivingBase entitylivingbaseIn = (EntityLivingBase) entity;

              TrueSightSubSetting trueSightSubSetting = addon.configuration().getTrueSight();
              boolean visible = !entitylivingbaseIn.isInvisible();
              boolean truesight_onlyPlayer = (!trueSightSubSetting.getOnlyPlayer().get().booleanValue()) || entitylivingbaseIn instanceof EntityPlayer;
              boolean truesight_targetSpectators = trueSightSubSetting.getTargetSpectator().get().booleanValue() || !(entitylivingbaseIn instanceof EntityPlayer tpl1 && tpl1.isSpectator());
              boolean semiVisible = (!visible && (!entitylivingbaseIn.isInvisibleToPlayer((EntityPlayer) (Minecraft.getMinecraft()).thePlayer) || (
                  TNTChina.TRUESIGHT.getState() && truesight_onlyPlayer && truesight_targetSpectators)));
              boolean attackCheck = entitylivingbaseIn instanceof EntityPlayer pl1 && Minecraft.getMinecraft().thePlayer.canAttackPlayer(pl1);


              if (entity != mc.thePlayer && EntityUtils.isSelected((Entity) entitylivingbaseIn, attackCheck) && (visible || semiVisible)) {
                  Color color = EntityUtils.getColor(entity);

                  switch (espSubSetting.getEspMode()) {
                    case BOX, OTHERBOX-> {
                      RenderUtils.drawEntityBox(entity, color, espSubSetting.getEspMode() != EnumESPMode.OTHERBOX);
                      break;
                    }

                    case RENDER2D -> {
                      RenderManager renderManager = mc.getRenderManager();
                      Timer timer = mc.timer;
                      double posX =
                          entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * timer.renderPartialTicks - renderManager.renderPosX;
                      double posY =
                          entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * timer.renderPartialTicks - renderManager.renderPosY;
                      double posZ =
                          entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * timer.renderPartialTicks - renderManager.renderPosZ;
                      RenderUtils.draw2D((EntityLivingBase) entity, posX, posY, posZ, color.getRGB(), Color.BLACK.getRGB());
                    }

                    case REAL2D -> {
                      RenderManager renderManager = mc.getRenderManager();
                      Timer timer = mc.timer;
                      AxisAlignedBB bb = ((EntityLivingBase) entity).getEntityBoundingBox()
                          .offset(-entity.posX, -entity.posY, -entity.posZ)
                          .offset(
                              entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * timer.renderPartialTicks,
                              entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * timer.renderPartialTicks,
                              entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * timer.renderPartialTicks
                          )
                          .offset(-renderManager.renderPosX, -renderManager.renderPosY, -renderManager.renderPosZ);

                      double[][] boxVertices = new double[][] {
                          {bb.minX, bb.minY, bb.minZ},
                          {bb.minX, bb.maxY, bb.minZ},
                          {bb.maxX, bb.maxY, bb.minZ},
                          {bb.maxX, bb.minY, bb.minZ},
                          {bb.minX, bb.minY, bb.maxZ},
                          {bb.minX, bb.maxY, bb.maxZ},
                          {bb.maxX, bb.maxY, bb.maxZ},
                          {bb.maxX, bb.minY, bb.maxZ}
                      };

                      var minX = Float.MAX_VALUE;
                      var minY = Float.MAX_VALUE;
                      var maxX = -1f;
                      var maxY = -1f;

                      for (double[] boxVertex : boxVertices) {
                        Vector2f screenPos = this.worldToScreen(
                            new Vector3f(
                                (float) boxVertex[0],
                                (float) boxVertex[1],
                                (float) boxVertex[2]
                            ), mvMatrix, projectionMatrix, mc.displayWidth, mc.displayHeight
                        );

                        if (screenPos == null) {
                          continue;
                        }

                        minX = Math.min(screenPos.x, minX);
                        minY = Math.min(screenPos.y, minY);
                        maxX = Math.max(screenPos.x, maxX);
                        maxY = Math.max(screenPos.y, maxY);
                      }

                      if (minX > 0 || minY > 0 || maxX <= mc.displayWidth || maxY <= mc.displayWidth) {
                        glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1f);
                        glBegin(GL_LINE_LOOP);
                        glVertex2f(minX, minY);
                        glVertex2f(minX, maxY);
                        glVertex2f(maxX, maxY);
                        glVertex2f(maxX, minY);
                        glEnd();
                      }
                    }
                  }
              }
            }

            if (real2d) {
              glColor4f(1f, 1f, 1f, 1f);
              glEnable(GL_DEPTH_TEST);
              glMatrixMode(GL_PROJECTION);
              glPopMatrix();
              glMatrixMode(GL_MODELVIEW);
              glPopMatrix();
              glPopAttrib();
            }

          }
      }
    }

  public Matrix4f getMatrix(int matrix) {
    FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
    GL11.glGetFloatv(matrix,floatBuffer);
    Matrix4f mat = new Matrix4f();
    mat.load(floatBuffer);
    return mat;
  }

  public Vector2f worldToScreen(Vector3f pointInWorld) {
    return worldToScreen(pointInWorld,
        getMatrix(GL11.GL_MODELVIEW_MATRIX),
        getMatrix(GL11.GL_PROJECTION_MATRIX),
        Minecraft.getMinecraft().displayWidth,
        Minecraft.getMinecraft().displayHeight);
  }

  public Vector2f worldToScreen(Vector3f pointInWorld, Matrix4f viewMatrix, Matrix4f projectionMatrix, int screenWidth, int screenHeight) {
    Vector4f clipSpacePos = multiply(multiply(new Vector4f(pointInWorld.x, pointInWorld.y, pointInWorld.z, 1f), viewMatrix), projectionMatrix);

    Vector3f ndcSpacePos = new Vector3f(clipSpacePos.x, clipSpacePos.y, clipSpacePos.z);
    ndcSpacePos.scale(1f / clipSpacePos.w);

    float screenX = (ndcSpacePos.x + 1f) / 2f * screenWidth;
    float screenY = (1f - ndcSpacePos.y) / 2f * screenHeight;

    return Math.abs(ndcSpacePos.z) > 1 ? null : new Vector2f(screenX, screenY);
  }

  private Vector4f multiply(Vector4f vec, Matrix4f mat) {
    return new Vector4f(
        vec.x * mat.m00 + vec.y * mat.m10 + vec.z * mat.m20 + vec.w * mat.m30,
        vec.x * mat.m01 + vec.y * mat.m11 + vec.z * mat.m21 + vec.w * mat.m31,
        vec.x * mat.m02 + vec.y * mat.m12 + vec.z * mat.m22 + vec.w * mat.m32,
        vec.x * mat.m03 + vec.y * mat.m13 + vec.z * mat.m23 + vec.w * mat.m33
    );
  }
}
