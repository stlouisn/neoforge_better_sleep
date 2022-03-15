package net.sssubtlety.no_sneaking_over_magma;

import de.guntram.mcmod.crowdintranslate.CrowdinTranslate;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class NoSneakingOverMagma {
    public static final String NAMESPACE = "no_sneaking_over_magma";
    public static final Util.TranslatableString NAME = new Util.TranslatableString("text." + NAMESPACE + ".name");

    public static class Init implements ModInitializer {
        @Override
        public void onInitialize() {
            FeatureControl.init();
        }
    }

    public static class ClientInit implements ClientModInitializer {
        @Override
        public void onInitializeClient () {
            CrowdinTranslate.downloadTranslations("no-sneaking-over-magma", NAMESPACE);
        }
    }
}
