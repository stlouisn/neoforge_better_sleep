package net.sssubtlety.no_sneaking_over_magma;

import de.guntram.mcmod.crowdintranslate.CrowdinTranslate;
import net.fabricmc.api.ClientModInitializer;

public class ClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CrowdinTranslate.downloadTranslations("no-sneaking-over-magma", NoSneakingOverMagma.NAMESPACE);
    }
}
