package net.sssubtlety.no_sneaking_over_magma;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

import static net.sssubtlety.no_sneaking_over_magma.FeatureControl.isConfigLoaded;
import static net.sssubtlety.no_sneaking_over_magma.NoSneakingOverMagma.NAMESPACE;


@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    private static final TranslatableText NO_CONFIG_SCREEN_TITLE = new TranslatableText("text." + NAMESPACE + ".no_config_screen.title");
    private static final TranslatableText NO_CONFIG_SCREEN_MESSAGE = new TranslatableText("text." + NAMESPACE + ".no_config_screen.message");

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return isConfigLoaded() ?
                parent -> AutoConfig.getConfigScreen(Config.class, parent).get() :
                NoConfigScreen::new;
    }

    public static class NoConfigScreen extends Screen {
        private final Screen parent;
        protected NoConfigScreen(Screen parent) {
            super(NO_CONFIG_SCREEN_TITLE);
            this.parent = parent;
        }

        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            renderBackground(matrices);
            super.render(matrices, mouseX, mouseY, delta);
            final int windowHCenter = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2;
            final int windowHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

            DrawableHelper.drawCenteredText(
                    matrices, MinecraftClient.getInstance().textRenderer,
                    Util.replace(NO_CONFIG_SCREEN_TITLE, "\\$\\{name\\}", NoSneakingOverMagma.NAME.get()),
                    windowHCenter, windowHeight / 10,
                    Util.getRgb(NO_CONFIG_SCREEN_MESSAGE)
            );

            DrawableHelper.drawCenteredText(
                    matrices, MinecraftClient.getInstance().textRenderer,
                    NO_CONFIG_SCREEN_MESSAGE,
                    windowHCenter, windowHeight / 2,
                    Util.getRgb(NO_CONFIG_SCREEN_MESSAGE)
            );
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void onClose() {
            this.client.setScreen(parent);
        }
    }
}

