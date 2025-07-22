package net.labymod.addons.truesight.v1_8_9;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;

public class DamageUtil {
  /**
   * 获取 ItemStack 在面对 target 时的总伤害值（不含暴击、力量药水等额外加成）
   */
  public static double getTotalDamage(ItemStack weapon, EntityLivingBase attacker, EntityLivingBase target) {
    // 1. 先算基础攻击力（generic.attackDamage）
    double base = 0.0;
    for (AttributeModifier mod : weapon.getAttributeModifiers()
        .get(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName()))
    {
      if (mod.getOperation() == 0) {
        base += mod.getAmount();
      }
    }

    // 2. 计算针对目标生物类型的附魔加成
    EnumCreatureAttribute creatureType = target.getCreatureAttribute();
    float enchantBonus = EnchantmentHelper.getModifierForCreature(weapon, creatureType);
    //   等同于 Sharpness.level*1.25 + (creatureType==UNDEAD? Smite.level*2.5:0)
    //                      + (creatureType==ARTHROPOD? Bane.level*2.5:0)

    return base + enchantBonus;
  }
}
