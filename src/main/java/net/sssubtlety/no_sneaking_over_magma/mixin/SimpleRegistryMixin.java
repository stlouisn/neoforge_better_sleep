package net.sssubtlety.no_sneaking_over_magma.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.effect.ConditionalEnchantmentEffect;
import net.minecraft.enchantment.effect.DamageImmunity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.registry.Holder;
import net.minecraft.registry.RegistrationInfo;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.world.World;
import net.sssubtlety.no_sneaking_over_magma.mixin_helper.DamageSourcePredicateMixinAccessor;
import net.sssubtlety.no_sneaking_over_magma.mixin_helper.DamageSourcesMixinAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static net.minecraft.component.EnchantmentEffectComponentTypes.DAMAGE_IMMUNITY;

@Mixin(SimpleRegistry.class)
abstract class SimpleRegistryMixin {
    @Unique
    private static boolean sourceIsNotHotFloorPredicateIgnored(World world, DamageSource source) {
        return source != ((DamageSourcesMixinAccessor) world.getDamageSources())
            .no_sneaking_over_magma$getHotFloorDamagePredicateIgnored();
    }

    @Inject(method = "register", at = @At("TAIL"))
    private <T> void modifyFrostWalker(
        RegistryKey<T> key, T entry, RegistrationInfo info,
        CallbackInfoReturnable<Holder.Reference<T>> cir
    ) {
        if (!key.equals(Enchantments.FROST_WALKER)) return;

        final var frostWalker = (Enchantment) entry;

        final List<ConditionalEnchantmentEffect<DamageImmunity>> damageImmunities =
            frostWalker.effects().get(DAMAGE_IMMUNITY);
        if (damageImmunities != null) {
            for (final ConditionalEnchantmentEffect<DamageImmunity> immunity : damageImmunities) {
                if (
                    immunity.requirements().orElse(null) instanceof
                        DamageSourcePropertiesLootCondition damageCondition
                ) {
                    //noinspection ConstantValue
                    if (
                        ((Object)damageCondition.predicate().orElse(null)) instanceof
                            DamageSourcePredicateMixinAccessor accessiblePredicate
                    ) {
                        accessiblePredicate.no_sneaking_over_magma$setAdditionalPredicate(
                            SimpleRegistryMixin::sourceIsNotHotFloorPredicateIgnored
                        );
                    }
                }
            }
        }
    }
}
