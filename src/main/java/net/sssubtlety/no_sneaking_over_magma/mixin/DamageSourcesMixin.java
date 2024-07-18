package net.sssubtlety.no_sneaking_over_magma.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKey;
import net.sssubtlety.no_sneaking_over_magma.mixin_helper.DamageSourcesMixinAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageSources.class)
abstract class DamageSourcesMixin implements DamageSourcesMixinAccessor {
    @Shadow public abstract DamageSource create(RegistryKey<DamageType> registryKey);

    @Unique private DamageSource hotFloorDamagePredicateIgnored;

    @Override
    public DamageSource no_sneaking_over_magma$getHotFloorDamagePredicateIgnored() {
        return hotFloorDamagePredicateIgnored;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initFields(CallbackInfo ci) {
        hotFloorDamagePredicateIgnored = this.create(DamageTypes.HOT_FLOOR);
    }
}
