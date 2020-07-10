package net.sssubtlety.no_sneaking_over_magma;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

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
