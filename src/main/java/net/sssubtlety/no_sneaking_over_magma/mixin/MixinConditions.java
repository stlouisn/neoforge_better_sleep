package net.sssubtlety.no_sneaking_over_magma.mixin;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.sssubtlety.no_sneaking_over_magma.NoSneakingOverMagmaConfig;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinConditions implements IMixinConfigPlugin {
    static {
        AutoConfig.register(NoSneakingOverMagmaConfig.class, GsonConfigSerializer::new);
    }

    private static final NoSneakingOverMagmaConfig CONFIG = AutoConfig.getConfigHolder(NoSneakingOverMagmaConfig.class).getConfig();

    @Override
    public void onLoad(String mixinPackage) { }

    @Override
    public String getRefMapperConfig() { return null; }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

        if(mixinClassName.contains("Entity")) {
            return !CONFIG.doesSneakingProtectOnMagma();
        } else if(mixinClassName.contains("MagmaBlock")) {
            return !CONFIG.doesFrostWalkerProtectOnMagma();
        } else {
            throw new IllegalStateException("no_sneaking_over_magma: MixinConditions' shouldApplyMixin received unexpected targetClassName. ");
        }
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { }

    @Override
    public List<String> getMixins() { return null; }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
}
