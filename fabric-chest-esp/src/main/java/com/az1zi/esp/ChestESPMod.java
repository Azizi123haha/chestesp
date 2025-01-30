import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class ChestESPMod implements ClientModInitializer {
    private static KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.chestesp.toggle",
                GLFW.GLFW_KEY_G, // teken g togglenya
                "category.chestesp"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                toggleESP();
                client.player.sendMessage(new LiteralText("Chest ESP " + (espEnabled ? "Enabled" : "Disabled")), true);
            }
        });

        WorldRenderEvents.AFTER_TRANSLUCENT.register((context) -> {
            if (espEnabled && client.world != null) {
                drawESP(context.matrixStack(), client.world);
            }
        });
    }
}

