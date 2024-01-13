package net.labymod.addons.truesight.v1_8_9;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiUtil {

    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = 0.003921569F * c.getRed();
        float g = 0.003921569F * c.getGreen();
        float b = 0.003921569F * c.getBlue();
        return (new Color(r, g, b, alpha)).getRGB();
    }

    public static void drawRect(double x, double y, double width, double height, int color) {
        double left = x;
        double top = y;
        double right = x + width;
        double bottom = y + height;
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (color >> 24 & 0xFF) / 255.0F;
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static int getCenterStringX(String text, int x, int width) {

        if (getFontRenderer() == null) {
            return 0;
        }

        return (width - getStringWidth(text)) / 2 + x;
    }

    public static int getCenterStringY(String text, int y, int height) {
        if (getFontRenderer() == null) {
            return 0;
        }

        return (height - getStringHeight()) / 2 + y;
    }

    public static FontRenderer getFontRenderer() {
        return ((Minecraft.getMinecraft()).fontRendererObj != null) ? (Minecraft.getMinecraft()).fontRendererObj : null;
    }

    public static int getStringHeight() {
        return (getFontRenderer() != null) ? (getFontRenderer()).FONT_HEIGHT : 0;
    }

    public static int getStringWidth(String text) {
        return (getFontRenderer() != null) ? getFontRenderer().getStringWidth(text) : 0;
    }

    public static double getBigString(int x, String text, double big) {
        int width = getStringWidth(text);
        return (x - width) / big - width / big;
    }

    public static void drawBackground(boolean b1, boolean b2, ResourceLocation background) {
        ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft());
        GlStateManager.disableDepth();
        GlStateManager.depthMask(b1);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        Minecraft.getMinecraft().getTextureManager().bindTexture(background);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(0.0D, scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
        worldrenderer.pos(scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
        worldrenderer.pos(scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(b2);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawImage(ResourceLocation resource, int x, int y, int width, int height) {
        double par1 = (x + width);
        double par2 = (y + height);
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, par2, -90.0D).tex(0.0D, 1.0D).endVertex();
        worldrenderer.pos(par1, par2, -90.0D).tex(1.0D, 1.0D).endVertex();
        worldrenderer.pos(par1, y, -90.0D).tex(1.0D, 0.0D).endVertex();
        worldrenderer.pos(x, y, -90.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}