package net.sssubtlety.no_sneaking_over_magma;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "no_sneaking_over_magma")
public class NoSneakingOverMagmaConfig implements ConfigData {
    boolean sneakingProtectsOnMagma = false;
    boolean frostWalkerProtectsOnMagma = true;

    public boolean doesSneakingProtectOnMagma() {
        return sneakingProtectsOnMagma;
    }

    public boolean doesFrostWalkerProtectOnMagma() {
        return frostWalkerProtectsOnMagma;
    }
}
