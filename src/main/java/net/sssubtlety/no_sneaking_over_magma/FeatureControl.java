package net.sssubtlety.no_sneaking_over_magma;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FeatureControl {
    private static final @Nullable Config CONFIG_INSTANCE;

    static {
        boolean shouldLoadConfig = false;

        final Optional<ModContainer> optModContainer = FabricLoader.getInstance().getModContainer("cloth-config");
        if (optModContainer.isPresent()){
            try {
                shouldLoadConfig = VersionPredicate.parse(">=6.1.48").test(optModContainer.get().getMetadata().getVersion());
            } catch (VersionParsingException e) {
                e.printStackTrace();
            }
        }

        CONFIG_INSTANCE = shouldLoadConfig ?
                AutoConfig.register(Config.class, GsonConfigSerializer::new).getConfig() : null;

    }

    public interface Defaults {
        boolean sneaking_protects_from_magma = false;
        boolean frost_walker_protects_from_magma = true;
        boolean magma_damages_non_living_entities = false;
        boolean magma_sets_fire_to_entities = false;
        boolean fetch_translation_updates = true;
    }

    public static boolean shouldSneakingProtectFromMagma() {
        return CONFIG_INSTANCE == null ? Defaults.sneaking_protects_from_magma : CONFIG_INSTANCE.sneaking_protects_from_magma;
    }

    public static boolean shouldFrostWalkerProtectFromMagma() {
        return CONFIG_INSTANCE == null ? Defaults.frost_walker_protects_from_magma : CONFIG_INSTANCE.frost_walker_protects_from_magma;
    }

    public static boolean shouldMagmaDamageNonLivingEntities() {
        return CONFIG_INSTANCE == null ? Defaults.magma_damages_non_living_entities : CONFIG_INSTANCE.magma_damages_non_living_entities;
    }

    public static boolean shouldMagmaSetFireToEntities() {
        return CONFIG_INSTANCE == null ? Defaults.magma_sets_fire_to_entities : CONFIG_INSTANCE.magma_sets_fire_to_entities;
    }

    public static boolean shouldFetchTranslationUpdates() {
        return CONFIG_INSTANCE == null ? Defaults.fetch_translation_updates : CONFIG_INSTANCE.fetch_translation_updates;
    }

    public static boolean isConfigLoaded() {
        return CONFIG_INSTANCE != null;
    }

    public static void init() { }

    private FeatureControl() { }
}
