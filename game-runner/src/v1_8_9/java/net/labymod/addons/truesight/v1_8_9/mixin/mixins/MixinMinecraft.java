package net.labymod.addons.truesight.v1_8_9.mixin.mixins;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.labymod.addons.truesight.v1_8_9.DamageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.labymod.addons.truesight.core.TNTChina;
import net.labymod.addons.truesight.v1_8_9.key.KeyLoader;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Minecraft.class})
public abstract class MixinMinecraft {

    @Shadow
    @Final
    private static Logger logger;

    @Shadow
    private int leftClickCounter;

    @Shadow
    public MovingObjectPosition objectMouseOver;

    @Shadow
    public WorldClient theWorld;

    @Shadow
    public EntityPlayerSP thePlayer;

    @Shadow
    private ServerData currentServerData;

    @Inject(method = "<init>", at = {@At("RETURN")})
    public void init(CallbackInfo callbackInfo) {
        TNTChina.INSTANCE = new TNTChina();
    }

    @Inject(method = "dispatchKeypresses", at = {@At("HEAD")})
    private void invokeKey(CallbackInfo callbackInfo) {
        KeyLoader.onKeyInput();
    }

    /*
    @Inject(method = "startGame", at = {@At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;ingameGUI:Lnet/minecraft/client/gui/GuiIngame;", shift = At.Shift.AFTER)})
    private void startGame(CallbackInfo callbackInfo) {
        new KeyLoader();
    }*/

    @Inject(method = "startGame", at = {@At("RETURN")})
    private void startGame(CallbackInfo callbackInfo) {
        new KeyLoader();
    }

    @Inject(method = "sendClickBlockToController", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/MovingObjectPosition;getBlockPos()Lnet/minecraft/util/BlockPos;"))
    private void onClickBlock(CallbackInfo callbackInfo) {
      if (this.leftClickCounter == 0 && theWorld.getBlockState(objectMouseOver.getBlockPos()).getBlock().getMaterial() != Material.air) {
          boolean enable = false, autoTool_enable = false;

          if (TrueSightAddon.addon != null) {
              if (TrueSightAddon.addon.configuration() != null) {
                  enable =  TrueSightAddon.addon.configuration().enabled().get().booleanValue();
                  autoTool_enable = TrueSightAddon.addon.configuration().getAutoTool().get().booleanValue();
              }
          }

          if (enable && autoTool_enable) {
              if (objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                  BlockPos blockPos = objectMouseOver.getBlockPos();

                  float bestSpeed = 1F;
                  int bestSlot = -1;

                  if (blockPos == null)
                      return;

                  Block block = theWorld.getBlockState(blockPos).getBlock();

                  if (block instanceof BlockChest) {
                      if (currentServerData != null) {
                          if (currentServerData.serverIP != null) {
                              if (currentServerData.serverIP.contains("hypixel")) {
                                  return;
                              }
                          }
                      }

                  }

                  for (int i = 0; i <= 8; i++) {
                       ItemStack item = thePlayer.inventory.getStackInSlot(i);

                       if (item == null)
                           continue;

                       float speed = item.getStrVsBlock(block);

                       if (speed > bestSpeed) {
                           bestSpeed = speed;
                           bestSlot = i;
                       }
                  }

                  if (bestSlot != -1) {
                      thePlayer.inventory.currentItem = bestSlot;
                  }
              }
              /*else if (objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                  //logger.info("EntityNameAAA: " + objectMouseOver.entityHit.getName());

                  if (objectMouseOver.entityHit instanceof EntityLivingBase) {
                      int bestSpeed = 1;
                      int bestSlot = -1;

                      for (int i = 0; i <= 8; i++) {
                        ItemStack item = thePlayer.inventory.getStackInSlot(i);

                        if (item == null)
                          continue;

                        int speed = item.getItemDamage();

                        if (speed > bestSpeed) {
                          bestSpeed = speed;
                          bestSlot = i;
                        }

                        //logger.info("Damage After Calculating: " + DamageUtil.getTotalDamage(item, thePlayer, (EntityLivingBase) objectMouseOver.entityHit));
                      }

                      //logger.info("Damage: " + bestSpeed);

                      if (bestSlot != -1) {
                          thePlayer.inventory.currentItem = bestSlot;
                      }
                  }
              }*/
          }
      }
    }

    //TODO: NoHitFix
    private static final String TARGET = "Lnet/minecraft/client/settings/KeyBinding;setKeyBindState(IZ)V";
    @Redirect(method="runTick", at=@At(value="INVOKE", target=TARGET))
    public void runTick_setKeyBindState(int keybind, boolean state) {
        if (TrueSightAddon.addon.configuration().getMouseDelayFix().get().booleanValue()) { //left click
          //System.out.println("resetting left click counter");
            if (keybind == -100 && !state) {
                leftClickCounter = 0;
            }
        }

        // TODO: Fix block place.

        KeyBinding.setKeyBindState(keybind, state);
    }

    @Inject(method = "clickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;attackEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;)V"))
    public void onClickMouse_attackEntities(CallbackInfo callbackInfo) {
      boolean enable = false, autoTool_enable = false;

      if (TrueSightAddon.addon != null) {
          if (TrueSightAddon.addon.configuration() != null) {
              enable =  TrueSightAddon.addon.configuration().enabled().get().booleanValue();
              autoTool_enable = TrueSightAddon.addon.configuration().getAutoTool().get().booleanValue();
          }
      }

      if (enable && autoTool_enable) {
        if (objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
          //logger.info("EntityNameAAA: " + objectMouseOver.entityHit.getName());

          if (objectMouseOver.entityHit instanceof EntityLivingBase) {
            int bestDamage = 1;
            int bestSlot = -1;

            for (int i = 0; i <= 8; i++) {
              ItemStack item = thePlayer.inventory.getStackInSlot(i);

              if (item == null)
                continue;

              int damage = item.getMaxDamage();

              if (damage > bestDamage) {
                bestDamage = damage;
                bestSlot = i;
              }

              //logger.info("Damage After Calculating: " + DamageUtil.getTotalDamage(item, thePlayer, (EntityLivingBase) objectMouseOver.entityHit));
            }

            //logger.info("Damage: " + bestSpeed);

            if (bestSlot != -1) {
              thePlayer.inventory.currentItem = bestSlot;
            }
          }
        }
      }
    }
}
