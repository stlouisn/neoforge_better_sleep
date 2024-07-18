package net.sssubtlety.no_sneaking_over_magma.mixin_helper;

import net.minecraft.entity.damage.DamageSource;

public interface DamageSourcesMixinAccessor {
    DamageSource no_sneaking_over_magma$getHotFloorDamagePredicateIgnored();
}
