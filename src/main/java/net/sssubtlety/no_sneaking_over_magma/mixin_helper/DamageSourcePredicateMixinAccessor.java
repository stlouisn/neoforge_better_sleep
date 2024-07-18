package net.sssubtlety.no_sneaking_over_magma.mixin_helper;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;

import java.util.function.BiPredicate;

public interface DamageSourcePredicateMixinAccessor {
    void no_sneaking_over_magma$setAdditionalPredicate(BiPredicate<World, DamageSource> predicate);
}
