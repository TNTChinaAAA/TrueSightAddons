package net.labymod.addons.truesight.core.gpuTape;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GpuTape {

    public static ConcurrentLinkedQueue<FramebufferFixer> FIXERS; // init in minecraft <init>
    public static boolean shouldFixOnDisable = false;

    public static int idealHash(int value) {
      value ^= value >>> 16;
      value *= -2048144789;
      value ^= value >>> 13;
      value *= -1028477387;
      value ^= value >>> 16;
      return value;
    }
}
