package net.sssubtlety.no_sneaking_over_magma;

import de.guntram.mcmod.crowdintranslate.CrowdinTranslate;
import net.fabricmc.api.ClientModInitializer;

public class NoSneakingOverMagmaClientInit implements ClientModInitializer {
    public static final String MOD_ID = "no_sneaking_over_magma";
    @Override
    public void onInitializeClient() {
        CrowdinTranslate.downloadTranslations("chicken-nerf", MOD_ID);
    }
}
