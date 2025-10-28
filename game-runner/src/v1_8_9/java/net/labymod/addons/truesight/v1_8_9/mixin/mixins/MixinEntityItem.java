package net.labymod.addons.truesight.v1_8_9.mixin.mixins;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityItem.class)
public abstract class MixinEntityItem {

    @Inject(method = "searchForOtherItemsNearby", at = @At("HEAD"), cancellable = true)
    private void stopSearch(CallbackInfo ci) {
      if (TrueSightAddon.addon == null) return;
      if (!TrueSightAddon.addon.configuration().getItemOptimizations().get().booleanValue()) return;

      ItemStack stack = ((EntityItem)(Object)this).getEntityItem();

      if (stack.stackSize >= stack.getMaxStackSize()) {
        ci.cancel();
      }
    }

    @Inject(method = "combineItems", at = @At("HEAD"), cancellable = true)
    private void stopSearchBoolean(CallbackInfoReturnable<Boolean> cir) {
      if (TrueSightAddon.addon == null) return;
      if (!TrueSightAddon.addon.configuration().getItemOptimizations().get().booleanValue()) return;

      ItemStack stack = ((EntityItem)(Object)this).getEntityItem();

      if (stack.stackSize >= stack.getMaxStackSize()) {
        cir.setReturnValue(false);
      }
    }
}
