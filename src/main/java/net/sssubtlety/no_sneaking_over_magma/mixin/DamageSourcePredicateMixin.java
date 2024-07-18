package net.sssubtlety.no_sneaking_over_magma.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sssubtlety.no_sneaking_over_magma.mixin_helper.DamageSourcePredicateMixinAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiPredicate;

@Mixin(DamageSourcePredicate.class)
abstract class DamageSourcePredicateMixin implements DamageSourcePredicateMixinAccessor {
    @Unique
    private static boolean pass(World world, DamageSource source) {
        return true;
    }

    @Unique private BiPredicate<World, DamageSource> additionalPredicate;

    @Override
    public void no_sneaking_over_magma$setAdditionalPredicate(BiPredicate<World, DamageSource> predicate) {
        this.additionalPredicate = predicate;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initFields(CallbackInfo ci) {
        this.additionalPredicate = DamageSourcePredicateMixin::pass;
    }

    @ModifyReturnValue(
        method = "test(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/Vec3d;" +
            "Lnet/minecraft/entity/damage/DamageSource;)Z",
        at = @At("RETURN")
    )
    private boolean checkAdditionalPredicate(
        boolean original,
        ServerWorld world, Vec3d pos, DamageSource damageSource
    ) {
        return original && additionalPredicate.test(world, damageSource);
    }
}
