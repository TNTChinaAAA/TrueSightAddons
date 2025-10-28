package net.labymod.addons.truesight.v1_8_9.mixin.mixins;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemPotion.class)
public abstract class MixinItemPotion {

  @Inject(method = "hasEffect", at = @At("HEAD"), cancellable = true)
  private void disableEffect(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
    if (TrueSightAddon.addon == null) return;
    if (!TrueSightAddon.addon.configuration().getItemOptimizations().get().booleanValue()) return;

    cir.setReturnValue(false);
  }
}