package net.labymod.addons.truesight.v1_8_9.mixin.mixins;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.labymod.addons.truesight.core.module.TrueSight;
import net.labymod.addons.truesight.core.module.antiblind.AntiBlindSubSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {

  @Shadow
  public int jumpTicks;

  public MixinEntityLivingBase(World lvt_1_1_) {
         super(lvt_1_1_);
  }

  @Inject(method = "getLook(F)Lnet/minecraft/util/Vec3;", at = {@At("HEAD")}, cancellable = true) // Mouse Delay Fix
  public void onGetLook(float partialTicks, CallbackInfoReturnable<Vec3> cir) {
        if (((EntityLivingBase) (Object) this) instanceof EntityPlayerSP) {
            cir.setReturnValue(super.getLook(partialTicks));
        }
  }

  @Inject(method = "onLivingUpdate", at = @At("HEAD"))
  private void headLiving(CallbackInfo ci) {
    if (TrueSightAddon.addon == null || !TrueSightAddon.addon.configuration().enabled().get().booleanValue()) return;
    if (!((EntityLivingBase)((Object) this)).equals(Minecraft.getMinecraft().thePlayer)) return;

    if (TrueSightAddon.addon.configuration().getJumpDelayFix().get().booleanValue()) this.jumpTicks = 0;
  }

  @Inject(method = "isPotionActive(Lnet/minecraft/potion/Potion;)Z", at = @At("HEAD"), cancellable = true)
  private void isPotionActive(Potion p_isPotionActive_1_, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
    if (TrueSightAddon.addon != null) {
      AntiBlindSubSetting antiBlind = TrueSightAddon.addon.configuration().getAntiBlind();

      if ((p_isPotionActive_1_ == Potion.confusion || p_isPotionActive_1_ == Potion.blindness) && antiBlind.enabled().get().booleanValue() && antiBlind.getConfusionEffect().get().booleanValue()) callbackInfoReturnable.setReturnValue(false);
    }
  }
}
