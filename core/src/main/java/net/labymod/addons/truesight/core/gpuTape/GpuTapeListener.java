package net.labymod.addons.truesight.core.gpuTape;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;

public class GpuTapeListener {

  @Subscribe
  public void onTick(GameTickEvent event) {
    if (TrueSightAddon.addon == null) return;
    if (!TrueSightAddon.addon.configuration().getGpuBooster().get()) {
      if (GpuTape.shouldFixOnDisable) {
        if (event.phase() == Phase.POST && GpuTape.FIXERS != null) {
          this.doFix();
        }

        GpuTape.shouldFixOnDisable = false;
      } else {
        return;
      }
    }

    if (event.phase() == Phase.POST && GpuTape.FIXERS != null) {
      this.doFix();
    }
  }

  public void doFix() {
      try {
        boolean done = false;
        int counter = 0;

        while(!GpuTape.FIXERS.isEmpty() && counter++ < 20) {
          FramebufferFixer fixer = GpuTape.FIXERS.poll();

          if (fixer != null) {
            if (!done) {
              fixer.destroy();
              done = true;
            }

            fixer.release();
          }
        }
      } catch (Exception e) {
        throw new CleanException("Failed to proccess cleaning framebuffer!", e);
      }
  }
}
