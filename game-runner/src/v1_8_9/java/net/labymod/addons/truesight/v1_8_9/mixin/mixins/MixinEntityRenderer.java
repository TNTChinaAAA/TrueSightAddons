package net.labymod.addons.truesight.v1_8_9.mixin.mixins;

import net.labymod.addons.truesight.core.event.EventManager;
import net.labymod.addons.truesight.core.event.events.Render3DEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {

  @Shadow
  private Entity pointedEntity;

  @Shadow
  private Minecraft mc;

  @Shadow
  private float thirdPersonDistanceTemp;

  @Shadow
  private float thirdPersonDistance;

  @Shadow
  private boolean cloudFog;

  @Inject(method = "renderWorldPass", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = At.Shift.BEFORE))
  private void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
      EventManager.callEvent(new Render3DEvent(partialTicks));
  }
}