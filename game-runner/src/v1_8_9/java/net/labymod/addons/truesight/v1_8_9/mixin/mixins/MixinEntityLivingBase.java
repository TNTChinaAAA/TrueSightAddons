package net.labymod.addons.truesight.v1_8_9.mixin.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {

  public MixinEntityLivingBase(World lvt_1_1_) {
         super(lvt_1_1_);
  }

  @Inject(method = "getLook(F)Lnet/minecraft/util/Vec3;", at = {@At("HEAD")}, cancellable = true)
  public void onGetLook(float partialTicks, CallbackInfoReturnable<Vec3> cir) {
        if (((EntityLivingBase) (Object) this) instanceof EntityPlayerSP) {
            cir.setReturnValue(super.getLook(partialTicks));
        }
  }
}
