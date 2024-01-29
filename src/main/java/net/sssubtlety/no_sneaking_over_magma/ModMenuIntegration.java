package net.sssubtlety.no_sneaking_over_magma;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.sssubtlety.no_sneaking_over_magma.FeatureControl.isConfigLoaded;
import static net.sssubtlety.no_sneaking_over_magma.NoSneakingOverMagma.NAMESPACE;


@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    private static final MutableText NO_CONFIG_SCREEN_TITLE = Text.translatable("text." + NAMESPACE + ".no_config_screen.title");
    private static final MutableText NO_CONFIG_SCREEN_MESSAGE = Text.translatable("text." + NAMESPACE + ".no_config_screen.message");

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
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
            super.render(graphics, mouseX, mouseY, delta);
            final int windowHCenter = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2;
            final int windowHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

            graphics.drawCenteredShadowedText(
                this.textRenderer,
                Util.replace(NO_CONFIG_SCREEN_TITLE, "\\$\\{name\\}", NoSneakingOverMagma.NAME.getString()),
                windowHCenter, windowHeight / 10,
                Formatting.WHITE.getColorValue()
            );

            graphics.drawCenteredShadowedText(
                this.textRenderer,
                NO_CONFIG_SCREEN_MESSAGE,
                windowHCenter, windowHeight / 2,
                Formatting.RED.getColorValue()
            );
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void closeScreen() {
            this.client.setScreen(parent);
        }
    }
}

