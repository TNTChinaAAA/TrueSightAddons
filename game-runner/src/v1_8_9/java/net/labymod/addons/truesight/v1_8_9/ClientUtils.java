/*    */
package net.labymod.addons.truesight.v1_8_9;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKey;
import java.lang.reflect.Field;
import java.security.PublicKey;

public class ClientUtils {
    public static final Minecraft mc = Minecraft.getMinecraft();
    private static final Logger logger = LogManager.getLogger("TrueSight");

    private static Field fastRenderField;

    static {
        try {
            fastRenderField = GameSettings.class.getDeclaredField("ofFastRender");

            if (!fastRenderField.isAccessible())
                fastRenderField.setAccessible(true);
        } catch (NoSuchFieldException noSuchFieldException) {
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void disableFastRender() {
        try {
            if (fastRenderField != null) {
                if (!fastRenderField.isAccessible()) {
                    fastRenderField.setAccessible(true);
                }

                fastRenderField.setBoolean(mc.gameSettings, false);
            }
        } catch (IllegalAccessException illegalAccessException) {}
    }

    public static void sendEncryption(NetworkManager networkManager, SecretKey secretKey, PublicKey publicKey, S01PacketEncryptionRequest encryptionRequest) {
        networkManager.sendPacket((Packet) new C01PacketEncryptionResponse(secretKey, publicKey, encryptionRequest.getVerifyToken()), p_operationComplete_1_ -> networkManager.enableEncryption(secretKey), new io.netty.util.concurrent.GenericFutureListener[0]);
    }

    public static void displayChatMessage(String message) {
        if (mc.thePlayer == null) {
            getLogger().info("(MCChat)" + message);
            return;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", message);
        mc.thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent(jsonObject.toString()));
    }
}