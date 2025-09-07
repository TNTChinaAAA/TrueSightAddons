package net.labymod.addons.truesight.core;

import net.labymod.addons.truesight.core.module.AutoTool;
import net.labymod.addons.truesight.core.module.ESP;
import net.labymod.addons.truesight.core.module.TrueSight;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TNTChina {

    public static final String MOD_VERSION = "1.0.7";
    public static final String MOD_NAME = "TrueSight";
    public static final String MOD_MODID = "true-sight";
    public static final String MOD_URL = "https://github.com/TNTChinaAAA/TrueSightAddons";
    public static final AutoTool AUTOTOOL = new AutoTool("AutoTool",36);
    public static final ESP ESP = new ESP("ESP", 38);
    public static final TrueSight TRUESIGHT = new TrueSight("TrueSight", 35);
    public static TNTChina INSTANCE;
    public static Map<Module, ConfigProperty<Boolean>> RENDER_MODULES_MAP = new HashMap<>();
    public static List<Module> MODULES = new ArrayList<>();

    static {
        TNTChina.MODULES.add(TNTChina.AUTOTOOL);
        TNTChina.MODULES.add(TNTChina.ESP); //Keyboard.KEY_L
        TNTChina.MODULES.add(TNTChina.TRUESIGHT);
    }

    public static final int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0D);
        rainbowState %= 360.0D;
        return Color.getHSBColor((float) (rainbowState / 360.0D), 0.8F, 0.7F).getRGB();
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

