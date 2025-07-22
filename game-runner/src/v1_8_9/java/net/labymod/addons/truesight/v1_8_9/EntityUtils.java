package net.labymod.addons.truesight.v1_8_9;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import java.awt.*;

public class EntityUtils {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static boolean targetInvisible = true;
    public static boolean targetPlayer = true;
    public static boolean targetMobs = true;
    public static boolean targetAnimals = false;
    public static boolean targetDead = true;

    public static final Color getColor(Entity entity) {
      if (entity instanceof EntityLivingBase) {
        EntityLivingBase entityLivingBase = (EntityLivingBase) entity;

        if (entityLivingBase.hurtTime > 0) {
          return Color.RED;
        }

        return new Color(18, 144, 195);
      }

      return new Color(255, 255, 255);
    }

    public static boolean isSelected(Entity entity, boolean canAttackCheck) {
        if (entity instanceof net.minecraft.entity.EntityLivingBase && (targetDead || entity.isEntityAlive()) && entity != mc.thePlayer && (
                targetInvisible || !entity.isInvisible())) {

            if (targetPlayer && entity instanceof EntityPlayer player) {
                return !(player.getName().contains("NPC") | player.isSpectator());
            }

            return ((targetMobs && isMob(entity)) || (targetAnimals && isAnimal(entity)));
        }

        return false;
    }

    public static boolean isPlayer(Entity entity) {
        return (entity instanceof EntityPlayer && entity.getName() != null);
    }

    public static boolean isAnimal(Entity entity) {
        return (entity instanceof net.minecraft.entity.passive.EntityAnimal || entity instanceof net.minecraft.entity.passive.EntitySquid || entity instanceof net.minecraft.entity.monster.EntityGolem || entity instanceof net.minecraft.entity.passive.EntityBat);
    }

    public static boolean isMob(Entity entity) {
        return (entity instanceof net.minecraft.entity.monster.EntityMob || entity instanceof net.minecraft.entity.passive.EntityVillager || entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.monster.EntityGhast || entity instanceof net.minecraft.entity.boss.EntityDragon);
    }

    public static String getName(NetworkPlayerInfo networkPlayerInfoIn) {
        return (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() :
                ScorePlayerTeam.formatPlayerName((Team) networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
    }

    public static int getPing(EntityPlayer entityPlayer) {
        if (entityPlayer == null) {
            return 0;
        }

        NetworkPlayerInfo networkPlayerInfo = mc.getNetHandler().getPlayerInfo(entityPlayer.getUniqueID());
        return (networkPlayerInfo == null) ? 0 : networkPlayerInfo.getResponseTime();
    }
}
