package net.sssubtlety.no_sneaking_over_magma;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.Optional;

public final class Util {
    @SuppressWarnings("ConstantConditions")
    public static int getRgb(Text text) {
        final TextColor color = text.getStyle().getColor();
        if (color == null) return Formatting.WHITE.getColorValue();
        else return color.getRgb();
    }

    public static Text replace(Text text, String regex, String replacement) {
        String string = text.getString();
        string = string.replaceAll(regex, replacement);
        return new LiteralText(string).setStyle(text.getStyle());
    }

    record TranslatableString(String key) {
        public String get() {
            return I18n.translate(key);
        }
    }

    public static boolean isModLoaded(String id, String versionPredicate) {
        final Optional<ModContainer> optModContainer = FabricLoader.getInstance().getModContainer(id);
        if (optModContainer.isPresent()){
            try {
                return VersionPredicate.parse(versionPredicate).test(optModContainer.get().getMetadata().getVersion());
            } catch (VersionParsingException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private Util() { }
}
