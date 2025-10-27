package net.labymod.addons.truesight.core.gpuTape;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GpuTape {

    public static ConcurrentLinkedQueue<FramebufferFixer> FIXERS; // init in minecraft <init>
    public static boolean shouldFixOnDisable = false;
}
