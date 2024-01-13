package net.labymod.addons.truesight.v1_8_9;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.labymod.addons.truesight.v1_8_9.module.ESP;
import net.labymod.addons.truesight.v1_8_9.module.TrueSight;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TNTChina {
    public static final String MOD_VERSION = "1.0.0";
    public static final String MOD_NAME = "TrueSight";
    public static final String MOD_MODID = "true-sight";
    public static final String MOD_URL = "https://github.com/TNTChinaAAA/TrueSight";
    public static TNTChina INSTANCE;
    public static List<Module> MODULES = new ArrayList<>();

    static {
        TNTChina.MODULES.add(new ESP("ESP", 38));
        TNTChina.MODULES.add(new TrueSight("TrueSight", 35));
    }

    public static final int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0D);
        rainbowState %= 360.0D;
        return Color.getHSBColor((float) (rainbowState / 360.0D), 0.8F, 0.7F).getRGB();
    }

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

    public static <T extends Module> T getModule(String name) {
        for (Module m : MODULES) {
            if (m.getName().equalsIgnoreCase(name)) {
                return (T) m;
            }
        }

        return null;
    }
}

