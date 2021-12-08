package net.sssubtlety.no_sneaking_over_magma;


import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = "no_sneaking_over_magma")
public class NoSneakingOverMagmaConfig implements ConfigData {
    private static final NoSneakingOverMagmaConfig INSTANCE = AutoConfig.register(NoSneakingOverMagmaConfig.class, GsonConfigSerializer::new).getConfig();

    public static void init() { }

    public static boolean doesSneakingProtectOnMagma() {
        return INSTANCE.sneakingProtectsOnMagma;
    }
    public static boolean doesFrostWalkerProtectOnMagma() {
        return INSTANCE.frostWalkerProtectsOnMagma;
    }

    private boolean sneakingProtectsOnMagma = false;
    private boolean frostWalkerProtectsOnMagma = true;
}
