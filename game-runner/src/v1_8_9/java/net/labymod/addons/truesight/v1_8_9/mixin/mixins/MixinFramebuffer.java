package net.labymod.addons.truesight.v1_8_9.mixin.mixins;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.labymod.addons.truesight.core.gpuTape.CleanException;
import net.labymod.addons.truesight.core.gpuTape.FramebufferFixer;
import net.labymod.addons.truesight.core.gpuTape.GpuTape;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import java.lang.ref.Cleaner;

@Mixin(Framebuffer.class)
public abstract class MixinFramebuffer implements FramebufferFixer, Cleaner.Cleanable {

    @Shadow
    public int framebufferTexture;

    @Shadow
    public int depthBuffer;

    @Shadow
    public int framebufferObject;

    @Override
    public void clean() {
      try {
        if (this.framebufferTexture > -1 || this.depthBuffer > -1 || this.framebufferObject > -1 && GpuTape.FIXERS != null) {
          GpuTape.FIXERS.add(this);
        }
      } catch (Exception e) {
        throw new CleanException("Failed to finalize/clean '" + this + "'", e);
      }
    }

    @Override
    public void destroy() {
      GlStateManager.bindTexture(0);
      OpenGlHelper.glBindFramebuffer(36160, 0);
    }

    @Override
    public void release() {
      if (this.framebufferTexture > -1) {
        GlStateManager.deleteTexture(this.framebufferTexture);
      }

      if (this.depthBuffer > -1) {
        GlStateManager.deleteTexture(this.depthBuffer);
      }

      if (this.framebufferObject > -1) {
        OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
        OpenGlHelper.glDeleteFramebuffers(this.framebufferObject);
      }
    }

    @Override
    public String toString() {
      if (TrueSightAddon.addon != null) {
        if (TrueSightAddon.addon.configuration().getGpuBooster().enabled().get().booleanValue()) {
          return "FrB(" + this.framebufferTexture + ", " + this.depthBuffer + ", " + this.framebufferObject + ")";
        }
      }

      return super.toString();
    }


    @Override
    public int hashCode() {
      if (TrueSightAddon.addon == null || !TrueSightAddon.addon.configuration().getGpuBooster().getShouldIdealHash().get().booleanValue()) { return super.hashCode(); }
      return GpuTape.idealHash(this.framebufferTexture + this.depthBuffer + this.framebufferObject);
    }
}
