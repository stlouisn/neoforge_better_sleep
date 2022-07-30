package net.sssubtlety.no_sneaking_over_magma;

import net.fabricmc.api.ModInitializer;

public class Init implements ModInitializer {
    @Override
    public void onInitialize() {
        FeatureControl.init();
    }
}
