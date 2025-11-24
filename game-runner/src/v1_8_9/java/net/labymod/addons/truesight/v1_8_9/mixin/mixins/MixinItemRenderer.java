package net.labymod.addons.truesight.v1_8_9.mixin.mixins;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.labymod.addons.truesight.core.module.antiblind.AntiBlindSubSetting;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {

  @Inject(method = "renderFireInFirstPerson", at = @At("HEAD"), cancellable = true)
  private void renderFireInFirstPerson(final CallbackInfo callbackInfo) {
    if (TrueSightAddon.addon != null) {
      AntiBlindSubSetting antiBlind = TrueSightAddon.addon.configuration().getAntiBlind();

      if (antiBlind.enabled().get().booleanValue() && antiBlind.getFireEffect().get().booleanValue()) callbackInfo.cancel();
    }
  }
}
